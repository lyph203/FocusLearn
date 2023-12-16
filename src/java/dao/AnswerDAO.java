/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dal.DBContext;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Answer;

/**
 *
 * @author Admin
 */
public class AnswerDAO extends DBContext {

    public List<Answer> getListAnswerByQuestionId(String questionId, int index) {
        String sql = "select * from answers where question_id = ?\n"
                + "	order by answer_id offset ? rows fetch next 3 rows only ";
        List<Answer> listAnswer = new ArrayList<>();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, questionId);
                ps.setInt(2, (index - 1) * 3);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    listAnswer.add(getAnswerById(rs.getString(1)));
                }
            }
        } catch (Exception e) {
            System.out.println("getListAnswerByQuestionId: " + e.getMessage());
        }
        return listAnswer;
    }

    public Answer getAnswerById(String id) {
        String sql = "select * from [answers] where answer_id = ?";
        Answer answer = new Answer();
        QuestionDAO questionDAO = new QuestionDAO();
        UserDAO udao = new UserDAO();
        try {
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    answer.setAnswerId(rs.getInt(1));
                    answer.setContent(rs.getString(2));
                    Date createDate = rs.getDate(3);
                    LocalDateTime localDateTime = new Date(createDate.getTime()).toLocalDate().atStartOfDay();
                    answer.setCreateDate(localDateTime);
                    answer.setQuestion(questionDAO.getQuestionByID(rs.getInt(4)));
                    answer.setUser(udao.getUserByUsername(rs.getString(5)));
                }
            }
        } catch (Exception e) {
            System.out.println("getAnswerById: " + e.getMessage());
        }
        return answer;
    }

    public void addNewAnswer(Answer answer) {
        String sql = "insert into answers\n"
                + "  values (?, ?, ?, ?)";
        try {
            try(PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, answer.getContent());
                ps.setString(2, answer.getCreateDate());
                ps.setInt(3, answer.getQuestion().getQuestionId());
                ps.setString(4, answer.getUser().getUsername());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println("addNewAnswer: " + e.getMessage());
        }
    }
}
