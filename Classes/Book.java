package Classes;
import java.time.Year;
import Exceptions.InvalidBookDataException;

public class Book {
    //Define variables
    private int bookID;
    private String title;
    private String author;
    private String category;
    private String ISBN;
    private int availableCopies;
    private int publicationYear;

    //Constructor
    public Book(int bookID, String title, String author, String category, String ISBN, int availableCopies, int publicationYear) throws InvalidBookDataException {
        this.bookID=bookID;
        this.title=title;
        this.author=author;
        this.category=category;
        this.ISBN=ISBN;
        setAvailableCopies(availableCopies);
        setPublicationYear(publicationYear);
    }

    //Getters and Setters
    public int getBookID() {
        return bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(int availableCopies) throws InvalidBookDataException {
        if (availableCopies < 0) {
            throw new InvalidBookDataException("Number of copies cannot be negative."); 
        }
        this.availableCopies = availableCopies;
    }
    public int getPublicationYear() {
        return publicationYear;
    }
    public void setPublicationYear(int publicationYear) throws InvalidBookDataException {
        int currentYear = Year.now().getValue();
        if (publicationYear > currentYear) {
            throw new InvalidBookDataException("Invalid publication year: " + publicationYear);
        }
        this.publicationYear = publicationYear;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }
    
}
