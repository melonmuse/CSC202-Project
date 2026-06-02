package Classes;
import java.time.Year;
import Exceptions.InvalidBookDataException;

public class Book {
    //Define variables
    private int book_id;
    private String title;
    private String author;
    private String category;
    private String isbn;
    private int availableCopies;
    private int publicationDate;

    //Constructor
    public Book(int book_id, String title, String author, String category, String isbn, int availableCopies, int publicationDate) throws InvalidBookDataException {
        this.book_id=book_id;
        this.title=title;
        this.author=author;
        this.category=category;
        this.isbn=isbn;
        setAvailableCopies(availableCopies);
        setPublicationDate(publicationDate);
    }

    //Getters and Setters
    public int getBook_id() {
        return book_id;
    }
    public void setBook_id(int book_id) {
        this.book_id = book_id;
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
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(int availableCopies) throws InvalidBookDataException {
        if (availableCopies < 0) {
            throw new InvalidBookDataException("Number of copies cannot be negative."); //Need to create this custom exception class
        }
        this.availableCopies = availableCopies;
    }
    public int getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(int publicationDate) throws InvalidBookDataException {
        int currentYear = Year.now().getValue();
        if (publicationDate < 1000 || publicationDate > currentYear) {
            throw new InvalidBookDataException("Invalid publication year: " + publicationDate);
        }
        this.publicationDate = publicationDate;
    }
    
}
