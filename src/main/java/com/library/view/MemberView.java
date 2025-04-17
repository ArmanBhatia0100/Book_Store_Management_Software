package com.library.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberView extends JPanel {
    private JButton button1;
    private JPanel panel1;
    private JTextField tFSearch;
    private JButton searchButton;
    private JLabel dayAndDate;
    private JComboBox comboOperations;
    private JPanel headerPanel;
    private JPanel bodyPanel;

    ViewCardsContainer parent;
    CardLayout cardLayout;

    public MemberView(ViewCardsContainer parent) {
            this.parent = parent;
            this.cardLayout = parent.getCardLayout();
            setLayout(new BorderLayout());
            add(panel1);

        comboOperations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parent, "bookView");
            }
        });
    }
}
