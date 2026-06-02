import java.util.ArrayList;

public class LibrarySystem {
    // This is where your list actually lives
    private static ArrayList<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        // Example: Adding a valid book
        addBookToList(1, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 11111, 5, 1937);
        
        // Example: Trying to add a book with a duplicate ID
        addBookToList(1, "Duplicate ID Book", "Some Author", "Fiction", 22222, 2, 2020);

        // Example: Trying to add a book with a duplicate ISBN
        addBookToList(2, "Duplicate ISBN Book", "Another Author", "Sci-Fi", 11111, 3, 2021);
    }

    public static void addBookToList(int id, String title, String author, String category, int isbn, int copies, int date) {
        // Check for uniqueness before creating or adding the book
        for (Book b : bookList) {
            if (b.getBook_id() == id) {
                System.out.println("Error: Book ID " + id + " already exists. Cannot add " + title);
                return; // Stop the method execution
            }
            if (b.getIsbn() == isbn) {
                System.out.println("Error: ISBN " + isbn + " already exists. Cannot add " + title);
                return; // Stop the method execution
            }
        }

        // If it passes both checks, it's safe to add!
        Book newBook = new Book(id, title, author, category, isbn, copies, date);
        bookList.add(newBook);
        System.out.println("Success: Added " + title + " to the system.");
    }
}