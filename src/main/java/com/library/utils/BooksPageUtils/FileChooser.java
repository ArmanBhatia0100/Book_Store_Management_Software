package com.library.utils.BooksPageUtils;

import javax.swing.*;
import java.io.File;

public class FileChooser {

    public static void getPDFFilePath(JTable booksTable) {

        // Create a file Chooser
        JFileChooser fileChooser = new JFileChooser();

        // Set default directory for the chooser
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        // Set default name for the file
        fileChooser.setSelectedFile(new File("all_books.pdf"));

        // Displaying save dialog button and save the result
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            //Ensuring PDF extension then sets file extension to .pdf
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                selectedFile = new File(filePath + ".pdf");
            }

            //Checks for override
            if (selectedFile.exists()) {
                int overwrite = JOptionPane.showConfirmDialog(null, "Overwrite existing file?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (overwrite != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            boolean success = ExportToPDF.exportToPDF(booksTable, selectedFile);
            if (success) {
                JOptionsUtils.showMessageBox("PDF exported successfully to: " + selectedFile.getAbsolutePath());
            } else {
                JOptionsUtils.showMessageBox("Failed to export PDF. Check permissions or disk space Error ");
            }
        }


    }

}
