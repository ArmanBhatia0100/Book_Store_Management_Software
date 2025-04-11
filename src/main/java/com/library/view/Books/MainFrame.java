package com.library.view.Books;

import com.library.database.BookDAOImplementation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;


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

    public MainFrame(){
        initailSetup();
        setSize(new Dimension(800, 600));
        add(mainPanel);
        setVisible(true);

        findByISBNButton.addActionListener(e -> {
            String actionCommand = e.getActionCommand();

            switch (actionCommand){
                case "FindByISBN"-> getBookByISBN();
                default -> JOptionPane.showMessageDialog(null, "Unknown action command");
            }
        });
    }

    void initailSetup(){

        // Get book for booksDOA
        Vector<Vector<Object>> books =  BookDAOImplementation.getAllBooks();
        fetchTableData(books);

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

        booksTable.getColumn("Title").setCellRenderer(leftRenderer);

        //Changed alignment of the records to center, Except the first two columns
        for (int i = 2; i < booksTable.getColumnCount(); i++) {
            booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    void getBookByISBN(){
        String ISBN = tFISBN.getText();
       Vector<Vector<Object>> books =  BookDAOImplementation.getBookByISBN(ISBN);
       fetchTableData(books);

    }
}

