package com.library.view.Books;

import com.library.model.Book;
import com.library.services.BookService;
import com.library.view.utils.BooksTableUtil;
import com.library.view.utils.DateTimeUpdater;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
    private JButton deleteButton;
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
    private String[] columnNames = {"Title", "Author", "ISBN", "Status", "Added Date"};

    public MainFrame() {
        initialSetup();
    }

    void initialSetup() {
        setSize(new Dimension(800, 600));
        add(mainPanel);
        this.setLocationRelativeTo(MainFrame.this);
        getDateAndTime();
        setupEventListeners();
        fetchTableData();
        setTableColumnStyles();
        setVisible(true);

        //Table setup
        booksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setupEventListeners() {
        searchButton.addActionListener(e -> findBook());
        addButton.addActionListener(e -> addBook());
        deleteButton.addActionListener(e -> deleteSelectedBook());
    }

    void getDateAndTime() {
        DateTimeUpdater.startDateTimeUpdate(dayAndDate);
    }

    void fetchTableData() {
        booksTable.setModel(new BooksTableUtil().createTableModel(columnNames));
    }

    int fetchTableData(Vector<Vector<Object>> books) {
        booksTable.setModel(new BooksTableUtil().createTableModel(columnNames,
                books));
        // If there is data
        if (booksTable.getModel().getRowCount() > 0) {
            return 1;
        }
        return 0;
    }

    void setTableColumnStyles() {
        booksTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        //Changed alignment of the records to center, Except the first two columns
        for (int i = 1; i < booksTable.getColumnCount(); i++) {
            booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    void findBook() {
        String bookInfo = tFSearch.getText();
        Vector<Vector<Object>> books = BookService.findBook(bookInfo);

        int row = books.size();

        if (row > 0) {
            fetchTableData(books);
            tFSearch.setText("");
        } else {
            showMessageBox("Book not found");
            refreshTableAndClearFields();
        }

    }

    void deleteSelectedBook() {
        if (booksTable.getSelectedRow() == -1) {
            showMessageBox("Please select a book to delete.");
            return;
        }
        int row = getSelectedRow();
        String ISBN = (String) booksTable.getValueAt(row, 2);
        if (ISBN != null) {
            boolean isDeleted = BookService.deleteSelectedBook(ISBN);
            if (isDeleted) {
                showMessageBox("Book deleted successfully");
                tFTitle.requestFocus();
            } else {
                showMessageBox("Book Not Deleted");
            }
            refreshTableAndClearFields();
        } else {
            showMessageBox("Select a Book from the table");
        }
    }

    void addBook() {
        title = tFTitle.getText();
        author = tFAuthor.getText();
        isbn = tFISBN.getText();
        status = Book.Status.valueOf(comboStatus.getSelectedItem().toString().toUpperCase());
        ;

        // Fields must not be empty; show dialog if they are empty
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            showMessageBox("All Fields Must Be Filled");
            return;
        }

        // Adding books to the backend
        try {
            boolean isAdded = BookService.addBook(title, author, isbn, status);
            if (isAdded) {
                showMessageBox("Book added successfully");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            showMessageBox(e.getMessage().toString());
        } catch (SQLException e) {
            showMessageBox(e.getMessage().toString());
        } finally {
            refreshTableAndClearFields();
        }
    }

    void showMessageBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    int getSelectedRow() {
        int row = booksTable.getSelectedRow();
        if (row >= 0) {
            return row;
        } else {
            return 0;
        }
    }

    void refreshTableAndClearFields() {
        fetchTableData();
        resetAllTextFields();
    }

    void resetAllTextFields() {
        tFTitle.setText("");
        tFAuthor.setText("");
        tFISBN.setText("");
        comboStatus.setSelectedIndex(0);
    }
}

