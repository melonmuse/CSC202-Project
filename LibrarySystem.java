import java.util.ArrayList;
import java.util.Scanner;
public class LibrarySystem {
    static ArrayList<Book> bookList = new ArrayList<>();
    static ArrayList<Student> studentList = new ArrayList<>();
    static ArrayList<Librarian> librarianList = new ArrayList<>();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Library System!");
        while (true) {
            // Display menu and handle user input for different operations
            System.out.println("Please select an option:");
            System.out.println("1. Add a Book");
            System.out.println("2. Add a Student");
            System.out.println("3. Add a Librarian");
            System.out.println("4. Borrow a Book");
            System.out.println("5. View Books");
            System.out.println("6. View Students");
            System.out.println("7. View Librarians");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1:
                    // Code to add a book
                    System.out.print("Enter Book ID: ");
                    int bookId = input.nextInt();
                    input.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = input.nextLine();
                    System.out.print("Enter Author: ");
                    String author = input.nextLine();
                    System.out.print("Enter Category: ");
                    String category = input.nextLine();
                    System.out.print("Enter ISBN: ");
                    int isbn = input.nextInt();
                    System.out.print("Enter Number of Copies: ");
                    int copies = input.nextInt();
                    System.out.print("Enter Publication Date (Year): ");
                    int date = input.nextInt();
                    addBookToList(bookId, title, author, category, isbn, copies, date);
                    break;
                case 2:
                    // Code to add a student
                    System.out.print("Enter Student ID: ");
                    int studentId = input.nextInt();
                    Student tempStudent = new Student();
                    tempStudent.setStudent_id(studentId);
                    input.nextLine();
                    System.out.print("Enter Student Name: ");
                    String studentName = input.nextLine();
                    tempStudent.setName(studentName);
                    System.out.print("Enter Student Email: ");
                    String studentEmail = input.nextLine();
                    tempStudent.setEmail(studentEmail);
                    System.out.print("Enter Student Major: ");
                    String studentMajor = input.nextLine();
                    tempStudent.setMajor(studentMajor);
                    studentList.add(tempStudent);
                    System.out.println("Success: Added " + studentName + " to the system.");
                    break;
                case 3:
                    // Code to add a librarian
                    System.out.print("Enter Librarian ID: ");
                    int librarianId = input.nextInt();
                    Librarian tempLibrarian = new Librarian(librarianId, null, null, 0);
                    input.nextLine();
                    System.out.print("Enter Librarian Name: ");
                    String librarianName = input.nextLine();
                    tempLibrarian.setName(librarianName);
                    System.out.print("Enter Librarian Email: ");
                    String librarianEmail = input.nextLine();
                    tempLibrarian.setEmail(librarianEmail);
                    System.out.print("Enter Librarian Staff Number: ");
                    int staffNumber = input.nextInt();
                    tempLibrarian.setStaffNumber(staffNumber);
                    librarianList.add(tempLibrarian);
                    System.out.println("Success: Added " + librarianName + " to the system.");
                    break;
                case 4:
                    // Code to borrow a book
                    System.out.print("What is the Student ID? ");
                    int borrowerId = input.nextInt();
                    Student borrower = null;
                    for (Student s : studentList) {
                        if (s.getStudent_id() == borrowerId) {
                            borrower = s;
                            break;
                        }
                    }
                    if (borrower == null) {
                        System.out.println("Error: Student with ID " + borrowerId + " not found.");
                        break;
                    }
                    System.out.print("What is the Book ID? ");
                    int litID = input.nextInt();
                    Book book = null;
                    for (Book b : bookList) {
                        if (b.getBook_id() == litID) {
                            book = b;
                            break;
                        }
                    }
                    if (book == null) {
                        System.out.println("Error: Book with ID " + litID + " not found.");
                        break;
                    }
                    if (book.getNumOfCopies() <= 0) {
                        System.out.println("Error: No copies of the book available.");
                        break;
                    }
                    book.setNumOfCopies(book.getNumOfCopies() - 1);
                    System.out.println("Success: " + borrower.getName() + " borrowed " + book.getBookTitle());
                    break;
                case 5:
                    // Code to view books
                    System.out.println("Books in the Library:");
                    for (Book b : bookList) {
                        System.out.println("ID: " + b.getBook_id() + ", Title: " + b.getBookTitle() + ", Author: " + b.getAuthor() + ", Category: " + b.getCategory() + ", ISBN: " + b.getIsbn() + ", Copies: " + b.getNumOfCopies() + ", Publication Date: " + b.getPublicationDate());
                    }
                    break;
                case 6:
                    // Code to view students
                    System.out.println("Students in the Library System:");
                    for (Student s : studentList) {
                        System.out.println("ID: " + s.getStudent_id() + ", Name: " + s.getName() + ", Email: " + s.getEmail() + ", Major: " + s.getMajor());
                    }               
                    break;            
                case 7:
                    // Code to view librarians
                    System.out.println("Librarians in the Library System:");
                    for (Librarian l : librarianList) {
                        System.out.println("ID: " + l.getLibrarian_id() + ", Name: " + l.getName() + ", Email: " + l.getEmail());
                    }
                    break;
                case 8:
                    System.out.println("Exiting the Library System. Goodbye!");
                    input.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void addBookToList(int id, String title, String author, String category, int isbn, int copies, int date) {
        //Making sure bookID and isbn are unique
        for (Book b : bookList) {
            if (b.getBook_id() == id) {
                System.out.println("Error: Book ID " + id + " already exists. Cannot add " + title);
                return; 
            }
            if (b.getIsbn() == isbn) {
                System.out.println("Error: ISBN " + isbn + " already exists. Cannot add " + title);
                return;
            }
        }

        // Adding book if unique
        Book newBook = new Book(id, title, author, category, isbn, copies, date);
        bookList.add(newBook);
        System.out.println("Success: Added " + title + " to the system.");
    }

}