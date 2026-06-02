import java.util.ArrayList;
public class Student extends User {
    //Define variables
    private int student_id;
    private String name;
    private String email;
    private String major;
    private ArrayList<Book> borrowedBooks;
    private ArrayList<Book> reservedBooks;

    //Constructor
    public Student() {
        super(0, null, null);   
    }

    public Student(int student_id, String name, String email, String major) {
        super(student_id, name, email); 
        this.major = major;
        this.borrowedBooks = new ArrayList<>();
        this.reservedBooks = new ArrayList<>();
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
    

}
