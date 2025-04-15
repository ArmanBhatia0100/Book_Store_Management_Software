package com.library.view.Books;

import com.library.controller.BookController;
import com.library.model.Book;
import com.library.view.utils.DateTimeUpdater;
import com.library.view.utils.InputValidator;
import com.library.view.utils.JOptionsUtils;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
        DateTimeUpdater.getDateAndTime();
        setupEventListeners();
        BookController.BooksTableUtil.fetchTableData(booksTable);
        BookController.BooksTableUtil.setTableColumnStyles(booksTable);
        setVisible(true);

        //Table setup
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupEventListeners() {
        searchButton.addActionListener(e -> findBook());
        addButton.addActionListener(e -> addBook());
        deleteBookButton.addActionListener(e -> deleteSelectedBook());
    }

    private void findBook() {
        String bookInfo = tFSearch.getText();
        Vector<Vector<Object>> books = BookController.findBook(bookInfo);

        int row = books.size();

        if (row > 0) {
            BookController.BooksTableUtil.fetchTableData(booksTable, books);
            tFSearch.setText("");
        } else {
            JOptionsUtils.showMessageBox("Book not found");
            refreshTableAndClearFields();
        }

    }

    private void deleteSelectedBook() {
        if (booksTable.getSelectedRow() == -1) {
            JOptionsUtils.showMessageBox("Please select a book to delete.");
            return;
        }
        int row = getSelectedRow();
        String ISBN = (String) booksTable.getValueAt(row, 2);
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
        Boolean validated = InputValidator.emptyInputChecker(title, author, isbn);

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

        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionsUtils.showMessageBox(e.getMessage().toString());
        } catch (SQLException e) {
            JOptionsUtils.showMessageBox(e.getMessage().toString());
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
        comboStatus.setSelectedIndex(0);
    }
    
}

