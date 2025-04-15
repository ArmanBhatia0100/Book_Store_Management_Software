package com.library.utils.BooksPageUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileOutputStream;

public class ExportToPDF {

    public static boolean exportToPDF(JTable booksTable, File selectedFile) {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
            document.open();

            TableModel booksModel = booksTable.getModel();
            int columnCount = booksModel.getColumnCount();
            int rowCount = booksModel.getRowCount();

            PdfPTable pdfTable = new PdfPTable(columnCount);

            // Add headers
            for (int i = 0; i < columnCount; i++) {
                pdfTable.addCell(booksModel.getColumnName(i));
            }

            // Add rows
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    Object value = booksModel.getValueAt(i, j);
                    pdfTable.addCell(value != null ? value.toString() : "");
                }
            }
            document.add(pdfTable);
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Failure
        }
    }

}
