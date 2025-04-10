package com.library.database;

import com.library.model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author arman
 */
public class BookDAOImplementation {

    private static final Connection con = DatabaseConnection.getConnection();

    public static void addBook(Book book) {
        String title = book.getTitle();
        String author = book.getAuthor();
        String isbn = book.getIsbn();
        String status = book.getStatus().toString();
        String added_date = book.getDated_date().toString();

        String query = "INSERT INTO BOOKS (title,author,isbn,status,added_date) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, status);
            pstmt.setString(5, added_date);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book added Successfully");
            } else {
                System.err.println("no book added");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteBook(int bookID) {

        String query = "DELETE FROM BOOKS WHERE bookID = (?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookID);
            int resultRows = pstmt.executeUpdate();

            if (resultRows > 0) {
                System.out.println("Booked Deleted Successfully");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getBookByID(int bookID) {
        String query = "SELECT * FROM BOOKS WHERE bookID = (?)";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bookID);
            ResultSet resultRows = pstmt.executeQuery();

            System.out.printf("%-30s %-30s %-30s %-30s %-30s\n", "name", "auth", "isbn", "status", "added_date");
            System.out.printf("%-150s\n", "-------------------------------------------------------------------------------------------------------------------");

            while (resultRows.next()) {
                String name = resultRows.getString("title");
                String auth = resultRows.getString("author");
                String isbn = resultRows.getString("isbn");
                String status = resultRows.getString("status");
                String added_date = resultRows.getString("added_date");

                System.out.printf("%-30s %-30s %-30s %-30s %-30s\n", name, auth, isbn, status, added_date);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAllBooks() {
        String query = "SELECT * FROM books";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet resultRows = pstmt.executeQuery();

            System.out.printf("%-30s %-30s %-30s %-30s %-30s\n", "name", "auth", "isbn", "status", "added_date");
            System.out.printf("%-150s\n", "-----------------------------------------------------------------------------------------------------------------------------------");

            while (resultRows.next()) {
                String name = resultRows.getString("title");
                String auth = resultRows.getString("author");
                String isbn = resultRows.getString("isbn");
                String status = resultRows.getString("status");
                String added_date = resultRows.getString("added_date");

                System.out.printf("%-30s %-30s %-30s %-30s %-30s\n", name, auth, isbn, status, added_date);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStatus(int BookID, Book.Status status) {

        String query = "UPDATE BOOK SET status = ? WHERE bookID =  ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, status.toString());
            pstmt.setInt(2, BookID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
