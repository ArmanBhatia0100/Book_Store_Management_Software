package com.library.services;

import com.library.database.BookDAOImplementation;
import com.library.model.Book;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;

public class BookService {

    // Books based functions
    public static void getBookByISBN(String ISBN) {
        Vector<Vector<Object>> books = BookDAOImplementation.getBookByISBN(ISBN);
    }

    public static boolean addBook(String title, String author, String ISBN, Book.Status status) throws SQLIntegrityConstraintViolationException, SQLException {
        //Getting the current Date and time
        LocalDate date = LocalDate.now();

        // Creating a new Book instance
        Book newBook = new Book(title, author, ISBN, status, date);

        // Storing the confirmation of book added (True or False)
        return BookDAOImplementation.addBook(newBook);
    }

    void deleteBookByISBN(String ISBN) {

        boolean isDeleted = BookDAOImplementation.deleteBook(ISBN);

        if (isDeleted) {
            JOptionPane.showMessageDialog(null, "Book Deleted");

        } else {
            JOptionPane.showMessageDialog(null, "NOT deleted");
        }

    }

}
