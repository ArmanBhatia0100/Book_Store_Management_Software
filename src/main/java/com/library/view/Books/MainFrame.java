package com.library.view.Books;

import com.library.database.BookDAOImplementation;
import com.library.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import javax.swing.Timer;
import java.util.Vector;

import java.time.LocalDate;


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

    public MainFrame() {

        initailSetup();
        getDateAndTime();
        findByISBNButton.addActionListener(e -> {
            String actionCommand = e.getActionCommand();
            switch (actionCommand) {
                case "FindByISBN" -> getBookByISBN();
                default -> JOptionPane.showMessageDialog(null, "Unknown action command");
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String actionCommand = e.getActionCommand();
                switch (actionCommand) {

                    case "Add" -> {
                        addBook();
                    }
                    default -> JOptionPane.showMessageDialog(null, "Unknown action command");
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBookByISBN();
            }
        });
    }

    // General Functions
    void initailSetup() {

        setSize(new Dimension(800, 600));
        add(mainPanel);
        this.setLocationRelativeTo(MainFrame.this);
        setVisible(true);

        // fetch current data from the db.
        fetchTableData();


    }

    void getDateAndTime() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        // Create a Timer that fires every 1000 milliseconds

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String time = LocalTime.now().format(timeFormatter);
                String date = LocalDate.now().format(dateFormatter);
                String day = LocalDate.now().getDayOfWeek().toString().toLowerCase();
                String camelCaseDay = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

                dayAndDate.setText(time + " | " + camelCaseDay + " | " + date);
            }
        });

        // Start the timer
        timer.start();
    }
    void fetchTableData(){
        String[] columnNames = {"Title","Author","ISBN","Status","Added Date"};
        DefaultTableModel tableModel = new DefaultTableModel (columnNames,0);

        Vector<Vector<Object>> books =  BookDAOImplementation.getAllBooks();
        // Take each book and add it to the table model.
        for(Vector<Object> row : books){
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



    void fetchTableData() {
        String[] columnNames = {"Title", "Author", "ISBN", "Status", "Added Date"};
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
        String[] columnNames = {"Title", "Author", "ISBN", "Status", "Added Date"};
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
        String title = tFTitle.getText();
        String author = tFAuthor.getText();
        String isbn = tFISBN.getText();
        Book.Status status = comboStatus.getSelectedItem().toString().equals("Available") ? Book.Status.AVAILABLE : Book.Status.ISSUED;
        LocalDate date = LocalDate.now();

        Book newBook = new Book(title, author, isbn, status, date);

        try {
            boolean isAdded = BookDAOImplementation.addBook(newBook);

            if (isAdded) {
                JOptionPane.showMessageDialog(null, "Book added");
                resetAllTextFields();
                fetchTableData();
                booksTable.revalidate();
                booksTable.repaint();
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            String message = e.getMessage().toString();
            JOptionPane.showMessageDialog(this, message);
            resetAllTextFields();
        } catch (SQLException e) {
            String message = e.getLocalizedMessage();
            JOptionPane.showMessageDialog(this, message);
            resetAllTextFields();
        }


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
    void addBook(){
        String title = tFTitle.getText();
        String author = tFAuthor.getText();
        String isbn = tFISBN.getText();
        Book.Status status = comboStatus.getSelectedItem().toString().equals("Available") ? Book.Status.AVAILABLE : Book.Status.ISSUED;
        LocalDate date = LocalDate.now();

        Book newBook = new Book(title,author,isbn,status,date);

        try{
            boolean isAdded = BookDAOImplementation.addBook(newBook);

            if(isAdded){
                JOptionPane.showMessageDialog(null, "Book added");
                resetAllTextFields();
                fetchTableData();
                booksTable.revalidate();
                booksTable.repaint();
            }
        }catch (SQLIntegrityConstraintViolationException e){
            String message = e.getMessage().toString();
            JOptionPane.showMessageDialog(this, message);
            resetAllTextFields();
        }
        catch (SQLException e){
            String message = e.getLocalizedMessage();
            JOptionPane.showMessageDialog(this, message);
            resetAllTextFields();
        }



    }

    void resetAllTextFields(){
        tFTitle.setText("");
        tFAuthor.setText("");
        tFISBN.setText("");
        comboStatus.setSelectedIndex(0);
    }
}

