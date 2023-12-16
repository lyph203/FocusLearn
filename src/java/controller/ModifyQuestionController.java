/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AnswerDAO;
import dao.CourseDAO;
import dao.QuestionDAO;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import model.Answer;
import model.Course;
import model.Question;
import model.Tag;
import model.User;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "ModifyQuestionController", urlPatterns = {"/modifyquestion"})
public class ModifyQuestionController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ModifyQuestionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ModifyQuestionController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionId = request.getParameter("id");
        QuestionDAO questionDAO = new QuestionDAO();
        TagDAO tagDAO = new TagDAO();
        CourseDAO courseDAO = new CourseDAO();
        AnswerDAO answerDAO = new AnswerDAO();

        String index_raw = request.getParameter("index");
        int index = 1;
        //check null value
        if (index_raw != null && !index_raw.isEmpty()) {
            index = Integer.parseInt(index_raw);
        }
        Question question;
        String did = (String) request.getAttribute("did");
        String questionIDDelete = (String) request.getAttribute("questionIDDelete");
        String finalQID = "0";
        //Check if load after deleting tag
        if (did != null) {
            question = questionDAO.getQuestionByID(Integer.parseInt(questionIDDelete));
            finalQID = questionIDDelete;
        } else {
            question = questionDAO.getQuestionByID(Integer.parseInt(questionId));
            finalQID = questionId;
        }
        int totalAnswer = questionDAO.getTotalAnswerByQuestionId(finalQID);
        List<Tag> listTag = questionDAO.getListTagByQuestionId(finalQID);
        List<Tag> listAvailableTag = tagDAO.getListTagUseByQuestions();
        question.setListTag(listTag);
        List<Course> listRelatedCourse = courseDAO.getListCourseByListTag(listTag);
        List<Answer> listAnswer = answerDAO.getListAnswerByQuestionId(finalQID, index);

        double endPage = Math.ceil(totalAnswer * 1.0 / 3);

        request.setAttribute("question", question);
        request.setAttribute("totalAnswer", totalAnswer);
        request.setAttribute("listAvailableTag", listAvailableTag);
        request.setAttribute("listRelatedCourse", listRelatedCourse);
        request.setAttribute("listAnswer", listAnswer);
        request.setAttribute("index", index);
        request.setAttribute("endPage", endPage);
        request.getRequestDispatcher("modifyquestion.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String qid = request.getParameter("id");
        String btnUpdate = request.getParameter("updateBtn");
        String btnDelete = request.getParameter("deleteBtn");
        String title = request.getParameter("title");
        String content = "";
        String[] paras = request.getParameterValues("content");
        //Join the space
        if (paras != null) {
            content = String.join("\n\n", paras);
        }
        //get user object from session
        User user = (User) request.getSession().getAttribute("user");
        Question q = new Question();
        QuestionDAO qDAO = new QuestionDAO();
        String msg = "";
        //Check if updating question 
        if (btnUpdate != null) {
            //Set up question attribute
            q.setTitle(title);
            q.setContent(content);
            q.setUpdateDate(LocalDateTime.now());
            q.setUser(user);
            qDAO.updateQuestion(qid, q);
            //update Tag
            TagDAO tagDAO = new TagDAO();
            //Get tag id, tag name
            String[] tagID = request.getParameterValues("tagID");
            String[] tagName = request.getParameterValues("tagName");
            //Get original values of Tag
            List<Tag> originalTags = qDAO.getListTagByQuestionId(qid);
            int originalNumTags = originalTags.size();
            String[] originalTagNames = new String[originalNumTags];
            //Get original Tag Name
            for (int i = 0; i < originalNumTags; i++) {
                originalTagNames[i] = originalTags.get(i).getTagName();
            }
            //Check if TAG name is not null
            if (tagName != null) {
                int newNumTags = tagName.length;
                //Iterate through added Tag array for add
                for (int i = 0; i < newNumTags; i++) {
                    //Check if tag name is not null
                    if (!tagName[i].isEmpty()) {
                        //Check if tag name is not exist in database
                        if (tagDAO.checkExistTagName(tagName[i])) {
                            //Check if course has not contain tag name
                            if (qDAO.hasQuestionTag(qid, tagName[i])) {
                                msg = "Tag name is duplicated";
                                break;
                            } else {
                                // Get existing tag ID  
                                int tagId = tagDAO.getTagIdByName(tagName[i]);
                                // Add course tag only
                                qDAO.addQuestionTag(qid, tagId);
                            }
                        } else {
                            Tag tag = new Tag();
                            tag.setTagName(tagName[i]);
                            tagDAO.addTag(tag);
                            qDAO.addQuestionTag(qid, tagDAO.getTagIdByName(tagName[i]));
                        }
                    }
                }
                msg = "Updated, Tag name is added";
            } else {
                msg = "Update Successfully";
            }
        } //Check if deleting question
        else if (btnDelete != null) {
            qDAO.deleteQuestion(qid);
            msg = "Question Closed";
        }
        request.setAttribute("msg", msg);
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
