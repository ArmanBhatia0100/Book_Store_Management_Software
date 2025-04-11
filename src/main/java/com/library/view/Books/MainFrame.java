package com.library.view.Books;

import com.library.database.BookDAOImplementation;
import com.library.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import javax.swing.Timer;
import java.util.Vector;

import java.time.LocalDate;


public class MainFrame extends JFrame{
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

    public MainFrame(){

        initailSetup();
        getDateAndTime();
        findByISBNButton.addActionListener(e -> {
            String actionCommand = e.getActionCommand();
            switch (actionCommand){
                case "FindByISBN"-> getBookByISBN();
                default -> JOptionPane.showMessageDialog(null, "Unknown action command");
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actionCommand = e.getActionCommand();
                String title = tFTitle.getText();
                String author = tFAuthor.getText();
                String isbn = tFISBN.getText();
                Book.Status status = comboStatus.getSelectedItem().toString().equals("available") ? Book.Status.AVAILABLE : Book.Status.ISSUED;


//                new Book()
                switch (actionCommand){
//                    case "Add"-> BookDAOImplementation.addBook();
                    default -> JOptionPane.showMessageDialog(null, "Unknown action command");
                }
            }
        });
    }

    void initailSetup(){
        setSize(new Dimension(800, 600));
        add(mainPanel);
        this.setLocationRelativeTo(MainFrame.this);
        setVisible(true);

        // Get book for booksDOA
        Vector<Vector<Object>> books =  BookDAOImplementation.getAllBooks();
        fetchTableData(books);

}   void getDateAndTime() {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        // Create a Timer that fires every 1000 milliseconds

        Timer timer = new Timer(1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
              String time = LocalTime.now().format(timeFormatter);
              String date = LocalDate.now().format(dateFormatter);
              String day = LocalDate.now().getDayOfWeek().toString().toLowerCase();
              String camelCaseDay = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

              dayAndDate.setText(time+ " | " + camelCaseDay + " | "+date);
            }
        });

        // Start the timer
        timer.start();
    }

    void fetchTableData(Vector<Vector<Object>> books){
        // Got the books as Vector Object. which is thread safe.

        String[] columnNames = {"Title","Author","ISBN","Status","Added Date"};
        // Create table with no rows and just columnsNames
        DefaultTableModel tableModel = new DefaultTableModel (columnNames,0);

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

//        booksTable.getColumn("ID").setPreferredWidth(2);

        //Changed alignment of the records to center, Except the first two columns
        for (int i = 1; i < booksTable.getColumnCount(); i++) {
            booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    void getBookByISBN(){
        String ISBN = tFISBN.getText();
       Vector<Vector<Object>> books =  BookDAOImplementation.getBookByISBN(ISBN);
       fetchTableData(books);

    }
}

