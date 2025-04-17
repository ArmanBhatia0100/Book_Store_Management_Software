package com.library.view;

import javax.swing.*;
import java.awt.*;

public class MemberView extends JPanel {
    private JButton bookMgmtBtn;
    private JPanel panel1;
    private JTextField tFSearch;
    private JButton searchButton;
    private JLabel dayAndDate;
    private JComboBox comboOperations;
    private JPanel headerPanel;
    private JPanel bodyPanel;
    public JPanel operationsPanel;

    ViewCardsContainer parentPanel;
    CardLayout cardLayout;

    public MemberView(ViewCardsContainer parentPanel) {
            this.parentPanel = parentPanel;
            this.cardLayout = parentPanel.getCardLayout();

            initialUISetup();
            listenersSetup();
    }

    private void initialUISetup() {
        setLayout(new BorderLayout());
        add(panel1);
    }

    private void listenersSetup() {
        bookMgmtBtn.addActionListener(event -> {
        cardLayout.show(parentPanel, "bookView");});
    }
}
