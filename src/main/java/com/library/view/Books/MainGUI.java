package com.library.view.Books;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainGUI extends JFrame {
    private JPanel panel1;
    private JPanel Top;
    private JPanel AddBtnFormAndTable;
    private JButton memberButton;
    private JButton reportsButton;
    private JButton booksButton;
    private JButton exportButton;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JScrollPane jtableScroll;


    public MainGUI(){
        setContentPane(panel1);
    setSize(800, 600);
    setVisible(true);
    createUIComponents();
    }


    private void createUIComponents() {
     this.add(panel1);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainGUI();
            }
        });
    }
}
