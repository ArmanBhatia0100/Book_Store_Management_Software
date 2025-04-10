package com.library.database;

import com.library.model.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author arman
 */
public class MemberDoaImplementation {

    private static final Connection con = DatabaseConnection.getConnection();

    public void add(Member member) {
        String name = member.getName();
        String email = member.getEmail();
        String phone = member.getPhone();
        String registrationDate = member.getRegistrationDate().toString();

        String query = "INSERT INTO MEMBERS(name,email,phone) VALUES (?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, registrationDate);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(int memberID) {
        String query = "DELETE FORM MEMBERS WHERE memberID = ?";

        try {
            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setInt(1, memberID);
            int AffectedRow = pstmt.executeUpdate();

            if (AffectedRow > 0) {
                System.out.println("Memeber tel");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void veiwBorrowingHistory(int memberID) {
        String query = "SELECT members.name AS Member, books.title AS Book "
                + "FROM transactions "
                + "INNER JOIN members ON transactions.member_Id = members.member_id "
                + "INNER JOIN books ON transactions.book_Id = books.book_Id";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);

            ResultSet affectedRow = pstmt.executeQuery();
            System.out.printf("%-30s %-30s\n", "Member_Name", "Borrowed_Books");
            System.out.printf("%-150s\n", "-------------------------------------------------------");

            while (affectedRow.next()) {

                System.out.printf("%-30s %-30s\n", affectedRow.getNString(1), affectedRow.getNString(2));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Member> getAllMember() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
