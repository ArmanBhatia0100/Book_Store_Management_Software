/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.library;

import com.formdev.flatlaf.IntelliJTheme;
import com.library.view.Books.MainFrame;

import javax.swing.*;

import java.sql.SQLException;

/**
 * @author arman
 */
public class Advance_library_management_system {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SwingUtilities.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           IntelliJTheme.setup(Advance_library_management_system.class.getResourceAsStream(
                                                   "/DeepOcean.theme.json"));
                                           new MainFrame();
                                       }
                                   }
        );

//        BookDAOImplementation BI = new BookDAOImplementation();
//        MemberDoaImplementation.veiwBorrowingHistory(1);
    }
}
