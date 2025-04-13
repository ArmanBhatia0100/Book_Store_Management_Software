package com.library.view.Books;

import com.library.database.BookDAOImplementation;
import com.library.model.Book;
import com.library.services.BookService;
import com.library.view.utils.DateTimeUpdater;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setVisible(true);

        getDateAndTime();

        findByISBNButton.addActionListener(e -> {
            getBookByISBN();
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBookByISBN();
            }
        });

        // fetch current data from the db.
        fetchTableData();

    }

    void getDateAndTime() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dayAndDate.setText(DateTimeUpdater.getDateAndTime());
            }
        });
        timer.start();
    }

    void fetchTableData() {

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        Vector<Vector<Object>> books = BookDAOImplementation.getAllBooks();
        // Take each book and add it to the table model.
        for (Vector<Object> row : books) {
            tableModel.addRow(row);

        }

        // setting the entire model to the table
        booksTable.setModel(tableModel);

        // Styling the table
        booksTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);


        //Changed alignment of the records to center, Except the first two columns
        for (int i = 1; i < booksTable.getColumnCount(); i++) {
            booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }

    void fetchTableData(Vector<Vector<Object>> books) {

        // Create table with no rows and just columnsNames
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Take each book and add it to the table model.
        for (Vector<Object> row : books) {
            tableModel.addRow(row);

        }

        // setting the entire model to the table
        booksTable.setModel(tableModel);

        // Styling the table
        booksTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);


        //Changed alignment of the records to center, Except the first two columns
        for (int i = 1; i < booksTable.getColumnCount(); i++) {
            booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // Books based functions
    void getBookByISBN() {
        String ISBN = tFISBN.getText();
        Vector<Vector<Object>> books = BookDAOImplementation.getBookByISBN(ISBN);
        fetchTableData(books);

    }

    void addBook() {
        title = tFTitle.getText();
        author = tFAuthor.getText();
        isbn = tFISBN.getText();
        status = comboStatus.getSelectedItem().toString().equals("Available") ? Book.Status.AVAILABLE : Book.Status.ISSUED;

        try {
            boolean isAdded = BookService.addBook(title, author, isbn, status);
            if (isAdded) {
                JOptionPane.showMessageDialog(null, "Book added successfully");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
        fetchTableData();
    }

    void deleteBookByISBN() {
        String ISBN = tFISBN.getText();
        boolean isDeleted = BookDAOImplementation.deleteBook(ISBN);

        if (isDeleted) {
            JOptionPane.showMessageDialog(null, "Book Deleted");
            resetAllTextFields();
            fetchTableData();

        } else {
            JOptionPane.showMessageDialog(null, "NOT deleted");
        }

    }

    void resetAllTextFields() {
        tFTitle.setText("");
        tFAuthor.setText("");
        tFISBN.setText("");
        comboStatus.setSelectedIndex(0);
    }
}

