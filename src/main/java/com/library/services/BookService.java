package com.library.services;

import com.library.database.BookDAOImplementation;
import com.library.model.Book;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Vector;

public class BookService {

    // Books based functions
    public static void getBookByISBN(String ISBN) {
        Vector<Vector<Object>> books = BookDAOImplementation.findBook(ISBN);
    }

    public static boolean addBook(String title, String author, String ISBN, Book.Status status) throws SQLIntegrityConstraintViolationException, SQLException {
        //Getting the current Date and time
        LocalDate date = LocalDate.now();

        // Creating a new Book instance
        Book newBook = new Book(title, author, ISBN, status, date);

        // Storing the confirmation of book added (True or False)
        return BookDAOImplementation.addBook(newBook);
    }

    public static boolean deleteSelectedBook(String isbn) {
        boolean isDeleted = false;
        if (isbn != null) {
            isDeleted = BookDAOImplementation.deleteBook(isbn);
        }
        return isDeleted;
    }

}
