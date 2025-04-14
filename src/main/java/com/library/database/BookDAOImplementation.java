package com.library.database;

import com.library.model.Book;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

/**
 * @author arman
 */
public class BookDAOImplementation {

    private static final Connection con = DatabaseConnection.getConnection();


    public static boolean addBook(Book book) throws SQLNonTransientException, SQLIntegrityConstraintViolationException, SQLException {
        String title = book.getTitle();
        String author = book.getAuthor();
        String isbn = book.getIsbn();
        String status = book.getStatus().toString();
        String added_date = book.getDated_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "INSERT INTO BOOKS (title,author,isbn,status,added_date) VALUES (?,?,?,?,?)";


        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, title);
        pstmt.setString(2, author);
        pstmt.setString(3, isbn);
        pstmt.setString(4, status);
        pstmt.setString(5, added_date);

        int rows = pstmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Book added Successfully");
            return true;
        }
        return false;

    }

    public static boolean deleteBook(String isbn) {

        String query = "DELETE FROM BOOKS WHERE bookID = (?)";

        try {
            String selectQuery = "SELECT book_id FROM BOOKS WHERE isbn = ?";
            PreparedStatement pstmt = con.prepareStatement(selectQuery);
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                // Step 2: Delete transactions associated with the book_id
                String deleteTransactionsQuery = "DELETE FROM TRANSACTIONS WHERE book_id = ?";
                pstmt = con.prepareStatement(deleteTransactionsQuery);
                pstmt.setInt(1, bookId);
                pstmt.executeUpdate();

                // Step 3: Delete the book from BOOKS
                String deleteBookQuery = "DELETE FROM BOOKS WHERE isbn = ?";
                pstmt = con.prepareStatement(deleteBookQuery);
                pstmt.setString(1, isbn);
                int resultRows = pstmt.executeUpdate();

                // Book found and deleted
                if (resultRows > 0) {
                    System.out.println("Book and related transactions deleted successfully");
                    return true;
                }
                // Book not found
                else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Vector<Vector<Object>> findBook(String bookInfo) {


        Vector<Vector<Object>> booksData = new Vector<Vector<Object>>();

        try {
            //find by title
            String query = "SELECT * FROM BOOKS WHERE isbn like ? OR title like ? OR author like ? OR isbn like ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + bookInfo + "%");
            pstmt.setString(2, "%" + bookInfo + "%");
            pstmt.setString(3, "%" + bookInfo + "%");
            pstmt.setString(4, "%" + bookInfo + "%");
            ResultSet resultRows = pstmt.executeQuery();

            if (resultRows.next()) {
                while (resultRows.next()) {
                    Vector<Object> book = new Vector<>();
                    String bookID = resultRows.getString("book_id");
                    String title = resultRows.getString("title");
                    String auth = resultRows.getString("author");
                    String isbn = resultRows.getString("isbn");
                    String status = resultRows.getString("status");
                    String added_date = resultRows.getString("added_date");

                    //Creating the book vector
//                book.add(bookID);
                    book.add(title);
                    book.add(auth);
                    book.add(isbn);
                    book.add(status);
                    book.add(added_date);

                    //Adding book vector or vector of vector (Books)
                    booksData.add(book);
                }
            } else {
                return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksData;
    }

    public static Vector<Vector<Object>> getAllBooks() {

        String query = "SELECT * FROM books";

        Vector<Vector<Object>> booksData = new Vector<Vector<Object>>();

        try {

            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet resultRows = pstmt.executeQuery();


            while (resultRows.next()) {
                Vector<Object> book = new Vector<>();
                String bookID = resultRows.getString("book_id");
                String title = resultRows.getString("title");
                String auth = resultRows.getString("author");
                String isbn = resultRows.getString("isbn");
                String status = resultRows.getString("status");
                String added_date = resultRows.getString("added_date");


                // Adding a single book DATA to book ArrayList {name,auth,isbn,etc}
//                book.add(bookID);
                book.add(title);
                book.add(auth);
                book.add(isbn);
                book.add(status);
                book.add(added_date);

                // Adding a BOOK to the array of BOOKS
                booksData.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return booksData;
    }

    public static void main(String[] args) {
//        getBookByISBN("978-");

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
