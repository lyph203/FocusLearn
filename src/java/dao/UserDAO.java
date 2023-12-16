/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import model.User;
import util.PasswordUtil;

/**
 *
 * @author Admin
 */
public class UserDAO extends DBContext {

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE Username = ? or email = ?";
        User user = new User();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setGender(rs.getInt("gender"));
                user.setDob(rs.getString("dob"));
                user.setImg(rs.getString("img"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getInt("role"));
                user.setStatus(rs.getInt("status"));
                user.setDescription(rs.getString("description"));
                user.setWallet(rs.getDouble("wallet"));
            }
        } catch (Exception e) {
            System.out.println("getUserByUsername: " + e.getMessage());
        }
        return user;
    }

    public boolean checkExistedUserWithUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM USERS WHERE (username = ? or email = ?) AND password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("checkExistedUserWithUsernameAndPassword: " + e.getMessage());
        }
        return false;
    }

    public boolean checkExistedUserWithUsername(String username) {
        String sql = "SELECT * FROM USERS WHERE username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("checkExistedUserWithUsername: " + e.getMessage());
        }
        return false;
    }

    public boolean checkExistedEmail(String email) {
        String sql = "select email from users where email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println("checkExistedEmail: " + e.getMessage());
        }
        return false;
    }

    public void signUp(String fullName, String gender, String username, String password, String email, String dob) {
        try {
            String sql = "INSERT INTO USERS (username, dob, email,full_name,gender,password,role)"
                    + "VALUES (?, ?, ?, ?, ?, ?,?);";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, dob);
            pstm.setString(3, email);
            pstm.setString(4, fullName);
            String genderINT = "";
            if (gender.equals("Male")) {
                genderINT = 1 + "";
            } else {
                genderINT = 0 + "";
            }
            pstm.setString(5, genderINT);
            pstm.setString(6, password);
            pstm.setString(7, "3");
            pstm.executeUpdate();
            //Close pstm
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Sign up failed: " + e.getMessage());
        }
    }

    public void updateUserProfilebyEmail(String fullname, String dob, String gender, String description, String email, String imgUrl) {
        String sql = "UPDATE users SET full_name = ?, dob = ?, gender = ?, description = ?, img = ? WHERE email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, fullname);
            ps.setString(2, dob);
            ps.setString(3, gender);
            ps.setString(4, description);
            ps.setString(5, imgUrl);
            ps.setString(6, email);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateUserProfilebyEmail: " + e.getMessage());
        }
    }

    public ArrayList<User> getListLecturer() {
        ArrayList<User> listLecturer = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [users] WHERE role = 2";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString(1);
                String dob = rs.getString(2);
                String email = rs.getString(3);
                String fname = rs.getString(4);
                int gender = rs.getInt(5);
                int role = rs.getInt(7);
                String img = "none";
                if (rs.getString(8) != null) {
                    img = rs.getString(8);
                }
                String description = "none";
                if (rs.getString(9) != null) {
                    description = rs.getString(9);
                }
                int status = rs.getInt(10);
                User lecturer = new User(username, fname, email, dob, gender, description, status, img);
                listLecturer.add(lecturer);
            }
        } catch (SQLException e) {
            System.out.println("getListLecturer: " + e.getMessage());
        }
        return listLecturer;
    }

    public int getTotalLecturer() {
        int totalLecturer = 0;
        String sql = "select COUNT(*) from users where role = 2";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalLecturer = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getTotalLecturer: " + e.getMessage());
        }
        return totalLecturer;
    }

    public int getTotalStudent() {
        int totalStudent = 0;
        String sql = "select COUNT(*) from users where role = 3";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalStudent = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getTotalStudent: " + e.getMessage());
        }
        return totalStudent;
    }

    public LinkedHashMap<User, Double> getListTopUser() {
        LinkedHashMap<User, Double> listTopUser = new LinkedHashMap<>();
        String sql = "select top 5 c.username, ROUND(AVG(CONVERT(float,rating)), 2) as 'AVGRating' from enrollment e, courses c\n"
                + "where e.course_id = c.course_id\n"
                + "Group by c.username\n"
                + "order by 'AVGRating' Desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = getUserByUsername(rs.getString(1));
                listTopUser.put(user, rs.getDouble(2));
                user.toString();
            }
        } catch (SQLException e) {
            System.out.println("getListTopUser: " + e.getMessage());
        }
        return listTopUser;
    }

    public void changePassword(String username, String newPassword) {
        String sql = "update users set password = ? where username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("changePassword: " + e.getMessage());
        }
    }
    
    public void banLecturer(String username) {
        try {
            String sql = "UPDATE users\n"
                    + "SET status = 0\n"
                    + "WHERE username = ?;"
                    + ""
                    + "UPDATE courses\n"
                    + "SET status = 0\n"
                    + "WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("banLecturer: " + e.getMessage());
        }
    }

    public void unbanLecturer(String username) {
        try {
            String sql = "UPDATE users\n"
                    + "SET status = 1\n"
                    + "WHERE username = ?;"
                    + ""
                    + "UPDATE courses\n"
                    + "SET status = 1\n"
                    + "WHERE username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ps.executeQuery();

        } catch (SQLException e) {
            System.out.println("unbanLecturer: " + e.getMessage());
        }
    }

    public double getAverageRatingOfLectureByUserName(String username) {
        String sql = "select c.username ,AVG(CONVERT(float,e.rating)) as 'AVGRating' \n"
                + "from enrollment e, courses c\n"
                + "where e.course_id = c.course_id and c.username = ?\n"
                + "group by c.username";
        double rating = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    rating = rs.getDouble(2);
                }
            }
        } catch (Exception e) {
            System.out.println("getAverageRatingOfLectureByUserName: " + e.getMessage());
        }
        return rating;
    }

    public int getTotalReviewOfLectureByUserName(String username) {
        String sql = "select c.username , Count(e.rating) as 'AVGRating' \n"
                + "from enrollment e, courses c\n"
                + "where e.course_id = c.course_id and c.username = ?\n"
                + "group by c.username";
        int rating = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    rating = rs.getInt(2);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalReviewOfLectureByUserName: " + e.getMessage());
        }
        return rating;
    }

    public int getTotalStudentOfLectureByUserName(String username) {
        String sql = "select c.username , Count(e.username) as 'AVGRating' \n"
                + "from enrollment e, courses c\n"
                + "where e.course_id = c.course_id and c.username = ?\n"
                + "group by c.username";
        int numberStudent = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    numberStudent = rs.getInt(2);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalStudentOfLectureByUserName: " + e.getMessage());
        }
        return numberStudent;
    }

    public int getTotalCourseOfLectureByUserName(String username) {
        String sql = "SELECT COUNT(course_id)\n"
                + "FROM [FocusLearn].[dbo].[courses]\n"
                + "where [username] = ?";
        int numberCourse = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    numberCourse = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalCourseOfLectureByUserName: " + e.getMessage());
        }
        return numberCourse;
    }

    public boolean createLecturerAccount(String username, String fullname, String email, String password, String dob, int gender, String description) {
        //query create lecturer account
        try {
            String sql = "INSERT INTO [users] ([username], [dob], [email], [full_name], [gender], [password], [role], [img], [description], [status])\n"
                    + "VALUES (?, ?, ?, ?, ?, ?, 2, ?, ?, 1)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);

            //change format of date
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-mm-dd");
            SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = originalFormat.parse(dob);
            String formattedDate = targetFormat.format(date);

            pstm.setString(2, formattedDate);
            pstm.setString(3, email);
            pstm.setString(4, fullname);
            pstm.setInt(5, gender);
            pstm.setString(6, new PasswordUtil().hashPasswordMD5(password));
            pstm.setString(7, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNL_ZnOTpXSvhf1UaK7beHey2BX42U6solRA&usqp=CAU");
            pstm.setString(8, description);
            pstm.execute();
            return true;

        } catch (Exception e) {
            System.out.println("createLecturerAccount: " + e.getMessage());
        }
        return false;
    }

    public ArrayList<User> searchListLecturer(String inputSearch) {
        ArrayList<User> listLecturer = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [users] WHERE username LIKE  ? or email LIKE  ? or full_name LIKE  ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + inputSearch + "%");
            ps.setString(2, "%" + inputSearch + "%");
            ps.setString(3, "%" + inputSearch + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString(1);
                String dob = rs.getString(2);
                String email = rs.getString(3);
                String fname = rs.getString(4);
                int gender = rs.getInt(5);
                int role = rs.getInt(7);
                String img = "none";
                if (rs.getString(8) != null) {
                    img = rs.getString(8);
                }
                String description = "none";
                if (rs.getString(9) != null) {
                    description = rs.getString(9);
                }
                int status = rs.getInt(10);
                User lecturer = new User(username, fname, email, dob, gender, description, status, img);
                listLecturer.add(lecturer);
            }
        } catch (SQLException e) {
            System.out.println("searchListLecturer: " + e.getMessage());
        }
        return listLecturer;
    }

    public int getTotalCourseOfLectureBySearch(String username, String inputSearch) {
        String sql = "SELECT COUNT(course_id)\n"
                + "FROM [FocusLearn].[dbo].[courses]\n"
                + "where [username] = ? and [course_name] like ?";
        int numberCourse = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, "%" + inputSearch + "%");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    numberCourse = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalCourseOfLectureByUserName: " + e.getMessage());
        }
        return numberCourse;
    }

}
