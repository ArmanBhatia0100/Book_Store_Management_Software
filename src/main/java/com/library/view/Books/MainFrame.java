package com.library.view.Books;

import com.library.database.BookDAOImplementation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;


public class MainFrame extends JFrame{
    private JButton button1;
    private JPanel mainPanel;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable booksTable;
    private JScrollPane bookTableScroll;
    private JComboBox comboBox1;
    private JPanel formPanel;

    public MainFrame(){



        booksTableRender();
        setSize(new Dimension(800, 600));
        add(mainPanel);
        setVisible(true);
    }


    void booksTableRender(){
        String[] columnNames = {"Title","Author","ISBN","Status","Added Date"};

        // Create table with no rows and just columnsNames
        DefaultTableModel tableModel = new DefaultTableModel (columnNames,0);

        // Got the books as Vector Object. which is thread safe.
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


booksTable.getColumn("Title").setCellRenderer(leftRenderer);

        //Changed alignment of the records to center, Except the first two columns
        for (int i = 2; i < booksTable.getColumnCount(); i++) {
            booksTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}

