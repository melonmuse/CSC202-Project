public class Main {
    public static void main(String args[]) {
        System.out.println("Hello");
        System.out.println("Testing things out here!");
        int num =2;
        if(num==2) {
            System.out.println("It is 2");
        }
        else {
            System.out.println("No");
        }
        Book book1 = new Book("Harry Potter");
        System.out.println("Book Name: " + book1.bookName);
        System.out.println("Book Author: " + book1.author);
        book1.author = "JK Rowling";
        System.out.println("Book Author: " + book1.author);
    }
}