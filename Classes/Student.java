package Classes;
import java.util.ArrayList;
import Exceptions.ReservationLimitException;

public class Student extends User {
    //Define variables
    private int student_id;
    private String name;
    private String email;
    private String major;
    public static final int maxBorrowed = 5;
    public static final int maxReserved = 3;
    private ArrayList<Book> borrowedBooks;
    private ArrayList<Book> reservedBooks;

    //Constructor
    public Student() {
        super(0, null, null);   
    }

    public Student(int student_id, String name, String email, String major) {
        super(student_id, name, email); 
        this.major = major;
        this.borrowedBooks = new ArrayList<Book>();
        this.reservedBooks = new ArrayList<Book>();
    }
    
    //Getters and setters
    public int getStudent_id() {
        return student_id;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    public void setBorrowedBooks(Book borrowedBooks) {
        this.borrowedBooks.add(borrowedBooks);
    }
    public ArrayList<Book> getReservedBooks() {
        return reservedBooks;
    }
    public void setReservedBooks(Book reservedBooks) {
        this.reservedBooks.add(reservedBooks);
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
            throw new ReservationLimitException("Error: Cannot reserve more than " + maxReserved + " books."); //Need to create this exception class
        }
        reservedBooks.add(book);
    }

    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

}
