package com.library.view;

import com.library.view.bookview.BookView;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public MainView() {
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setLayout(new GridLayout(1,1));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Library Management System");
    setLocationRelativeTo(null);

    ViewCardsContainer viewCardContainer = new ViewCardsContainer();
    add(viewCardContainer);
    setVisible(true);
    }
}
