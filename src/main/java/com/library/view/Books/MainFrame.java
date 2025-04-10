package com.library.view.Books;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


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
        Object[][] data = {
                {"The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", "Available", "2023-01-15"},
                {"To Kill a Mockingbird", "Harper Lee", "978-0446310789", "Checked Out", "2023-02-10"},
                {"1984", "George Orwell", "978-0451524935", "Available", "2023-03-22"},
                {"Pride and Prejudice", "Jane Austen", "978-0141439518", "Available", "2023-04-05"},
                {"The Catcher in the Rye", "J.D. Salinger", "978-0316769488", "Checked Out", "2023-05-12"},
                {"The Hobbit", "J.R.R. Tolkien", "978-0547928227", "Available", "2023-06-18"},
                {"Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "978-0439708180", "Checked Out", "2023-07-25"},
                {"The Lord of the Rings", "J.R.R. Tolkien", "978-0544003415", "Available", "2023-08-30"},
                {"Brave New World", "Aldous Huxley", "978-0060850524", "Available", "2023-09-10"},
                {"Animal Farm", "George Orwell", "978-0451526342", "Checked Out", "2023-10-15"},
                {"Jane Eyre", "Charlotte Brontë", "978-0141441146", "Available", "2023-11-20"},
                {"Wuthering Heights", "Emily Brontë", "978-0141439556", "Checked Out", "2023-12-01"},
                {"The Odyssey", "Homer", "978-0140268867", "Available", "2024-01-05"},
                {"Fahrenheit 451", "Ray Bradbury", "978-1451673319", "Checked Out", "2024-02-14"},
                {"The Alchemist", "Paulo Coelho", "978-0062315007", "Available", "2024-03-20"}
        };
        DefaultTableModel tableModel = new DefaultTableModel( data, columnNames);
        booksTable.setModel(tableModel);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 16));

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

