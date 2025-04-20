package com.library.view;

import com.library.view.bookview.BookView;
import com.library.view.memberView.MemberView;

import javax.swing.*;
import java.awt.*;

import com.library.view.bookview.BookView;

public class ViewCardsContainer extends JPanel {
   private CardLayout cardLayout = null;
    public ViewCardsContainer() {
        cardLayout = new CardLayout();
        this.setLayout(cardLayout);

        BookView bookView= new BookView(this);
        MemberView memberView = new MemberView(this);

        this.add(bookView,"bookView");
        this.add(memberView,"memberView");

        cardLayout.show(this,"bookView");
        this.setVisible(true);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
