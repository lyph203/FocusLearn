/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Admin
 */
public class CertificateDAO extends DBContext {

    public String getCertificateImage(String username, String courseId) {
        String sql = "select * from [certificates] where [courseid] = ? and username = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, courseId);
                ps.setString(2, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString(2);
                }
            }
        } catch (Exception e) {
            System.out.println("getCertificateImage" + e.getMessage());
        }
        return "";
    }

    public void addNewCertificate(String username, String url, String courseId) {
        String sql = "INSERT INTO [dbo].[certificates] ([img] ,[courseid] ,[username]) VALUES (?, ?, ?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, url);
                ps.setString(2, courseId);
                ps.setString(3, username);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("addNewCertificate" + e.getMessage());
        }
    }
}
