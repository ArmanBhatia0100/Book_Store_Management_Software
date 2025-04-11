/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.library;

import com.formdev.flatlaf.IntelliJTheme;
import com.library.view.Books.MainFrame;

import java.io.InputStream;
import java.sql.SQLException;
import javax.swing.SwingUtilities;

/**
 * @author arman
 */
public class Advance_library_management_system {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        SwingUtilities.invokeLater(new Runnable() {
                                       @Override
                                       public void run() {
                                           InputStream stream = Advance_library_management_system.class.getResourceAsStream("/DeepOcean.theme.json");
                                           if (stream == null) {
                                               System.err.println("Theme file not found!");
                                           } else {
                                               IntelliJTheme.setup(stream);
                                           }
                                           new MainFrame();
                                       }
                                   }
        );

//        BookDAOImplementation BI = new BookDAOImplementation();
//        MemberDoaImplementation.veiwBorrowingHistory(1);
    }
}
