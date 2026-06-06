import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import Classes.Book;
import Classes.Student;
import Classes.Librarian;
import Classes.LibrarySystem;
import Exceptions.BookUnavailableException;
import Exceptions.InvalidBookDataException;
import Exceptions.ReservationLimitException;
import Interfaces.IBookSearch;
import Interfaces.BookSearchEngine;

//To run: 
//1. Compile all Java files:
//javac -cp . Main.java Classes/*.java Interfaces/*.java Exceptions/*.java
//2. Run the program:

//java Main

public class Main {

    static LibrarySystem library = new LibrarySystem();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InvalidBookDataException {
        //Setup
        Librarian sara = new Librarian(1, "Sara", "sara@uni.edu", 100, true);

        library.addBook(new Book(1, "Clean Code",                 "Robert Martin",       "Programming",      "978-0132350884", 2, 2008));
        library.addBook(new Book(2, "Java Programming",           "Daniel Liang",        "Programming",      "978-0134670942", 3, 2018));
        library.addBook(new Book(3, "Introduction to Algorithms", "Thomas Cormen",       "Computer Science", "978-0262033848", 1, 2009));
        library.addBook(new Book(4, "Database Systems",           "Ramez Elmasri",       "Databases",        "978-0133970777", 2, 2015));
        library.addBook(new Book(5, "Operating System Concepts",  "Abraham Silberschatz","Computer Science", "978-1119800361", 2, 2018));

        library.registerStudent(new Student(1, "Abeer",  "abeer@uni.edu",  "Software Engineering", true));
        library.registerStudent(new Student(2, "Salma",  "salma@uni.edu",  "Computer Engineering", true));
        library.registerStudent(new Student(3, "Omaima", "omaima@uni.edu", "Computer Engineering", false));

        library.addStudyRoom("Room A");
        library.addStudyRoom("Room B");
        library.addStudyRoom("Room C");
        

        //Show Menu
        System.out.println("Welcome to the Library!");
        System.out.println("The Librarian for this library is " + sara.getName() + ". Feel free to ask for help at " + sara.getEmail());

        while (true) {
            System.out.println("\nSelect an option to continue:");
            System.out.println("1.  List all books");
            System.out.println("2.  Search for a book");
            System.out.println("3.  Check out a book");
            System.out.println("4.  Return a book");
            System.out.println("5.  Reserve a book");
            System.out.println("6.  View student profile");
            System.out.println("7.  Book a study room");
            System.out.println("8.  Free a study room");
            System.out.println("9.  Add a new book");
            System.out.println("10. Save / Load data");
            System.out.println("0.  Exit");
            System.out.print("Your choice: ");

            switch (scanner.nextLine().trim()) {
                case "1":  listAllBooks();   break;
                case "2":  searchBooks();    break;
                case "3":  checkOutBook();   break;
                case "4":  returnBook();     break;
                case "5":  reserveBook();    break;
                case "6":  viewStudent();    break;
                case "7":  bookStudyRoom();  break;
                case "8":  freeStudyRoom();  break;
                case "9":  addBook();        break;
                case "10": saveLoadMenu();   break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void listAllBooks() {
        ArrayList<Book> catalog = library.getCatalog();
        System.out.println("\nAll Books (" + catalog.size() + "):");
        for (Book b : catalog) {
            System.out.println("  " + b.getBookID() + ". " + b.getTitle()
                    + " by " + b.getAuthor()
                    + " [" + b.getCategory() + "]"
                    + " - copies available: " + b.getAvailableCopies());
        }
    }

    private static void searchBooks() {
        System.out.println("\nSearch by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Category");
        System.out.println("4. Available books only");
        System.out.print("Your choice: ");
        String choice = scanner.nextLine().trim();

        IBookSearch engine = new BookSearchEngine(library.getCatalog());
        ArrayList<Book> results;

        switch (choice) {
            case "1":
                System.out.print("Enter title keyword: ");
                results = engine.searchByTitle(scanner.nextLine().trim());
                break;
            case "2":
                System.out.print("Enter author name: ");
                results = engine.searchByAuthor(scanner.nextLine().trim());
                break;
            case "3":
                System.out.print("Enter category: ");
                results = engine.filterByCategory(scanner.nextLine().trim());
                break;
            case "4":
                results = engine.findAvailableBooks();
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }

        System.out.println("\nFound " + results.size() + " result(s):");
        for (Book b : results) {
            System.out.println("  - " + b.getTitle() + " by " + b.getAuthor()
                    + " (copies: " + b.getAvailableCopies() + ")");
        }
    }

    private static void checkOutBook() throws IOException {
        Student student = pickStudent();
        if (student == null) return;
        Book book = pickBook();
        if (book == null) return;

        try {
            library.borrowBook(student, book);
            System.out.println(student.getName() + " successfully checked out \"" + book.getTitle()
                    + "\". Copies remaining: " + book.getAvailableCopies());
        } catch (BookUnavailableException e) {
            System.out.println("Could not check out book: " + e.getMessage());
        }
    }

    private static void returnBook() throws IOException {
        Student student = pickStudent();
        if (student == null) return;

        ArrayList<Book> borrowed = student.getBorrowedBooks();
        if (borrowed.isEmpty()) {
            System.out.println(student.getName() + " has no books to return.");
            return;
        }

        System.out.println("\nCurrently borrowed by " + student.getName() + ":");
        for (int i = 0; i < borrowed.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + borrowed.get(i).getTitle());
        }
        System.out.print("Select book number to return: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= borrowed.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Book book = borrowed.get(idx);
        library.returnBook(student, book);
        System.out.println(student.getName() + " returned \"" + book.getTitle() + "\".");
    }

    private static void reserveBook() throws IOException {
        Student student = pickStudent();
        if (student == null) return;
        Book book = pickBook();
        if (book == null) return;

        try {
            library.reserveBook(student, book);
            System.out.println(student.getName() + " reserved \"" + book.getTitle() + "\". "
                    + "Reservations: " + student.getReservedBooks().size() + " / " + Student.maxReserved);
        } catch (ReservationLimitException e) {
            System.out.println("Could not reserve book: " + e.getMessage());
        }
    }

    private static void viewStudent() {
        Student student = pickStudent();
        if (student == null) return;

        System.out.println("\nStudent: " + student.getName());
        System.out.println("Email:   " + student.getEmail());
        System.out.println("Major:   " + student.getMajor());
        System.out.println("Checked out (" + student.getBorrowedBooks().size() + "/" + Student.maxBorrowed + "):");
        for (Book b : student.getBorrowedBooks()) {
            System.out.println("  - " + b.getTitle());
        }
        System.out.println("Reserved (" + student.getReservedBooks().size() + "/" + Student.maxReserved + "):");
        for (Book b : student.getReservedBooks()) {
            System.out.println("  - " + b.getTitle());
        }
    }

    private static void bookStudyRoom() throws IOException {
        Student student = pickStudent();
        if (student == null) return;

        ArrayList<String> names = library.getRoomNames();
        ArrayList<Boolean> avail = library.getRoomAvailable();
        System.out.println("\nStudy rooms:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + names.get(i)
                    + " - " + (avail.get(i) ? "Available" : "Occupied"));
        }
        System.out.print("Select room number: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= names.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        boolean ok = library.bookStudyRoom(student, names.get(idx));
        if (ok) {
            System.out.println(student.getName() + " booked " + names.get(idx) + ".");
        } else {
            System.out.println(names.get(idx) + " is already occupied.");
        }
    }

    private static void freeStudyRoom() {
        ArrayList<String> names = library.getRoomNames();
        ArrayList<Boolean> avail = library.getRoomAvailable();
        System.out.println("\nStudy rooms:");
        for (int i = 0; i < names.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + names.get(i)
                    + " - " + (avail.get(i) ? "Available" : "Occupied"));
        }
        System.out.print("Select room number to free: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= names.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        avail.set(idx, true);
        System.out.println(names.get(idx) + " is now available.");
    }

    private static void addBook() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Category: ");
        String category = scanner.nextLine().trim();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine().trim();
        System.out.print("Number of copies: ");
        int copies = readInt();
        System.out.print("Publication year: ");
        int year = readInt();

        int newId = library.getCatalog().size() + 1;
        try {
            library.addBook(new Book(newId, title, author, category, isbn, copies, year));
            System.out.println("Book added successfully with ID " + newId + ".");
        } catch (InvalidBookDataException e) {
            System.out.println("Could not add book: " + e.getMessage());
        }
    }

    private static void saveLoadMenu() throws IOException {
        System.out.println("\n1. Save books and students to files");
        System.out.println("2. Load books and students from files");
        System.out.print("Your choice: ");
        String choice = scanner.nextLine().trim();
        try {
            if (choice.equals("1")) {
                library.saveBooks();
                library.saveStudents();
                System.out.println("Data saved to books.txt and students.txt.");
            } else if (choice.equals("2")) {
                library.loadBooks();
                library.loadStudents();
                System.out.println("Loaded " + library.getCatalog().size()
                        + " books and " + library.getStudents().size() + " students.");
            } else {
                System.out.println("Invalid option.");
            }
        } catch (InvalidBookDataException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    private static Student pickStudent() {
        ArrayList<Student> students = library.getStudents();
        System.out.println("\nSelect a student:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + students.get(i).getName()
                    + " (" + students.get(i).getMajor() + ")");
        }
        System.out.print("Your choice: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= students.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        Student student = students.get(idx);
        System.out.print("Enter email to confirm identity: ");
        String email = scanner.nextLine().trim();
        if (!student.authenticate(student.getUserID(), email)) {
            System.out.println("Authentication failed.");
            return null;
        }
        return student;
    }

    private static Book pickBook() {
        ArrayList<Book> catalog = library.getCatalog();
        System.out.println("\nSelect a book:");
        for (int i = 0; i < catalog.size(); i++) {
            Book b = catalog.get(i);
            System.out.println("  " + (i + 1) + ". " + b.getTitle()
                    + " by " + b.getAuthor()
                    + " (copies: " + b.getAvailableCopies() + ")");
        }
        System.out.print("Your choice: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= catalog.size()) {
            System.out.println("Invalid selection.");
            return null;
        }
        return catalog.get(idx);
    }

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
