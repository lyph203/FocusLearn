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
import java.util.ArrayList;
import java.util.Map;
import model.Question;
import model.Tag;
import model.User;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "ListCreatedQuestionController", urlPatterns = {"/viewlistcreatedquestion"})
public class ListCreatedQuestionController extends HttpServlet {

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
            out.println("<title>Servlet ListCreatedQuestionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCreatedQuestionController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param req servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Get username of lecturer logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
        } else {
            //Get user name of lecturer logged in
            String username = user.getUsername();
            TagDAO tagDAO = new TagDAO();
            QuestionDAO questionDAO = new QuestionDAO();
            String indexPage = req.getParameter("index");
            Map<String, String[]> parameterMap = req.getParameterMap();
            //get list tag
            ArrayList<Tag> listTags = tagDAO.getListTags();

            //Get search content
            String searchContent = req.getParameter("searchContent");
            //check null value
            if (searchContent == null || searchContent.isEmpty()) {
                searchContent = "";
            }

            //Check for null value of indexPage
            if (indexPage == null) {
                indexPage = 1 + "";
            }
            int index = Integer.parseInt(indexPage);
            //Define total questions

            int totalQuestion = questionDAO.countAvailableQuestionByUsername(username, searchContent);
            if (parameterMap.get("tagsID") != null) {
                totalQuestion = questionDAO.countAvailableFilterQuestionByUsername(username, parameterMap, searchContent);
            }
            //Define number of questions display per page
            int questionPerPage = 6;
            //Find the index of last page
            int endPage = totalQuestion / questionPerPage;
            //Increase 1 page
            if (totalQuestion % questionPerPage != 0) {
                endPage++;
            }
            //Check if index user enterred over total pages
            if (index > endPage || index == 0) {
                String errorMsg = "Can not found the page you want to view.";
                req.setAttribute("errorMsg", errorMsg);
            } else {
                ArrayList<Question> listPagedQuestion;
                String[] tagsID = parameterMap.get("tagsID");

                //get list if use filter
                if (tagsID != null) {
                    listPagedQuestion = questionDAO.getListPagingFilterQuestionByUserName(username, index, questionPerPage, parameterMap, searchContent);
                } else {
                    //List question paged by index
                    listPagedQuestion = questionDAO.getListPagingQuestionByUserName(username, index, questionPerPage, searchContent);
                }
                //Set data attribute
                req.setAttribute("tagsID", tagsID);
                req.setAttribute("tag", index);
                req.setAttribute("endPage", endPage);
                req.setAttribute("totalQuestion", totalQuestion);
                req.setAttribute("search", searchContent);
                req.setAttribute("listPagedQuestion", listPagedQuestion);
            }
            req.setAttribute("listTags", listTags);
            req.getRequestDispatcher("createdquestiondetail.jsp").forward(req, response);
        }
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
        processRequest(request, response);
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
