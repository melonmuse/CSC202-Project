package Classes;
import java.util.ArrayList;
import Exceptions.ReservationLimitException;

public class Student extends User {
    //Define variables
    private String major;
    public static final int maxBorrowed = 5;
    public static final int maxReserved = 3;
    private ArrayList<Book> borrowedBooks;
    private ArrayList<Book> reservedBooks;

    //Constructor
    public Student(int student_id, String name, String email, String major, boolean notifyByEmail) {
        super(student_id, name, email, notifyByEmail);
        this.major = major;
        this.borrowedBooks = new ArrayList<Book>();
        this.reservedBooks = new ArrayList<Book>();
    }

    //Getters and setters
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public ArrayList<Book> getReservedBooks() {
        return reservedBooks;
    }

    //Methods
    public void addBorrowedBook(Book book) {
        if (borrowedBooks.size() >= maxBorrowed) {
            System.out.println("Error: Cannot borrow more than " + maxBorrowed + " books.");
            return;
        }
        borrowedBooks.add(book);
    }

    public void addReservedBook(Book book) throws ReservationLimitException {
        if(reservedBooks.size()>=maxReserved) {
            throw new ReservationLimitException("Error: Cannot reserve more than " + maxReserved + " books.");
        }
        reservedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    public void removeReservedBook(Book book) {
        reservedBooks.remove(book);
    }

}