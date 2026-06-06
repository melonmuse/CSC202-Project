package Classes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Exceptions.BookUnavailableException;
import Exceptions.InvalidBookDataException;
import Exceptions.ReservationLimitException;
public class LibrarySystem {

    private ArrayList<Book> catalog;
    private ArrayList<Student> students;

    // Reservation waiting list
    private ArrayList<Integer> queueBookIds;
    private ArrayList<Student> queueStudents;

    // Study rooms
    private ArrayList<String> roomNames;
    private ArrayList<Boolean> roomAvailable;

    // File names
    private static final String BOOKS_FILE = "books.txt";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";
    private static final String SEP = "|";  

    public LibrarySystem() {
        this.catalog = new ArrayList<Book>();
        this.students = new ArrayList<Student>();
        this.queueBookIds = new ArrayList<Integer>();
        this.queueStudents = new ArrayList<Student>();
        this.roomNames = new ArrayList<String>();
        this.roomAvailable = new ArrayList<Boolean>();
    }

    //Catalog Management: Add, Remove, View Books, View Students, and Register Students
    public void addBook(Book book) {
        catalog.add(book);
    }

    public void removeBook(Book book) {
        catalog.remove(book);
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

    //Borrowing and Returning Books
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

    public void returnBook(Student student, Book book) throws IOException {
        student.removeBorrowedBook(book);
        updateAvailability(book, +1);                  // one more copy
        logTransaction("RETURN", student, book);
        fulfillNextReservation(book);
    }

    //Book Reservations and Waiting List
    public void reserveBook(Student student, Book book)
            throws ReservationLimitException, IOException {
        student.addReservedBook(book);            

        queueBookIds.add(book.getBookID());
        queueStudents.add(student);
        logTransaction("RESERVE", student, book);
    }

    //Move student from reserved to borrowed if a copy becomes available
    private void fulfillNextReservation(Book book) throws IOException {
        if (!book.isAvailable()) {
            return;
        }
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

    //Availability
    private void updateAvailability(Book book, int remove) {
        try {
            book.setAvailableCopies(book.getAvailableCopies() + remove);
        } catch (InvalidBookDataException e) {
            System.out.println("Availability error: " + e.getMessage());
        }
    }

    //Study Room Booking
    public void addStudyRoom(String roomName) {
        roomNames.add(roomName);
        roomAvailable.add(true);
    }

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

    //File I/O
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

    public void saveStudents() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student s : students) {
                writer.write(s.getUserID() + SEP + s.getName() + SEP + s.getEmail()
                        + SEP + s.getMajor() + SEP + s.isNotifyByEmail());
                writer.newLine();
            }
        }
    }

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

    //Appends a borrowing/reservation record to transactions.txt.
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