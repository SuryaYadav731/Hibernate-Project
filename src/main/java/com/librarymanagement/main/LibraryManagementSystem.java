package com.librarymanagement.main; // Adjust this as necessary

import com.librarymanagement.dao.UserDAO;
import com.librarymanagement.dao.BookDAO;
import com.librarymanagement.dao.BorrowingDAO;
import com.librarymanagement.model.User;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.Borrowing;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        BorrowingDAO borrowingDAO = new BorrowingDAO();

        while (true) {
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Add Book (Admin)");
            System.out.println("4. View Books");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    userDAO.addUser(newUser);
                    System.out.println("User registered successfully!");
                    break;

                case 2:
                    System.out.println("Enter username:");
                    String loginUsername = scanner.nextLine();
                    System.out.println("Enter password:");
                    String loginPassword = scanner.nextLine();

                    if (userDAO.authenticateUser(loginUsername, loginPassword)) {
                        System.out.println("Login successful! Welcome, " + loginUsername + "!");
                    } else {
                        System.out.println("Invalid username or password!");
                    }
                    break;

                case 3:
                    System.out.println("Enter book title:");
                    String title = scanner.nextLine();
                    System.out.println("Enter author:");
                    String author = scanner.nextLine();
                    System.out.println("Enter published date (yyyy-mm-dd):");
                    String dateStr = scanner.nextLine();
                    Date publishedDate = Date.valueOf(dateStr);
                    System.out.println("Enter available copies:");
                    int availableCopies = scanner.nextInt();
                    Book newBook = new Book();
                    newBook.setTitle(title);
                    newBook.setAuthor(author);
                    newBook.setPublishedDate(publishedDate);
                    newBook.setAvailableCopies(availableCopies);
                    bookDAO.addBook(newBook);
                    System.out.println("Book added successfully!");
                    break;

                case 4:
                    List<Book> books = bookDAO.getAllBooks();
                    System.out.println("Available Books:");
                    for (Book book : books) {
                        System.out.println(book.getId() + ": " + book.getTitle() + " by " + book.getAuthor());
                    }
                    break;

                case 5:
                    System.out.println("Enter your username:");
                    String borrowerUsername = scanner.nextLine();
                    User borrower = userDAO.getUser(borrowerUsername);
                    System.out.println("Enter book ID to borrow:");
                    int bookId = scanner.nextInt();
                    Book bookToBorrow = bookDAO.getBook(bookId);
                    if (bookToBorrow != null && bookToBorrow.getAvailableCopies() > 0) {
                        Borrowing borrowing = new Borrowing();
                        borrowing.setUser(borrower);
                        borrowing.setBook(bookToBorrow);
                        borrowing.setBorrowDate(new java.util.Date());
                        borrowingDAO.borrowBook(borrowing);
                        bookToBorrow.setAvailableCopies(bookToBorrow.getAvailableCopies() - 1);
                        bookDAO.updateBook(bookToBorrow);
                        System.out.println("Book borrowed successfully!");
                    } else {
                        System.out.println("Book not available.");
                    }
                    break;

                case 6:
                    System.out.println("Enter your username:");
                    String returnerUsername = scanner.nextLine();
                    User returner = userDAO.getUser(returnerUsername);
                    System.out.println("Enter book ID to return:");
                    int returnBookId = scanner.nextInt();
                    Borrowing returnBorrowing = borrowingDAO.getBorrowing(returner.getId(), returnBookId);
                    if (returnBorrowing != null) {
                        borrowingDAO.returnBook(returnBorrowing);
                        Book bookToReturn = bookDAO.getBook(returnBookId);
                        bookToReturn.setAvailableCopies(bookToReturn.getAvailableCopies() + 1);
                        bookDAO.updateBook(bookToReturn);
                        System.out.println("Book returned successfully!");
                    } else {
                        System.out.println("No borrowing record found for this user.");
                    }
                    break;

                case 7:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
