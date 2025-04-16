package com.library.controller;

import com.library.database.BookDAOImplementation;
import com.library.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Vector;

public class BookController {

    // Add book
    public static boolean addBook(String title, String author, String ISBN, Book.Status status) throws SQLIntegrityConstraintViolationException, SQLException {
        //Getting the current Date and time
        LocalDate date = LocalDate.now();

        // Creating a new Book instance
        Book newBook = new Book(title, author, ISBN, status, date);

        // Storing the confirmation of book added (True or False)
        return BookDAOImplementation.addBook(newBook);
    }

    // Delete Book
    public static boolean deleteSelectedBook(String isbn) {
        boolean isDeleted = false;
        if (isbn != null) {
            isDeleted = BookDAOImplementation.deleteBook(isbn);
        }
        return isDeleted;
    }

    // Find Book
    public static Vector<Vector<Object>> findBook(String bookInfo) {
        return BookDAOImplementation.findBook(bookInfo);
    }

    // Books Table Model Class
    public class BooksTableUtil {
        private static String[] columnNames = {"Title", "Author", "ISBN", "Status", "Added Date"};

        public static DefaultTableModel createTableModel(String[] columnNames) {
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            Vector<Vector<Object>> books = BookDAOImplementation.getAllBooks();
            // Take each book and add it to the table model.
            for (Vector<Object> row : books) {
                tableModel.addRow(row);
            }
            return tableModel;
        }

        public static DefaultTableModel createTableModel(String[] columnNames, Vector<Vector<Object>> books) {
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            // Take each book and add it to the table model.
            for (Vector<Object> row : books) {
                tableModel.addRow(row);
            }
            return tableModel;
        }

        public static int fetchTableData(JTable booksTable) {
            setTableColumnStyles(booksTable);
            booksTable.setModel(BookController.BooksTableUtil.createTableModel(columnNames));
            booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            return 0;
        }

        public static int fetchTableData(JTable booksTable, Vector<Vector<Object>> books) {
            setTableColumnStyles(booksTable);
            booksTable.setModel(BookController.BooksTableUtil.createTableModel(columnNames,
                    books));
            booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            // If there is data
            if (booksTable.getModel().getRowCount() > 0) {
                return 1;
            }
            return 0;
        }

        public static void setTableColumnStyles(JTable booksTable) {
            booksTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();

            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

            //Changed alignment of the records to center, Except the first two columns
            for (int i = 1; i < booksTable.getColumnCount(); i++) {
                booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }

    }
}
