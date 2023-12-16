/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import model.Course;
import model.Tag;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "ListCourseController", urlPatterns = {"/viewCourse"})
public class ListCourseController extends HttpServlet {

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
            out.println("<title>Servlet ListCourseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListCourseController at " + request.getContextPath() + "</h1>");
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

        TagDAO tagDAO = new TagDAO();
        CourseDAO courseDAO = new CourseDAO();
        String indexPage = request.getParameter("index");
        Map<String, String[]> parameterMap = request.getParameterMap();

        //get list tag
        ArrayList<Tag> listTags = tagDAO.getListTags();

        //Get search content
        String searchContent = request.getParameter("searchContent");
        //check null value
        if (searchContent == null || searchContent.isEmpty()) {
            searchContent = "";
        }

        //Check for null value of indexPage
        if (indexPage == null) {
            indexPage = 1 + "";
        }
        int index = Integer.parseInt(indexPage);
        //Define total courses

        int totalCourse = courseDAO.countAvailableCourse(searchContent);
        if (parameterMap.get("ratings") != null || parameterMap.get("level") != null || parameterMap.get("price") != null || parameterMap.get("tagsID") != null) {
            totalCourse = courseDAO.countAvailableFilterCourse(parameterMap, searchContent);
        }
        //Define number of courses display per page
        int coursePerPage = 6;
        //Find the index of last page
        int endPage = totalCourse / coursePerPage;
        //Increase 1 page
        if (totalCourse % coursePerPage != 0) {
            endPage++;
        }

        String[] ratings = parameterMap.get("ratings");
        String[] level = parameterMap.get("level");
        String[] price = parameterMap.get("price");
        String[] tagsID = parameterMap.get("tagsID");
        //Check if index user enterred over total pages
        if (index > endPage || index == 0) {
            String errorMsg = "Can not found the page you want to view.";
            request.setAttribute("errorMsg", errorMsg);
        } else {
            LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listPagedCourse;
            //get list if use filter
            if (parameterMap.get("ratings") != null || parameterMap.get("level") != null || parameterMap.get("price") != null || parameterMap.get("tagsID") != null) {
                listPagedCourse = courseDAO.getListPagingFilterCourse(index, coursePerPage, parameterMap, searchContent);
            } else {
                //List course paged by index
                listPagedCourse = courseDAO.getListPagingCourse(index, coursePerPage, 1, searchContent);
            }
            request.setAttribute("listPagedCourse", listPagedCourse);
        }
        //Set data attribute
        request.setAttribute("ratings", ratings);
        request.setAttribute("level", level);
        request.setAttribute("tagsID", tagsID);
        request.setAttribute("tag", index);
        request.setAttribute("price", price);
        request.setAttribute("endPage", endPage);
        request.setAttribute("totalCourse", totalCourse);

        request.setAttribute("search", searchContent);
        request.setAttribute("listTags", listTags);
        request.getRequestDispatcher("allcourses.jsp").forward(request, response);
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
