package com.library.view.bookview;

import javax.swing.*;
import java.awt.*;

public class BookView extends JPanel {
    private JPanel bookViewPanel;
    private JTextField textField1;
    private JButton searchButton;
    private JComboBox comboBox1;
    private JTable table1;
    private JButton PDFButton;
    private JButton deleteBookButton;
    private JLabel dayAndTime;

    public BookView() {
        add(bookViewPanel);
        this.setPreferredSize(new Dimension(800, 600));
    }
}
