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
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Question;
import model.Tag;

/**
 *
 * @author Admin
 */
public class QuestionDAO extends DBContext {

    public Question getQuestionByID(int questionID) {
        Question question = new Question();
        UserDAO userDAO = new UserDAO();
        String sql = "select * from questions where question_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                question.setQuestionId(rs.getInt(1));
                question.setContent(rs.getString(2));
                Date createDate = rs.getDate(3);
                LocalDateTime localDateTime = new Date(createDate.getTime()).toLocalDate().atStartOfDay();
                question.setCreatedDate(localDateTime);
                question.setTitle(rs.getString(4));
                question.setUpdateDate(null);
                if (rs.getDate(5) != null) {
                    createDate = rs.getDate(5);
                    localDateTime = new Date(createDate.getTime()).toLocalDate().atStartOfDay();
                    question.setUpdateDate(localDateTime);
                }
                question.setUser(userDAO.getUserByUsername(rs.getString(6)));
                question.setStatus(rs.getString(7));
            }
        } catch (SQLException e) {
            System.out.println("getQuestionByID: " + e.getMessage());
        }
        return question;
    }

    private String getConditionFilter(Map<String, String[]> parameterMap) {
        String condition = "";
        String[] tag = parameterMap.get("tagsID");

        if (tag != null && tag.length > 0) {
            for (int i = 0; i < tag.length; i++) {
                if (condition.equals("") || condition.isEmpty()) {
                    condition += " and (tag_id = " + tag[i];
                } else {
                    condition += " or tag_id = " + tag[i];
                }
            }
            condition += ")";
        }
        return condition;
    }

    public int countAvailableQuestion(String searchContent) {
        String sql = "Select COUNT(*) from questions where title like '%" + searchContent + "%' or username like '%" + searchContent + "%'";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countAvailableQuestion: " + e.getMessage());
        }
        return 0;
    }

    public int countAvailableFilterQuestion(Map<String, String[]> parameterMap, String searchContent) {
        String sql = "";
        try {
            String condition = getConditionFilter(parameterMap);
            sql = "select COUNT(*) from questions q, question_tags qt\n"
                    + "where q.question_id = qt.question_id " + condition + " and (q.title like '%" + searchContent + "%' or q.username like '%" + searchContent + "%')";

            PreparedStatement pstm = connection.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countAvailableFilterQuestion" + e.getMessage());
        }
        return 0;
    }

    public ArrayList<Question> getListPagingFilterQuestion(int index, int questionPerPage, Map<String, String[]> parameterMap, String searchContent) {
        ArrayList<Question> listQuestion = new ArrayList<>();
        String sql = "";

        try {
            String condition = getConditionFilter(parameterMap);
            sql = "SELECT distinct q.question_id from questions q, question_tags qt\n"
                    + "where q.question_id = qt.question_id " + condition + " and (q.title like '%" + searchContent + "%' or q.username like '%" + searchContent + "%')\n"
                    + "Order by q.question_id\n"
                    + "Offset ? Rows fetch next ? rows only";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, (index - 1) * questionPerPage);
            pstm.setInt(2, questionPerPage);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Question question = getQuestionByID(rs.getInt(1));
                listQuestion.add(question);
            }
        } catch (Exception e) {
            System.out.println("getListPagingFilterQuestion: " + e.getMessage());
        }

        return listQuestion;
    }

    public ArrayList<Question> getListPagingQuestion(int index, int questionPerPage, String searchContent) {
        ArrayList<Question> listQuestion = new ArrayList<>();
        String sql = "";

        try {
            sql = "SELECT q.question_id from questions q\n"
                    + "where q.title like '%" + searchContent + "%' or q.username like '%" + searchContent + "%'\n"
                    + "Order by q.question_id\n"
                    + "Offset ? Rows fetch next ? rows only";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, (index - 1) * questionPerPage);
            pstm.setInt(2, questionPerPage);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Question question = getQuestionByID(rs.getInt(1));
                listQuestion.add(question);
            }
        } catch (Exception e) {
            System.out.println("getListPagingQuestion: " + e.getMessage());
        }

        return listQuestion;
    }

    public void addQuestion(Question q) {
        String sql = "";
        try {
            sql = "Insert into questions (content,created_date,title,username,status) values (?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, q.getContent());
            pstm.setString(2, q.getCreatedDate());
            pstm.setString(3, q.getTitle());
            pstm.setString(4, q.getUser().getUsername());
            pstm.setString(5, q.getStatus());
            int rowAffected = pstm.executeUpdate();
            if (rowAffected > 0) {
                ResultSet generatedKeys = pstm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedQuestionId = generatedKeys.getInt(1);
                    q.setQuestionId(generatedQuestionId);
                }
            }
        } catch (SQLException e) {
            System.out.println("addQuestion: " + e.getMessage());
        }
    }

    public int getTotalAnswerByQuestionId(String questionId) {
        String sql = "select count(*) as total from answers where question_id = ?";
        int count = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, questionId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            System.out.println("getTotalAnswerByQuestionId: " + e.getMessage());
        }
        return count;
    }

    public List<Tag> getListTagByQuestionId(String questionId) {
        String sql = "select * from [question_tags] where question_id = ?";
        List<Tag> listTag = new ArrayList<>();
        TagDAO tagDAO = new TagDAO();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, questionId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Tag tag = tagDAO.getTagByID(rs.getInt(3));
                    listTag.add(tag);
                }
            }
        } catch (Exception e) {
            System.out.println("getListTagByQuestionId: " + e.getMessage());
        }
        return listTag;
    }

    public boolean hasQuestionTag(int questionId, String tagName) {
        String sql = "SELECT 1 FROM question_tags "
                + "WHERE question_id = ? AND tag_id IN ("
                + "SELECT tag_id FROM tags WHERE tag_name = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionId);
            ps.setString(2, tagName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("hasQuestionTag: " + e.getMessage());
            return false;
        }
    }

    public void addQuestionTag(int questionId, int tagId) {
        String sql = "INSERT INTO question_tags (question_id,tag_id) values (?,?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, questionId);
                ps.setInt(2, tagId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("addQuestionTag: " + e.getMessage());
        }
    }

    public int getTotalQuestionBySearch(String username, String inputSearch) {
        String sql = "SELECT COUNT(question_id)\n"
                + "FROM questions\n"
                + "WHERE [username] = ?\n"
                + "AND ([content] LIKE ? OR [title] LIKE ?); ";
        int numberQuestion = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, "%" + inputSearch + "%");
                ps.setString(3, "%" + inputSearch + "%");
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    numberQuestion = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("getTotalCourseBySearch: " + e.getMessage());
        }
        return numberQuestion;
    }

    public int getTotalQuestionByUserName(String username) {
        String sql = "SELECT COUNT(question_id)\n"
                + "FROM [FocusLearn].[dbo].[questions]\n"
                + "where [username] = ?";
        int numberQuestion = 0;
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    numberQuestion = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("getTotalQuestionByUserName: " + e.getMessage());
        }
        return numberQuestion;
    }

    public ArrayList<Question> getListPagingFilterQuestionByUserName(String username, int index, int questionPerPage, Map<String, String[]> parameterMap, String searchContent) {
        ArrayList<Question> listQuestion = new ArrayList<>();
        String sql = "";
        try {
            String condition = getConditionFilter(parameterMap);
            sql = "SELECT q.question_id from questions q, question_tags qt\n"
                    + "where q.question_id = qt.question_id " + condition + " and (q.title like '%" + searchContent + "%' or q.username like '%" + searchContent + "%') and username = ?\n"
                    + "Order by q.question_id\n"
                    + "Offset ? Rows fetch next ? rows only";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, (index - 1) * questionPerPage);
            pstm.setInt(2, questionPerPage);
            pstm.setString(3, username);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Question question = getQuestionByID(rs.getInt(1));
                listQuestion.add(question);
            }
        } catch (SQLException e) {
            System.out.println("getListPagingFilterQuestionByUserName: " + e.getMessage());
        }

        return listQuestion;
    }

    public ArrayList<Question> getListPagingQuestionByUserName(String username, int index, int questionPerPage, String searchContent) {
        ArrayList<Question> listQuestion = new ArrayList<>();
        String sql = "";

        try {
            sql = "SELECT q.question_id from questions q\n"
                    + "where username = ? and ( q.title like '%" + searchContent + "%' or q.username like '%" + searchContent + "%')\n"
                    + "Order by q.question_id\n"
                    + "Offset ? Rows fetch next ? rows only";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            pstm.setInt(2, (index - 1) * questionPerPage);
            pstm.setInt(3, questionPerPage);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Question question = getQuestionByID(rs.getInt(1));
                listQuestion.add(question);
            }
        } catch (SQLException e) {
            System.out.println("getListPagingQuestionByUserName: " + e.getMessage());
        }
        return listQuestion;
    }

    public int countAvailableFilterQuestionByUsername(String username, Map<String, String[]> parameterMap, String searchContent) {
        String sql = "";
        try {
            String condition = getConditionFilter(parameterMap);
            sql = "select COUNT(*) from questions q, question_tags qt\n"
                    + "where q.question_id = qt.question_id " + condition + " and (q.title like '%" + searchContent + "%' or q.username like '%" + searchContent + "%') and username = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countAvailableFilterQuestion" + e.getMessage());
        }
        return 0;
    }

    public int countAvailableQuestionByUsername(String username, String searchContent) {
        String sql = "Select COUNT(*) from questions where username = ? and (title like '%" + searchContent + "%' or username like '%" + searchContent + "%')";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, username);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("countAvailableQuestion: " + e.getMessage());
        }
        return 0;
    }

    public void updateQuestion(String qid, Question q) {
        String sql = "UPDATE questions SET content = ? , title = ?, update_date = ? WHERE question_id = ?";

        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, q.getContent());
            pstm.setString(2, q.getTitle());
            pstm.setString(3, q.getUpdateDate());
            pstm.setString(4, qid);
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("updateQuestion: " + e.getMessage());
        }
    }

    public void deleteQuestion(String qid) {
        String sql = "Update questions set status = 0 WHERE question_id = ?";
        try {
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, qid);
            pstm.execute();
        } catch (SQLException e) {
            System.out.println("deleteQuestion: " + e.getMessage());
        }
    }

    public boolean hasQuestionTag(String qid, String tagName) {
        // Query to check if mapping exists between course and tag
        String sql = "SELECT 1 FROM question_tags "
                + "WHERE question_id = ? AND tag_id IN ("
                + "SELECT tag_id FROM tags WHERE tag_name = ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, qid);
            ps.setString(2, tagName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("hasQuestionTag: " + e.getMessage());
            return false;
        }
    }

    public void addQuestionTag(String qid, int tagId) {
        String sql = "INSERT INTO question_tags (question_id, tag_id) VALUES (?, ?)";
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, qid);
                ps.setInt(2, tagId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("addQuestionTag: " + e.getMessage());
        }
    }

    public void deleteQuestionTag(String did, String qID) {
        String sql = "DELETE FROM question_tags WHERE question_id = ? AND tag_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, qID);
            ps.setString(2, did);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleteQuestionTag: " + e.getMessage());
        }
    }
}
