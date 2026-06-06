package Interfaces;
import java.util.ArrayList;
import Classes.Book;
public interface IBookSearch {

    /** Search for books whose title contains the given text. */
    ArrayList<Book> searchByTitle(String title);

    /** Search for books whose author contains the given text. */
    ArrayList<Book> searchByAuthor(String author);

    /** Return all books in the given category. */
    ArrayList<Book> filterByCategory(String category);

    /** Return only the books that currently have available copies. */
    ArrayList<Book> findAvailableBooks();
}
