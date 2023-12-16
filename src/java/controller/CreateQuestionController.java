/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.QuestionDAO;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import model.Question;
import model.Tag;
import model.User;

/**
 *
 * @author khoa2
 */
@WebServlet(name="CreateQuestionController", urlPatterns={"/createquestion"})
public class CreateQuestionController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CreateQuestionController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateQuestionController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //Get session
        HttpSession session = request.getSession();
        // Retrieve the user object from the session
        User user = (User) session.getAttribute("user");
        //Ensure user logged in before create question
//        if (user ==null){
//            response.sendRedirect("login.jsp");
//        }
        //else{
            
        request.getRequestDispatcher("allquestion.jsp").forward(request, response);
       // }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //Get session
        HttpSession session = request.getSession();
        // Retrieve the user object from the session
        User user = (User) session.getAttribute("user");
        //Get Parameter user enterred
        String title = request.getParameter("qtitle");
        String content = request.getParameter("qcontent");
        String[] tagID = request.getParameterValues("tagID");
        String[] tagName = request.getParameterValues("qtag");
        String successMsg ="";
        //Add question to db
        QuestionDAO qDAO = new QuestionDAO();
        Question q = new Question();
        q.setContent(content);
        q.setTitle(title);
        q.setUser(user);
        q.setCreatedDate(LocalDateTime.now());
        q.setStatus("1");
        qDAO.addQuestion(q);
        int qid = q.getQuestionId();
        //Add question tag
        TagDAO tagDAO = new TagDAO();
        //Check if TAG name is not null
        if (tagName != null) {
            //Iterate through added Tag array for add
            for (int i = 0; i < tagName.length; i++) {
                //Check if tag name is not null
                if (!tagName[i].isEmpty()) {
                    //Check if tag name is not exist in database
                    if (tagDAO.checkExistTagName(tagName[i])) {
                        //Check if question has not contain tag name
                        if (qDAO.hasQuestionTag(qid, tagName[i])) {
                            successMsg = "Tag name is duplicated";
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
                        //Set update/add course tag to DB
                        if (tagID[i].equals("add")) {
                            tagDAO.addTag(tag);
                            qDAO.addQuestionTag(qid, tagDAO.getTagIdByName(tagName[i]));
                        }
                    }
                }
            }
        }
        response.sendRedirect("modifyquestion?id="+qid);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
