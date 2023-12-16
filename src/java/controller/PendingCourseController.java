/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.Course;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "VerifyCourseController", urlPatterns = {"/pendingcourse"})
public class PendingCourseController extends HttpServlet {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
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
        CourseDAO courseDAO = new CourseDAO();
        String indexPage = request.getParameter("index");
        String searchContent = request.getParameter("search");
        //Check for null value of indexPage
        if (indexPage == null) {
            indexPage = 1 + "";
        }
        //Check for null value of searchContent
        if (searchContent == null) {
            searchContent = "";
        }
        int index = Integer.parseInt(indexPage);
        //Define total courses
        int totalCourse = courseDAO.countTotalPendingCourse(searchContent);
        //Define number of courses display per page
        int coursePerPage = 10;
        //Find the index of last page
        int endPage = totalCourse / coursePerPage;
        //Increase 1 page
        if (totalCourse % coursePerPage != 0) {
            endPage++;
        }
        
        //Check if index user enterred over total pages
        if (index > endPage || index == 0) {
            String errorMsg = "Can not found the page you want to view.";
            request.setAttribute("errorMsg", errorMsg);
        } else {
            //List course paged by index
            LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = courseDAO.getListPagingCourse(index, coursePerPage, 2, searchContent);
            LinkedHashMap<Course, Integer> listNumberOfUser = courseDAO.getNumberOfUserEachCourse();
            //Set data attribute
            request.setAttribute("endPage", endPage);
            request.setAttribute("tag", index);
            request.setAttribute("search", searchContent);
            request.setAttribute("totalCourse", totalCourse);
            request.setAttribute("listCourse", listCourse);
            request.setAttribute("listNumberOfUser", listNumberOfUser);
        }
        request.getRequestDispatcher("pendingCourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void destroy() {
        super.destroy();
        // Shutdown the thread pool when the servlet is destroyed
        executorService.shutdown();
    }
}
