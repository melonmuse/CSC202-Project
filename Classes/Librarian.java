package Classes;
import java.util.ArrayList;

public class Librarian extends User {
    //Define variables
    private int librarian_id;
    private String name;
    private String email;
    private int staffNumber;
    private ArrayList<Book> managedBooks;

    //Constructor
    public Librarian(int librarian_id, String name, String email, int staffNumber) {
        super(librarian_id, name, email); 
        this.staffNumber = staffNumber;
        this.managedBooks = new ArrayList<Book>();
    }

    //Getters and setters
    public int getLibrarian_id() {
        return librarian_id;
    }
    public void setLibrarian_id(int librarian_id) {
        this.librarian_id = librarian_id;
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
    public int getStaffNumber() {
        return staffNumber;
    }
    public void setStaffNumber(int staffNumber) {
        this.staffNumber = staffNumber;
    }
    public ArrayList<Book> getManagedBooks() {
        return managedBooks;
    }
    public void setManagedBooks(Book managedBooks) {
        this.managedBooks.add(managedBooks);
    }
        
}
