import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Classes.Book;
import Classes.Student;
import Exceptions.BookUnavailableException;
import Exceptions.InvalidBookDataException;
import Exceptions.ReservationLimitException;
public class LibrarySystem {

    private ArrayList<Book> catalog;
    private ArrayList<Student> students;

    // Reservation waiting list, in the order students reserved.
    // queueBookIds.get(i) is the book reserved by queueStudents.get(i).
    private ArrayList<Integer> queueBookIds;
    private ArrayList<Student> queueStudents;

    // Study rooms. roomNames.get(i) is available when roomAvailable.get(i) is true.
    private ArrayList<String> roomNames;
    private ArrayList<Boolean> roomAvailable;

    // File names used for persistence.
    private static final String BOOKS_FILE = "books.txt";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String SEP = "|";   // field separator in the files

    public LibrarySystem() {
        this.catalog = new ArrayList<Book>();
        this.students = new ArrayList<Student>();
        this.queueBookIds = new ArrayList<Integer>();
        this.queueStudents = new ArrayList<Student>();
        this.roomNames = new ArrayList<String>();
        this.roomAvailable = new ArrayList<Boolean>();
    }

    // ----- Catalog management -----

    public void addBook(Book book) {
        catalog.add(book);
    }

    public void removeBook(Book book) {
        catalog.remove(book);
        // Remove any reservations waiting for this book.
        for (int i = queueBookIds.size() - 1; i >= 0; i--) {
            if (queueBookIds.get(i).equals(book.getBookID())) {
                queueBookIds.remove(i);
                queueStudents.remove(i);
            }
        }
    }

    public ArrayList<Book> getCatalog() {
        return catalog;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void registerStudent(Student student) {
        students.add(student);
    }

    // ----- Borrowing and returning -----

    /**
     * Borrows a book for a student. Throws BookUnavailableException when no
     * copies are available. The student's borrow limit is enforced inside
     * Student.addBorrowedBook.
     */
    public void borrowBook(Student student, Book book)
            throws BookUnavailableException, IOException {
        if (!book.isAvailable()) {
            throw new BookUnavailableException(
                    "No copies available for: " + book.getTitle());
        }
        student.addBorrowedBook(book);                 // checks borrow limit
        updateAvailability(book, -1);                  // one less copy
        logTransaction("BORROW", student, book);
    }

    /**
     * Returns a borrowed book: restores a copy and then gives the book to the
     * next student waiting in its reservation queue, if any.
     */
    public void returnBook(Student student, Book book) throws IOException {
        student.removeBorrowedBook(book);
        updateAvailability(book, +1);                  // one more copy
        logTransaction("RETURN", student, book);
        fulfillNextReservation(book);
    }

    // ----- Reservations -----

    /**
     * Reserves a book for a student. Throws ReservationLimitException when the
     * student already has the maximum number of reservations.
     */
    public void reserveBook(Student student, Book book)
            throws ReservationLimitException, IOException {
        student.addReservedBook(book);                 // checks reservation limit

        // Add the student to the back of the waiting list for this book.
        queueBookIds.add(book.getBookID());
        queueStudents.add(student);
        logTransaction("RESERVE", student, book);
    }

    /**
     * If a returned book has students waiting and a copy is available, give it
     * to the first student in line (moves it from reserved to borrowed).
     */
    private void fulfillNextReservation(Book book) throws IOException {
        if (!book.isAvailable()) {
            return;
        }
        // Find the first (oldest) reservation for this book.
        for (int i = 0; i < queueBookIds.size(); i++) {
            if (queueBookIds.get(i).equals(book.getBookID())) {
                Student next = queueStudents.get(i);
                queueBookIds.remove(i);
                queueStudents.remove(i);
                next.removeReservedBook(book);
                next.addBorrowedBook(book);
                updateAvailability(book, -1);
                logTransaction("BORROW", next, book);
                return;
            }
        }
    }

    // ----- Availability -----

    /**
     * Changes the available copies of a book by the given amount.
     * delta is -1 when borrowing and +1 when returning.
     */
    private void updateAvailability(Book book, int delta) {
        try {
            book.setAvailableCopies(book.getAvailableCopies() + delta);
        } catch (InvalidBookDataException e) {
            // This should not happen during normal borrow/return operations.
            System.out.println("Availability error: " + e.getMessage());
        }
    }

    // ----- Study rooms -----

    public void addStudyRoom(String roomName) {
        roomNames.add(roomName);
        roomAvailable.add(true);   // true = available
    }

    /**
     * Books a study room for a student if it is free. Returns true on success.
     */
    public boolean bookStudyRoom(Student student, String roomName) throws IOException {
        for (int i = 0; i < roomNames.size(); i++) {
            if (roomNames.get(i).equals(roomName) && roomAvailable.get(i)) {
                roomAvailable.set(i, false);
                logTransaction("ROOM-BOOK:" + roomName, student, null);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    public ArrayList<Boolean> getRoomAvailable() {
        return roomAvailable;
    }

    // ----- File I/O (Task 8) -----

    /** Saves the whole catalog to books.txt. */
    public void saveBooks() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : catalog) {
                writer.write(b.getBookID() + SEP + b.getTitle() + SEP + b.getAuthor()
                        + SEP + b.getCategory() + SEP + b.getISBN()
                        + SEP + b.getAvailableCopies() + SEP + b.getPublicationYear());
                writer.newLine();
            }
        }
    }

    /** Loads the catalog from books.txt (replacing the current catalog). */
    public void loadBooks() throws IOException, InvalidBookDataException {
        catalog.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\\" + SEP);
                Book book = new Book(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4],
                        Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
                catalog.add(book);
            }
        }
    }

    /** Saves all registered students to students.txt. */
    public void saveStudents() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : students) {
                writer.write(s.getUserID() + SEP + s.getName() + SEP + s.getEmail()
                        + SEP + s.getMajor() + SEP + s.isNotifyByEmail());
                writer.newLine();
            }
        }
    }

    /** Loads students from students.txt (replacing the current list). */
    public void loadStudents() throws IOException {
        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] fields = line.split("\\" + SEP);
                Student s = new Student(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3]);
                s.setNotifyByEmail(Boolean.parseBoolean(fields[4]));
                students.add(s);
            }
        }
    }

    /**
     * Appends a borrowing/reservation record to transactions.txt.
     * book may be null for non-book actions such as room bookings.
     */
    private void logTransaction(String action, Student student, Book book) throws IOException {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            String bookId = (book == null) ? "-" : String.valueOf(book.getBookID());
            writer.write(LocalDateTime.now() + SEP + action + SEP
                    + student.getUserID() + SEP + bookId);
            writer.newLine();
        }
    }
}