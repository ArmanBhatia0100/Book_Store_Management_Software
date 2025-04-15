package com.library.view.utils;

import com.library.database.BookDAOImplementation;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class BooksTableUtil {

    public DefaultTableModel createTableModel(String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        Vector<Vector<Object>> books = BookDAOImplementation.getAllBooks();
        // Take each book and add it to the table model.
        for (Vector<Object> row : books) {
            tableModel.addRow(row);
        }
        return tableModel;
    }

    public DefaultTableModel createTableModel(String[] columnNames, Vector<Vector<Object>> books) {
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        // Take each book and add it to the table model.
        for (Vector<Object> row : books) {
            tableModel.addRow(row);
        }
        return tableModel;
    }

}
