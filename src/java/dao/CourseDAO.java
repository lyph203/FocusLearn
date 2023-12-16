/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.Course;
import model.CourseSection;
import model.Tag;

/**
 *
 * @author Admin
 */
public class CourseDAO extends DBContext {

    public Course getCourseByID(int courseID) {
        Course course = new Course();
        UserDAO userDAO = new UserDAO();
        String sql = "select * from courses where course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course.setCourseId(rs.getInt(1));
                course.setCourseName(rs.getString(2));
                Date createDate = rs.getDate(3);
                LocalDateTime localDateTime = new Date(createDate.getTime()).toLocalDate().atStartOfDay();
                course.setCreatedDate(localDateTime);
                course.setDescription(rs.getString(4));
                course.setDiscount(rs.getInt(5));
                course.setImage(rs.getString(6));
                course.setPrice(rs.getDouble(7));
                course.setStatus(rs.getInt(8));
                Date updateDate = rs.getDate(9);
                localDateTime = new Date(updateDate.getTime()).toLocalDate().atStartOfDay();
                course.setUpdateDate(localDateTime);
                course.setAuthor(userDAO.getUserByUsername(rs.getString(10)));
                course.setLevel(rs.getString(11));
                course.setLongDescription(rs.getString(12));
            }
        } catch (SQLException e) {
            System.out.println("getCourseByID: " + e.getMessage());
        }
        return course;
    }

    public double getTotalIncome() {
        double totalIncome = 0;
        ArrayList<Double> listPayment = new ArrayList<>();
        String sql = "select (price-(price*discount/100))*10/100 as 'Total' from enrollment e, courses c\n"
                + "where e.course_id = c.course_id";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listPayment.add(rs.getDouble(1));
            }
            for (Double payment : listPayment) {
                totalIncome += payment;
            }
        } catch (SQLException e) {
            System.out.println("getTotalIncome: " + e.getMessage());
        }
        return Math.round(totalIncome * 100.0) / 100.0;
    }

    public LinkedHashMap<Course, Double> getListTopCourse(int amount) {
        LinkedHashMap<Course, Double> listTopCourse = new LinkedHashMap<>();
        String sql = "select top " + amount + " course_id, ROUND(AVG(CONVERT(float,rating)), 2) as 'AVGRating' from enrollment\n"
                + "Group by course_id\n"
                + "order by 'AVGRating' Desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                listTopCourse.put(course, rs.getDouble(2));
            }
        } catch (SQLException e) {
            System.out.println("getListTopCourse: " + e.getMessage());
        }
        return listTopCourse;
    }

    public List<Course> getAllCourse() {
        List<Course> list = new ArrayList<>();
        String sql = " Select * from courses";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(4);
                int discount = rs.getInt(5);
                String image = rs.getString(6);
                String price = rs.getString(7);
                Double dPrice = Double.parseDouble(price);
                String username = rs.getString(10);
                String level = rs.getString(11);
                Course c = new Course(id, name, description, dPrice, discount, image, level, username);
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println("getAllCourse fail" + e.getMessage());
        }
        return list;
    }

    public int countTotalCourse(String searchContent) {
        String sql = "Select COUNT(*) from courses where course_name like '%" + searchContent + "%'";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countTotalCourse:" + e.getMessage());
        }
        return 0;
    }

    public int countAvailableCourse(String searchContent) {
        String sql = "Select COUNT(*) from courses where status ='1' and course_name like '%" + searchContent + "%'";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countTotalCourse fail" + e.getMessage());
        }
        return 0;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListCourseAndRating() {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = "SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE c.status = 1\n"
                + "GROUP BY c.course_id\n"
                + "ORDER BY c.course_id\n";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListCourseAndRating: " + e.getMessage());
        }
        return listCourse;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListTopCourses(int amount) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = " SELECT top " + amount + " c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.rating) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE c.status = 1\n"
                + "GROUP BY c.course_id\n"
                + "order by 'AVGRating' Desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListTopCourses: " + e.getMessage());
        }
        return listCourse;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListPagingCourse(int index, int coursePerPage, int statusValue, String searchContent) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = "";
        //List only includes available courses
        if (statusValue == 1) {
            sql = " SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.rating) AS 'RatingCount'\n"
                    + "FROM courses c\n"
                    + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                    + "WHERE c.status = 1 and c.course_name like '%" + searchContent + "%'\n"
                    + "GROUP BY c.course_id\n"
                    + "order by c.course_id\n"
                    + "Offset ? Rows fetch next ? rows only";
        } //list includes all course
        else if (statusValue == 10) {
            sql = " SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                    + "FROM courses c\n"
                    + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                    + "WHERE (c.status = 1 OR c.status = 0) and c.course_name like '%" + searchContent + "%'\n"
                    + "GROUP BY c.course_id\n"
                    + "order by c.course_id\n"
                    + "  Offset ? Rows fetch next ? rows only";
        } //List only includes pending courses
        else if (statusValue == 2) {
            sql = " SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                    + "FROM courses c\n"
                    + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                    + "WHERE c.status = 2 and c.course_name like '%" + searchContent + "%'\n"
                    + "GROUP BY c.course_id\n"
                    + "order by c.course_id\n"
                    + "  Offset ? Rows fetch next ? rows only";
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (index - 1) * coursePerPage);
            ps.setInt(2, coursePerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListPagingCourse: " + e.getMessage());
        }
        return listCourse;
    }

    public LinkedHashMap<Course, Integer> getNumberOfUserEachCourse() {
        LinkedHashMap<Course, Integer> listNumber = new LinkedHashMap<>();
        String sql = "SELECT course_id,count(course_id) as total_users\n"
                + "FROM enrollment\n"
                + "GROUP BY course_id;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                listNumber.put(course, rs.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println("getNumberOfUserEachCourse: " + e.getMessage());
        }
        return listNumber;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getCourseInforByID(int courseID) {
        Course course = new Course();
        UserDAO userDAO = new UserDAO();
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> list = new LinkedHashMap<>();
        String sql = "   SELECT c.*, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE c.course_id = ?\n"
                + "GROUP BY c.course_id,c.course_name,c.created_date,c.description,c.discount,c.img,c.level,c.price,c.status,c.update_date,c.username,c.long_description\n"
                + "ORDER BY c.course_id;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course.setCourseId(rs.getInt(1));
                course.setCourseName(rs.getString(2));
                Date createDate = rs.getDate(3);
                LocalDateTime localDateTime = new Date(createDate.getTime()).toLocalDate().atStartOfDay();
                course.setCreatedDate(localDateTime);
                course.setDescription(rs.getString(4));
                course.setDiscount(rs.getInt(5));
                course.setImage(rs.getString(6));
                course.setPrice(rs.getDouble(7));
                course.setStatus(rs.getInt(8));
                Date updateDate = rs.getDate(9);
                localDateTime = new Date(updateDate.getTime()).toLocalDate().atStartOfDay();
                course.setUpdateDate(localDateTime);
                course.setAuthor(userDAO.getUserByUsername(rs.getString(10)));
                course.setLevel(rs.getString(11));
                course.setLongDescription(rs.getString(12));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(13), rs.getInt(14));
                list.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getCourseInforByID: " + e.getMessage());
        }
        return list;
    }

    public void banCourse(String courseID) {
        String sql = "update courses set status ='0' \n"
                + "where course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("banCourse: " + e.getMessage());

        }
    }

    public void unbanCourse(String courseID) {
        String sql = "update courses set status ='1' \n"
                + "where course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("UnbanCourse: " + e.getMessage());
        }
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getCourseDetail(String id) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = " SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE c.status = 1 and c.course_id = ?\n"
                + "GROUP BY c.course_id\n"
                + "order by c.course_id\n";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getCourseDetail: " + e.getMessage());
        }
        return listCourse;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListLecturerCourse(String username) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = "SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.rating) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE c.status = 1 and c.username = ?\n"
                + "GROUP BY c.course_id\n"
                + "ORDER BY c.course_id\n";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListLecturerCourse: " + e.getMessage());
        }
        return listCourse;
    }

    public int getTotalStudentOfCourseById(String id) {
        String sql = "select count(username) from enrollment where course_id = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalStudentOfCourseById: " + e.getMessage());
        }
        return 0;
    }

    public LinkedHashMap<Course, Double> getListSuggestedCourse(String id) {
        String sql = "select ct.course_id,  AVG(CONVERT(float, e.rating)) as rating\n"
                + "from course_tags ct left join enrollment e\n"
                + "on ct.course_id = e.course_id\n"
                + "where tag_id in (select tag_id from course_tags where course_id = ?) and ct.course_id != ?\n"
                + "group by ct.course_id";

        LinkedHashMap<Course, Double> listCourse = new LinkedHashMap<>();
        String listId = "(" + id;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);
                ps.setString(2, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Course course = getCourseByID(rs.getInt(1));
                    listId += "," + course.getCourseId();
                    double rating = rs.getString(2) == null || rs.getString(2).isEmpty() ? 0 : rs.getDouble(2);
                    listCourse.put(course, rating);
                }
            }
        } catch (Exception e) {
            System.out.println("getListSuggestedCourse: " + e.getMessage());
        }
        if (listCourse.size() < 6) {
            listId += ")";
            getCourseNotInRangeOfId(listCourse, listId, 6 - listCourse.size());
        }
        return listCourse;
    }

    public void getCourseNotInRangeOfId(LinkedHashMap<Course, Double> listCourse, String invalidRange, int limit) {
        String sql = "select ct.course_id,  AVG(CONVERT(float, e.rating)) as rating\n"
                + "from course_tags ct left join enrollment e\n"
                + "on ct.course_id = e.course_id\n"
                + "where ct.course_id not in " + invalidRange + "\n"
                + "group by ct.course_id\n"
                + "order by rating desc\n"
                + "Offset 0 Rows fetch next ? rows only";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, limit);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Course course = getCourseByID(rs.getInt(1));
                    double rating = rs.getString(2) == null || rs.getString(2).isEmpty() ? 0 : rs.getDouble(2);
                    listCourse.put(course, rating);
                }
            }
        } catch (Exception e) {
            System.out.println("getCourseNotInRangeOfId: " + e.getMessage());
        }
    }

    public int countTotalPendingCourse(String searchContent) {
        String sql = "Select COUNT(*) from courses where status = 2 and course_name like '%" + searchContent + "%'";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countTotalPendingCourse:" + e.getMessage());
        }
        return 0;
    }

    public void enrollCouse(String courseid, String username, String enrollDate) {
        String sql = "  insert into enrollment(course_id, username, enrollDate) values (?, ?, ?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, courseid);
                ps.setString(2, username);
                ps.setString(3, enrollDate);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("enrollCouse: " + e.getMessage());
        }
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListPagingFilterCourse(int index, int coursePerPage, Map<String, String[]> parameterMap, String searchContent) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = "";

        try {
            String condition = getConditionFilter(parameterMap);
            sql = "SELECT distinct c.course_id, d.AVGRating, COUNT(e.course_id) AS 'RatingCount' FROM course_tags ct, courses c\n"
                    + "LEFT JOIN enrollment e ON c.course_id = e.course_id,\n"
                    + "(SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating' FROM courses c\n"
                    + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                    + "GROUP BY c.course_id, c.level\n"
                    + ") as d\n"
                    + "WHERE c.status = 1 and c.course_id = d.course_id and ct.course_id = c.course_id and c.course_name like '%" + searchContent + "%'\n"
                    + "GROUP BY c.course_id, d.AVGRating, c.level, c.price, ct.tag_id\n"
                    + "Having " + condition + "\n"
                    + "order by c.course_id\n"
                    + "Offset ? Rows fetch next ? rows only";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, (index - 1) * coursePerPage);
            pstm.setInt(2, coursePerPage);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (Exception e) {
            System.out.println("getListPagingFilterCourse: " + e.getMessage());
        }

        return listCourse;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListLecturerCourseForPaging(String username, int index, int coursePerPage) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = "SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE  c.username = ?\n"
                + "GROUP BY c.course_id\n"
                + "ORDER BY c.course_id\n"
                + " Offset ? Rows fetch next ? rows only";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, (index - 1) * coursePerPage);
            ps.setInt(3, coursePerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListLecturerCourseForPaging: " + e.getMessage());
        }
        return listCourse;
    }

    public boolean checkEnrolledCourseByUsername(String courseId, String studentUsername) {
        String sql = "  Select count(*) from enrollment where username=? and course_id=?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, studentUsername);
                ps.setString(2, courseId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("checkEnrolledCourseByUsername: " + e.getMessage());
        }
        return false;
    }

    public int countAvailableFilterCourse(Map<String, String[]> parameterMap, String searchContent) {
        String sql = "";
        try {
            String condition = getConditionFilter(parameterMap);
            sql = "select COUNT(f.course_id) from \n"
                    + "(\n"
                    + "	SELECT c.course_id, d.AVGRating, COUNT(e.course_id) AS 'RatingCount' FROM course_tags ct, courses c\n"
                    + "	LEFT JOIN enrollment e ON c.course_id = e.course_id,\n"
                    + "	(SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating' FROM courses c\n"
                    + "	LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                    + "	GROUP BY c.course_id, c.level\n"
                    + "	) as d\n"
                    + "	WHERE c.status = 1 and c.course_id = d.course_id and ct.course_id = c.course_id and c.course_name like '%" + searchContent + "%'\n"
                    + "	GROUP BY c.course_id, d.AVGRating, c.level, c.price, ct.tag_id\n"
                    + "	Having " + condition + ")\n"
                    + "as f";

            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countAvailableFilterCourse" + e.getMessage());
        }
        return 0;
    }

    private String getConditionFilter(Map<String, String[]> parameterMap) {
        String condition = "";
        String[] ratings = parameterMap.get("ratings");
        if (ratings != null && ratings.length > 0) {
            condition += "AVGRating > " + ratings[0];
        }

        String[] level = parameterMap.get("level");
        if (level != null && level.length > 0) {
            if (!condition.equals("")) {
                condition += " and level IN (";
            } else {
                condition += "level IN (";
            }
            for (int i = 0; i < level.length - 1; i++) {
                condition += "'" + level[i] + "',";
            }
            condition += "'" + level[level.length - 1] + "')";
        }

        String[] price = parameterMap.get("price");
        if (price != null && price.length > 0) {
            for (int i = 0; i < price.length; i++) {
                if (Integer.parseInt(price[i]) == 0 && !condition.equals("")) {
                    condition += " or price < 1";
                }
                if (Integer.parseInt(price[i]) == 0 && condition.equals("")) {
                    condition += "price < 1";
                }
                if (Integer.parseInt(price[i]) == 1000000 && !condition.equals("")) {
                    condition += " or price between 0 and 1000000";
                }
                if (Integer.parseInt(price[i]) == 1000000 && condition.equals("")) {
                    condition += "price between 0 and 1000000";
                }
                if (Integer.parseInt(price[i]) == 1000001 && !condition.equals("")) {
                    condition += " or price > 1000001";
                }
                if (Integer.parseInt(price[i]) == 301 && condition.equals("")) {
                    condition += "price > 1000001";
                }
            }
        }

        String[] tag = parameterMap.get("tagsID");
        int totalTag = 14;
        if (tag != null && tag.length > 0) {
            for (int i = 0; i < tag.length; i++) {
                for (int j = 1; j < totalTag + 1; j++) {
                    if (tag[i].equals(String.valueOf(j)) && !condition.equals("")) {
                        condition += " and tag_id = " + String.valueOf(j);
                    }
                    if (tag[i].equals(String.valueOf(j)) && condition.equals("")) {
                        condition += "tag_id = " + String.valueOf(j);
                    }
                }
            }
        }
        return condition;
    }

    public void addCourse(Course c) {
        String sql = "INSERT INTO Courses (course_name,created_date,description,discount,img,price,status,update_date,username,level,long_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, c.getCourseName());
            pstm.setString(2, c.getCreatedDate() + "");
            pstm.setString(3, c.getDescription());
            pstm.setString(4, 0 + "");
            pstm.setString(5, c.getImage());
            pstm.setString(6, c.getPrice() + "");
            pstm.setInt(7, 3);
            pstm.setString(8, c.getUpdateDate() + "");
            pstm.setString(9, c.getAuthor().getUsername());
            pstm.setString(10, c.getLevel());
            pstm.setString(11, c.getLongDescription());
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("addCourse" + e.getMessage());
        }
    }

    public boolean isDuplicateCourseName(String name) {
        String sql = "  Select count(*) from courses where course_name=?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (Exception e) {
            System.out.println("isDuplicateCourseName: " + e.getMessage());
        }
        return false;
    }

    public Course getCourseByName(String courseName) {
        Course course = new Course();
        UserDAO userDAO = new UserDAO();
        String sql = "select * from courses where course_name = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course.setCourseId(rs.getInt(1));
                course.setCourseName(rs.getString(2));
                Date createDate = rs.getDate(3);
                LocalDateTime localDateTime = new Date(createDate.getTime()).toLocalDate().atStartOfDay();
                course.setCreatedDate(localDateTime);
                course.setDescription(rs.getString(4));
                course.setDiscount(rs.getInt(5));
                course.setImage(rs.getString(6));
                course.setPrice(rs.getDouble(7));
                course.setStatus(rs.getInt(8));
                Date updateDate = rs.getDate(9);
                localDateTime = new Date(updateDate.getTime()).toLocalDate().atStartOfDay();
                course.setUpdateDate(localDateTime);
                course.setAuthor(userDAO.getUserByUsername(rs.getString(10)));
                course.setLevel(rs.getString(11));
                course.setLongDescription(rs.getString(12));
            }
        } catch (SQLException e) {
            System.out.println("getCourseByName: " + e.getMessage());
        }
        return course;
    }

    public int countMyCourses(String username) {
        String sql = "select count(*)\n"
                + "from courses c left join enrollment e\n"
                + "on e.course_id = c.course_id\n"
                + "where e.username = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countMyCourses fail: " + e.getMessage());
        }
        return 0;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListPagingMyCourses(int index, int coursePerPage, String username) {

        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();

        String sql = " SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.rating) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE e.course_id in (select course_id from enrollment where username = ?)\n"
                + "GROUP BY c.course_id\n"
                + "order by c.course_id\n"
                + "  Offset ? Rows fetch next ? rows only";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, (index - 1) * coursePerPage);
            ps.setInt(3, coursePerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListPagingMyCourse: " + e.getMessage());
        }
        return listCourse;
    }

    public LinkedHashMap<Course, LinkedHashMap<Double, Integer>> getListSearchLecturerCourseForPaging(String username, int index, int coursePerPage, String inputSearch) {
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = new LinkedHashMap<>();
        String sql = "SELECT c.course_id, AVG(CONVERT(float, e.rating)) AS 'AVGRating', COUNT(e.course_id) AS 'RatingCount'\n"
                + "FROM courses c\n"
                + "LEFT JOIN enrollment e ON c.course_id = e.course_id\n"
                + "WHERE  c.username = ? and c.course_name like ?\n"
                + "GROUP BY c.course_id\n"
                + "ORDER BY c.course_id\n"
                + " Offset ? Rows fetch next ? rows only";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, "%" + inputSearch + "%");
            ps.setInt(3, (index - 1) * coursePerPage);
            ps.setInt(4, coursePerPage);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getCourseByID(rs.getInt(1));
                LinkedHashMap<Double, Integer> ratingInf = new LinkedHashMap<>();
                ratingInf.put(rs.getDouble(2), rs.getInt(3));
                listCourse.put(course, ratingInf);
            }
        } catch (SQLException e) {
            System.out.println("getListSearchLecturerCourseForPaging: " + e.getMessage());
        }
        return listCourse;
    }

    public void updateCourse(Course course, String status) {
        String sql = "update courses\n"
                + "set course_name = ?,description = ?,discount = ? ,img = ? ,price = ?,update_date = ?,level = ?,long_description = ?,status=?\n"
                + "where course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getDiscount());
            ps.setString(4, course.getImage());
            ps.setDouble(5, course.getPrice());
            LocalDateTime createdDate = LocalDateTime.now();
            String dateString = createdDate.toString();
            ps.setString(6, dateString);
            ps.setString(7, course.getLevel());
            ps.setString(8, course.getLongDescription());
            ps.setString(9, status);
            ps.setInt(10, course.getCourseId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateCourse: " + e.getMessage());
        }
    }

    public void addCourseTag(int courseId, int tagId) {
        String sql = "INSERT INTO course_tags (course_id,tag_id) values (?,?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, courseId);
                ps.setInt(2, tagId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("addCourseTag: " + e.getMessage());
        }
    }

    public void updateCourseTag(int id, int courseId, int tagId) {
        String sql = "update course_tags\n"
                + "set course_id = ?,tag_id =?\n"
                + "where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setInt(2, tagId);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateCourseTag: " + e.getMessage());
        }
    }

    public void deleteTag(String did, String courseID) {
        String sql = "DELETE FROM course_tags WHERE course_id = ? AND tag_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, courseID);
            ps.setString(2, did);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteTag: " + e.getMessage());
        }
    }

    public void ratingCourse(String courseid, String username, String rateStar) {
        String sql = "update [enrollment] set rating = ? where username = ? and course_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(3, courseid);
            ps.setString(2, username);
            ps.setString(1, rateStar);
            int check = ps.executeUpdate();
            System.out.println(check);
        } catch (SQLException e) {
            System.out.println("ratingCourse: " + e.getMessage());
        }
    }

    public boolean hasTag(int courseId, String tagName) {
        // Query to check if mapping exists between course and tag
        String sql = "SELECT 1 FROM course_tags "
                + "WHERE course_id = ? AND tag_id IN ("
                + "SELECT tag_id FROM tags WHERE tag_name = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, tagName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("hasTag: " + e.getMessage());
            return false;
        }
    }

    public List<Course> getListCourseByListTag(List<Tag> listTag) {
        String condition = "0";
        for (Tag tag : listTag) {
            condition += ", " + tag.getTagId();
        }
        String sql = "select top 3  c.course_id\n"
                + "  from courses c, course_tags ct\n"
                + "  where c.course_id = ct.course_id and ct.tag_id in (" + condition + ")";
        List<Course> listCourse = new ArrayList<>();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    listCourse.add(getCourseByID(rs.getInt(1)));
                }
            }
        } catch (Exception e) {
            System.out.println("getListCourseByListTag: " + e.getMessage());
        }
        return listCourse;
    }

    public Course getEnrolledCoursebyUsername(int aInt, String username) {
        Course course = new Course();
        String sql = "select e.*\n"
                + "from enrollment e left join courses c\n"
                + "on e.course_id = c.course_id\n"
                + "where c.course_id = ? and e.username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, aInt);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course.setCourseId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("getEnrolledCoursebyUsername: " + e.getMessage());
        }
        return course;
    }

    public LinkedHashMap<Course, Double> getCourseProgressbyUsername(String username) {
        LinkedHashMap<Course, Double> listCourseProgress = new LinkedHashMap<>();
        String sql = "SELECT cs.course_id, COUNT(cp.sectionid) * 100.0 / NULLIF(COUNT(cs.section_id), 0) AS Progress\n"
                + "FROM course_sections cs\n"
                + "JOIN enrollment e ON cs.course_id = e.course_id\n"
                + "LEFT JOIN course_process cp ON cs.section_id = cp.sectionid AND e.username = cp.username\n"
                + "WHERE e.username = ?\n"
                + "GROUP BY cs.course_id";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = getEnrolledCoursebyUsername(rs.getInt(1), username);
                listCourseProgress.put(course, rs.getDouble(2));
            }
        } catch (Exception e) {
            System.out.println("dao.CourseDAO.getCourseProgressbyUsername(): " + e);
        }
        return listCourseProgress;
    }

    public void updateFinishedDateOfCourse(String username, String couseId, String finishedDate) {
        String sql = "update [enrollment] set finishedDate = ? where course_id = ? and username = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, finishedDate);
                ps.setString(2, couseId);
                ps.setString(3, username);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("updateFinishedDateOfCourse: " + e.getMessage());
        }
    }

    public boolean checkFinisedStatusOfCourse(String username) {
        String sql = "select * from enrollment where username = ? and finishedDate is not null";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("checkFinisedStatusOfCourse" + e.getMessage());
        }
        return false;
    }

    public String getCourseIdBySectionId(String sectionId) {
        String sql = "select [course_id] from [course_sections] where [section_id] = ? ";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, sectionId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        } catch (Exception e) {
            System.out.println("checkFinisedStatusOfCourse" + e.getMessage());
        }
        return "";
    }

    public String getEnrollDate(String username, String courseId) {
        String sql = "select * from enrollment where course_id = ? and username = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, courseId);
                ps.setString(2, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString(5);
                }
            }
        } catch (Exception e) {
            System.out.println("getEnrollDate" + e.getMessage());
        }
        return "";
    }

    public String getFinishDate(String username, String courseId) {
        String sql = "select * from enrollment where course_id = ? and username = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, courseId);
                ps.setString(2, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString(5);
                }
            }
        } catch (Exception e) {
            System.out.println("getFinishDate" + e.getMessage());
        }
        return "";
    }

    public void banCourseByUserName(String username) {
        String sql = "update courses\n"
                + "  set status = 0\n"
                + "  where username = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("banCourseByUserName: " + e.getMessage());
        }
    }

    public int countVerifiedCourse(String searchContent) {
         String sql = "Select COUNT(*) from courses where (status ='1' or status ='0') and course_name like '%" + searchContent + "%'";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countVerifiedCourse:" + e.getMessage());
        }
        return 0;
    }

}
