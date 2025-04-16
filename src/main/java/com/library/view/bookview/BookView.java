package com.library.view.bookview;

import com.library.controller.BookController;
import com.library.model.Book;
import com.library.utils.BooksPageUtils.DateTimeUpdater;
import com.library.utils.BooksPageUtils.FileChooser;
import com.library.utils.BooksPageUtils.InputValidator;
import com.library.utils.BooksPageUtils.JOptionsUtils;
import com.library.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;

public class BookView extends JPanel {
    private JPanel bookViewPanel;
    private JTextField tFSearch;
    private JButton searchButton;
    private JComboBox comboBox1;
    private JTable booksTable;
    private JButton PDFButton;
    private JButton deleteBookButton;
    private JLabel dayAndDate;
    private JTextField tFTitle;
    private JTextField tFAuthor;
    private JTextField tFISBN;
    private JButton addButton;
    private JComboBox comboStatus;

    // Class fields
    private String title;
    private String author;
    private String isbn;
    private Book.Status status;


    public BookView() {
        setLayout(new BorderLayout());
        add(bookViewPanel);
        initialSetup();
    }

    private void initialSetup() {
        DateTimeUpdater.startDateTimeUpdate(dayAndDate);
        setupEventListeners();

        BookController.BooksTableUtil.fetchTableData(booksTable);
        BookController.BooksTableUtil.setTableColumnStyles(booksTable);

        setVisible(true);
    }

    private void setupEventListeners() {
        searchButton.addActionListener(e -> findBook());
        addButton.addActionListener(e -> addBook());
        deleteBookButton.addActionListener(e -> deleteSelectedBook());
        PDFButton.addActionListener(e -> FileChooser.getPDFFilePath(booksTable));
    }

    private void findBook() {
        String bookInfo = tFSearch.getText();
        Vector<Vector<Object>> books = BookController.findBook(bookInfo);

        if (books.size() > 0) {
            BookController.BooksTableUtil.fetchTableData(booksTable, books);
        } else {
            JOptionsUtils.showMessageBox("Book not found");
        }
    }

    private void deleteSelectedBook() {

        // Not Selected
        if (getSelectedRow() == -1) {
            JOptionsUtils.showMessageBox("Please select a book to delete.");
            return;
        }

        String ISBN = (String) booksTable.getValueAt(getSelectedRow(), 2);

        if (ISBN != null) {
            boolean isDeleted = BookController.deleteSelectedBook(ISBN);
            if (isDeleted) {
                JOptionsUtils.showMessageBox("Book deleted successfully");
                tFTitle.requestFocus();
            } else {
                JOptionsUtils.showMessageBox("Book Not Deleted");
            }
            refreshTableAndClearFields();
        } else {
            JOptionsUtils.showMessageBox("Select a Book from the table");
        }

    }

    private void addBook() {
        title = tFTitle.getText();
        author = tFAuthor.getText();
        isbn = tFISBN.getText();
        status = Book.Status.valueOf(comboStatus.getSelectedItem().toString().toUpperCase());

        // Validating the input
        boolean validated = InputValidator.emptyInputChecker(title, author, isbn);

        if (!validated) {
            JOptionsUtils.showMessageBox("All Fields Must Be Filled");
            return;
        }

        // Adding books to the backend
        try {
            boolean success = BookController.addBook(title, author, isbn, status);

            if (success) {
                JOptionsUtils.showMessageBox("Book added successfully");
            }

        } catch (SQLException e) {
            JOptionsUtils.showMessageBox(e.getMessage());
        } finally {
            refreshTableAndClearFields();
        }
    }

    private int getSelectedRow() {
        int row = booksTable.getSelectedRow();
        return Math.max(row, -1);
    }

    private void refreshTableAndClearFields() {
        BookController.BooksTableUtil.fetchTableData(booksTable);
        tFTitle.setText("");
        tFAuthor.setText("");
        tFISBN.setText("");
        tFSearch.setText("");
        comboStatus.setSelectedIndex(0);
    }

}
