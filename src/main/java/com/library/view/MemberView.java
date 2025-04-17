package com.library.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberView extends JPanel {
    private JButton button1;
    private JPanel panel1;

    ViewCardsContainer parent;
    CardLayout cardLayout;

    public MemberView(ViewCardsContainer parent) {
            this.parent = parent;
            this.cardLayout = parent.getCardLayout();

        add(panel1);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parent, "bookView");
            }
        });
    }
}
