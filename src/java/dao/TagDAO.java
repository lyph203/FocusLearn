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
import model.Tag;

/**
 *
 * @author Admin
 */
public class TagDAO extends DBContext {

    public ArrayList<Tag> getListTags() {
        ArrayList<Tag> listTags = new ArrayList<>();
        try {
            String sql = "SELECT * FROM [tags]";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                Tag tag = new Tag(id, name);
                listTags.add(tag);
            }
        } catch (SQLException e) {
            System.out.println("getListTags: " + e.getMessage());
        }
        return listTags;
    }

    public List<Tag> getListTagByCourseID(int intCourseID) {
        List<Tag> list = new ArrayList<>();
        String sql = " Select * from course_tags where course_id = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, intCourseID);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int tagID = rs.getInt(3);
                Tag tag = getTagByID(tagID);
                list.add(tag);
            }
        } catch (SQLException e) {
            System.out.println("getListTagByCourseID" + e.getMessage());
        }
        return list;
    }

    public Tag getTagByID(int tagId) {
        String sql = "select * from tags where tag_id = ?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, tagId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Tag(rs.getInt(1), rs.getString(2));
                }
            }
        } catch (Exception e) {
            System.out.println("getTagByID: " + e.getMessage());
        }
        return new Tag();
    }

    public boolean checkExistTagName(String name) {
        String sql = "Select * from tags where tag_name=?";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            }
        } catch (Exception e) {
            System.out.println("checkExistTagName: " + e.getMessage());
        }
        return false;
    }

    public void addTag(Tag tag) {
        String sql = "INSERT INTO tags (tag_name) values (?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, tag.getTagName());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("addTag: " + e.getMessage());
        }
    }

    public int getTagIdByName(String name) {
        String sql = "select * from tags where tag_name = ?";
        int tagID = 0;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tagID = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("getTagIdByName: " + e.getMessage());
        }
        return tagID;
    }

    public void updateTag(Tag tag) {
        String sql = "update tags\n"
                + "set tag_name =?\n"
                + "where tag_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tag.getTagName());
            ps.setInt(2, tag.getTagId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateTag: " + e.getMessage());
        }
    }

    public List<Tag> getListTagUseByQuestions() {
        String sql = "SELECT *\n"
                + "  FROM [FocusLearn].[dbo].[tags]\n"
                + "  where tag_id in (select distinct tag_id from question_tags)";
        List<Tag> listTag = new ArrayList<>();
        TagDAO tagDAO = new TagDAO();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Tag tag = tagDAO.getTagByID(rs.getInt(1));
                    listTag.add(tag);
                }

            }
        } catch (Exception e) {
            System.out.println("getListTagUseByQuestions: " + e.getMessage());
        }
        return listTag;
    }

}
