package Classes;
import java.util.ArrayList;

public class Librarian extends User {
    //Define variables
    private int staffNumber;
    private ArrayList<Book> managedBooks;

    //Constructor
    public Librarian(int librarian_id, String name, String email, int staffNumber, boolean notifyByEmail) {
        super(librarian_id, name, email, notifyByEmail); 
        this.staffNumber = staffNumber;
        this.managedBooks = new ArrayList<Book>();
    }

    //Getters and setters
    public int getStaffNumber() {
        return staffNumber;
    }
    public void setStaffNumber(int staffNumber) {
        this.staffNumber = staffNumber;
    }
    public ArrayList<Book> getManagedBooks() {
        return managedBooks;
    }
    public void addManagedBook(Book book) {
        this.managedBooks.add(book);
    }
    public String getUserType() {
        return "Librarian";
    }
        
}
