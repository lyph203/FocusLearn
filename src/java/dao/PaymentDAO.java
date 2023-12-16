/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Payment;
import model.User;

/**
 *
 * @author Admin
 */
public class PaymentDAO extends DBContext {

    public void addPayment(String username, String amount, String createdDate, String info, String status, String hashSecret, String courseid) {
        String sql = "insert into [payment] values (?, ?, ?, ?, ?, ?, ?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, String.valueOf(Double.parseDouble(amount)));
                ps.setString(3, createdDate);
                ps.setString(4, info);
                ps.setString(5, status);
                ps.setString(6, hashSecret);
                ps.setString(7, courseid);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("addPayment: " + e.getMessage());
        }
    }

    public void updatePayment(int status, String hashSecret) {
        String sql = "update Payment\n"
                + "  set status = ?\n"
                + "  where hash_secret = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, status);
                ps.setString(2, hashSecret);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updatePayment: " + e.getMessage());
        }
    }

    public void updateUserWallet(User user, String amount) {
        String sql = "update users set wallet = wallet - ? where username = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, Double.parseDouble(amount)/100 + "");
                ps.setString(2, user.getUsername());
                user.setWallet(user.getWallet() - Double.parseDouble(amount)/100);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updateUserWallet: " + e.getMessage());
        }
    }

    public void updateUserWallet(String username, String amount) {
        String sql = "update users set wallet = wallet + ? where username = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                User user = new UserDAO().getUserByUsername(username);
                ps.setString(1,Double.parseDouble(amount) * 90 / 100 + "");
                ps.setString(2, user.getUsername());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updateUserWallet with String username: " + e.getMessage());
        }
    }

    public ArrayList<Payment> getListPaymentbyUsername(String username) {
        String sql = "select *\n"
                + "from Payment\n"
                + "where username = ?";
        ArrayList<Payment> listPayment = new ArrayList<>();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String user = rs.getString(2);
                    double amount = rs.getDouble(3);
                    String date = rs.getString(4);
                    //Change type from String -> LocalDateTime
                    LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
                    String info = rs.getString(5);
                    int status = rs.getInt(6);

                    Payment payment = new Payment(id, user, amount, localDateTime, info, status);
                    listPayment.add(payment);
                }
            }
        } catch (Exception e) {
            System.out.println("getListPaymentbyUsername with String username: " + e.getMessage());
        }
        return listPayment;
    }

    public int getTotalPaymentHistorybyUsername(String username) {
        String sql = "SELECT COUNT(*)\n"
                + "FROM [FocusLearn].[dbo].[Payment]\n"
                + "where [username] = ?";
        int numberPayment = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    numberPayment = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalCourseOfLectureByUserName: " + e.getMessage());
        }
        return numberPayment;
    }

    public ArrayList<Payment> getListPaymentforPaging(String username, int index, int paymentPerPage) {
        String sql = "select *\n"
                + "from Payment\n"
                + "where username = ?\n"
                + "order by id\n"
                + "offset ? rows fetch next ? rows only";
        ArrayList<Payment> listPayment = new ArrayList<>();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setInt(2, (index - 1) * paymentPerPage);
                ps.setInt(3, paymentPerPage);
                ResultSet rs = ps.executeQuery();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String user = rs.getString(2);
                    double amount = rs.getDouble(3);
                    String date = rs.getString(4);
                    //Change type from String -> LocalDateTime
                    LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
                    String info = rs.getString(5);
                    int status = rs.getInt(6);

                    Payment payment = new Payment(id, user, amount, localDateTime, info, status);
                    listPayment.add(payment);
                }
            }
        } catch (Exception e) {
            System.out.println("getListPaymentbyUsername with String username: " + e.getMessage());
        }
        return listPayment;
    }

}
