package com.library.view.Books;

import com.library.database.BookDAOImplementation;
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
    private JButton bookManagementButton;
    private JPanel mainPanel;
    private JButton memberManagementButton;
    private JButton reportsButton;
    private JButton exportButton;
    private JTextField tFBookID;
    private JTextField tFTitle;
    private JTextField tFAuthor;
    private JTextField tFISBN;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable booksTable;
    private JScrollPane bookTableScroll;
    private JComboBox comboStatus;
    private JPanel formPanel;
    private JButton findByISBNButton;
    private JLabel GMtime;
    private JLabel dayAndDate;
    private JTextField searchByTitleAuthorTextField;
    private JButton searchButton;

    // Class fields
    private String title;
    private String author;
    private String isbn;
    private Book.Status status;
    private String[] columnNames = {"Title", "Author", "ISBN", "Status", "Added Date"};

    public MainFrame() {
        initailSetup();
    }

    // General Functions
    void initailSetup() {
        setSize(new Dimension(800, 600));
        add(mainPanel);
        this.setLocationRelativeTo(MainFrame.this);
        getDateAndTime();
        setupEventListeners();
        fetchTableData();
        setTableColumnStyles();
        setVisible(true);
    }

    private void setupEventListeners() {
        findByISBNButton.addActionListener(e -> getBookByISBN());
        addButton.addActionListener(e -> addBook());
        deleteButton.addActionListener(e -> deleteBookByISBN());
        // Add searchButton listener if implemented
    }


    void getDateAndTime() {
        DateTimeUpdater.startDateTimeUpdate(dayAndDate);
    }

    void fetchTableData() {
        // setting the entire model to the table
        booksTable.setModel(new BooksTableUtil().createTableModel(columnNames));
    }

    int fetchTableData(Vector<Vector<Object>> books) {
        // setting the entire model to the table
        booksTable.setModel(new BooksTableUtil().createTableModel(columnNames,
                books));
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

    // Books based functions
    void getBookByISBN() {
        String ISBN = tFISBN.getText();
        int row = fetchTableData(BookDAOImplementation.getBookByISBN(ISBN));

        if (row < 1) {
            showMessageBox("ISBN not found");
        }
    }

    void addBook() {
        title = tFTitle.getText();
        author = tFAuthor.getText();
        isbn = tFISBN.getText();
        status = comboStatus.getSelectedItem().toString().equals("Available") ? Book.Status.AVAILABLE : Book.Status.ISSUED;
        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
            showMessageBox("All Fields Must Be Filled");
            return;
        }
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
            fetchTableData();
            resetAllTextFields();
        }
    }

    void deleteBookByISBN() {
        isbn = tFISBN.getText();
        boolean isDeleted = BookDAOImplementation.deleteBook(isbn);
        if (isDeleted) {
            showMessageBox("Book deleted successfully");
        } else {
            showMessageBox("Book Not Deleted");
        }
        fetchTableData();
        resetAllTextFields();
    }

    void showMessageBox(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    void resetAllTextFields() {
        tFTitle.setText("");
        tFAuthor.setText("");
        tFISBN.setText("");
        comboStatus.setSelectedIndex(0);
    }
}

