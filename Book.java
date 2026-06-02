public class Book {
    //Define variables
    private int book_id;
    private String bookTitle;
    private String author;
    private String category;
    private int isbn;
    private int numOfCopies;
    private int publicationDate;

    //Constructor
    Book(int book_id, String bookTitle, String author, String category, int isbn, int numOfCopies, int publicationDate) {
        this.book_id=book_id;
        this.bookTitle=bookTitle;
        this.author=author;
        this.category=category;
        this.isbn=isbn;
        this.numOfCopies=numOfCopies;
        this.publicationDate=publicationDate;
    }

    //Getters and Setters
    public int getBook_id() {
        return book_id;
    }
    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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
    public int getIsbn() {
        return isbn;
    }
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }
    public int getNumOfCopies() {
        return numOfCopies;
    }
    public void setNumOfCopies(int numOfCopies) {
        this.numOfCopies = numOfCopies;
    }
    public int getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }
    
}
