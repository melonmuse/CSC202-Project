package Interfaces;
import java.util.ArrayList;
import Classes.Book;

public class BookSearchEngine implements IBookSearch {
    private ArrayList<Book> catalog;

    public BookSearchEngine(ArrayList<Book> catalog) {
        this.catalog = catalog;
    }
    @Override
    public ArrayList<Book> searchByTitle(String title) {
        ArrayList<Book> results = new ArrayList<Book>();
        for (Book book : catalog) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(book);
            }
        }
        sortByTitle(results);
        return results;
    }

    @Override
    public ArrayList<Book> searchByAuthor(String author) {
        ArrayList<Book> results = new ArrayList<Book>();
        for (Book book : catalog) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(book);
            }
        }
        sortByTitle(results);
        return results;
    }

    @Override
    public ArrayList<Book> filterByCategory(String category) {
        ArrayList<Book> results = new ArrayList<Book>();
        for (Book book : catalog) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                results.add(book);
            }
        }
        sortByTitle(results);
        return results;
    }

    @Override
    public ArrayList<Book> findAvailableBooks() {
        ArrayList<Book> results = new ArrayList<Book>();
        for (Book book : catalog) {
            if (book.getAvailableCopies() > 0) {
                results.add(book);
            }
        }
        sortByTitle(results);
        return results;
    }

    private void sortByTitle(ArrayList<Book> books) {
        for (int i = 0; i < books.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < books.size(); j++) {
                if (books.get(j).getTitle()
                        .compareToIgnoreCase(books.get(minIndex).getTitle()) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Book temp = books.get(i);
                books.set(i, books.get(minIndex));
                books.set(minIndex, temp);
            }
        }
    }
}