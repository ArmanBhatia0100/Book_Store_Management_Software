package com.library.view.Books;

import com.library.controller.BookController;
import com.library.model.Book;
import com.library.utils.BooksPageUtils.DateTimeUpdater;
import com.library.utils.BooksPageUtils.FileChooser;
import com.library.utils.BooksPageUtils.InputValidator;
import com.library.utils.BooksPageUtils.JOptionsUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Vector;


public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JTextField tFTitle;
    private JTextField tFAuthor;
    private JTextField tFISBN;
    private JButton addButton;
    private JTable booksTable;
    private JScrollPane bookTableScroll;
    private JComboBox comboStatus;
    private JPanel formPanel;
    private JButton findByISBNButton;
    private JLabel GMtime;
    private JLabel dayAndDate;
    private JTextField tFSearch;
    private JButton searchButton;
    private JButton deleteBookButton;
    private JButton PDFButton;
    private JButton excelButton;

    // Class fields
    private String title;
    private String author;
    private String isbn;
    private Book.Status status;


    public MainFrame() {
        initialSetup();

    }

    private void initialSetup() {
        setSize(new Dimension(800, 600));
        add(mainPanel);
        this.setLocationRelativeTo(MainFrame.this);

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

        if (!books.isEmpty()) {
            BookController.BooksTableUtil.fetchTableData(booksTable, books);
        } else {
            JOptionsUtils.showMessageBox("Book not found");
        }

        refreshTableAndClearFields();
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

