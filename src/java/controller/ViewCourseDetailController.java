/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.CourseSectionDAO;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import model.Course;
import model.CourseSection;
import model.Tag;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "ViewCourseDetailController", urlPatterns = {"/cmviewcourse"})
public class ViewCourseDetailController extends HttpServlet {

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
            out.println("<title>Servlet ViewCourseDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewCourseDetailController at " + request.getContextPath() + "</h1>");
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
        CourseDAO courseDao = new CourseDAO();
        //get courseID
        String courseID = request.getParameter("cid");
        int intCID = Integer.parseInt(courseID);
        //Check if course ID is null or empty
        if (courseID != null || courseID.trim().isEmpty()) {
            LinkedHashMap<Course, LinkedHashMap<Double, Integer>> course = courseDao.getCourseInforByID(Integer.parseInt(courseID));
            //Get list section
            CourseSectionDAO csDAO = new CourseSectionDAO();
            List<CourseSection> listSection = csDAO.getListSectionByCourseID(intCID);
            //Check if empty section of course
            if (listSection.isEmpty()) {
                String nullMSG = "No section available";
                request.setAttribute("nullMSG", nullMSG);
            } else {
                request.setAttribute("listSection", listSection);
            }
            
            //Get list tag
            TagDAO tagDAO = new TagDAO();
            List <Tag> listTag = tagDAO.getListTagByCourseID(intCID);
            //Check if empty section of course
            if (listTag.isEmpty()) {
                String tagNullMSG = "No Tag available";
                request.setAttribute("tagNullMSG", tagNullMSG);
            } else {
                request.setAttribute("listTag", listTag);
            }
            request.setAttribute("course", course);
            request.getRequestDispatcher("bancoursedetail.jsp").forward(request, response);
        } else {
            System.out.println("Error at ViewCourseDetailController, ID is null");
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
