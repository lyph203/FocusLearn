/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.CourseSection;

public class CourseSectionDAO extends DBContext {

    public void addCourseSection(CourseSection cs) {
        String sql = "INSERT INTO course_sections (description,title,video,course_id) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cs.getDescription());
            pstm.setString(2, cs.getTitle());
            pstm.setString(3, cs.getVideo());
            pstm.setInt(4, cs.getCourse().getCourseId());
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("addCourseSection" + e.getMessage());
        }
    }

    public List<CourseSection> getListSectionByCourseID(int intCourseID) {
        List<CourseSection> list = new ArrayList<>();
        String sql = " Select * from course_sections where course_id = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, intCourseID);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String des = rs.getString(2);
                String name = rs.getString(3);
                String video = rs.getString(4);
                CourseDAO courseDAO = new CourseDAO();
                Course c = courseDAO.getCourseByID(intCourseID);
                CourseSection cs = new CourseSection(id, name, video, des, c);
                list.add(cs);
            }
        } catch (SQLException e) {
            System.out.println("getListSectionByCourseID" + e.getMessage());
        }
        return list;
    }

    public void updateSections(List<CourseSection> sections, String sectionID) {
        String updateQuery = "UPDATE course_sections SET title=?, video=?, description=? WHERE section_id =?";
        try {
            // Prepare the update statement
            PreparedStatement pstm = connection.prepareStatement(updateQuery);
            for (CourseSection section : sections) {
                // Set the values for the statement
                pstm.setString(1, section.getTitle());
                pstm.setString(2, section.getVideo());
                pstm.setString(3, section.getDescription());
                pstm.setString(4, sectionID);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("updateSections" + e.getMessage());
        }
    }

    public CourseSection getSectionId(String sectionId) {
        String sql = "select * from [course_sections] where [section_id] = ?";
        CourseSection courseSection = new CourseSection();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, sectionId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    courseSection.setSectionId(rs.getInt(1));
                    courseSection.setDescription(rs.getString(2));
                    courseSection.setTitle(rs.getString(3));
                    courseSection.setVideo(rs.getString(4));
                }
            }
        } catch (Exception e) {
            System.out.println("getSectionId: " + e.getMessage());
        }
        return courseSection;
    }

    public void insertCompletenessSection(String username, String sectionId) {
        String sql = "INSERT INTO [course_process] (username, [sectionid], status) VALUES (?, ?, 1)";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setString(2, sectionId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insertCompletenessSection: " + e.getMessage());
        }
    }

    public boolean checkExistedComplete(String sectionId) {
        String sql = "select * from course_process where sectionId = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, sectionId);
            ResultSet rs = pstm.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("insertCompletenessSection: " + e.getMessage());
        }
        return true;
    }

    public void getStatusSection(List<CourseSection> listSection) {
        for (CourseSection courseSection : listSection) {
            setStatus(courseSection);
        }
    }

    public void setStatus(CourseSection cs) {
        String sql = "select cs.section_id, cs.description, cs.title, cs.video, cs.course_id, cp.status\n"
                + "  from course_process cp, course_sections cs\n"
                + "  where cp.sectionid = cs.section_id and cs.section_id = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cs.getSectionId() + "");
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                cs.setStatus(1);
            }
        } catch (SQLException e) {
            System.out.println("setStatus: " + e.getMessage());
        }
    }

    public int countTotalSectionOfCourseBySectionId(String sectionId) {
        String sql = "select count(section_id) from course_sections\n"
                + "  where course_id in (select course_id from course_sections where section_id = ?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, sectionId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("countTotalSectionOfCourseBySectionId: " + e.getMessage());
        }
        return 0;
    }

    public int countTotalFinishedSectionOfCourseBySectionId(String username, String sectionId) {
        String sql = "select count(sectionid) from course_process\n"
                + "  where username = ? and sectionid in (\n"
                + "	select section_id from course_sections\n"
                + "	where course_id in (select course_id from course_sections where section_id = ?)\n"
                + ")";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, sectionId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("countTotalSectionOfCourseBySectionId: " + e.getMessage());
        }
        return 0;
    }
    
    public boolean checkFinishedCourse(String username, String sectionId) {
        return countTotalSectionOfCourseBySectionId(sectionId) == countTotalFinishedSectionOfCourseBySectionId(username, sectionId);
    }
    
    

}
