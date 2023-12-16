/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CourseDAO;
import dao.CourseSectionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Course;
import model.CourseSection;

/**
 *
 * @author Admin
 */
@WebServlet(name="LearnCourseController", urlPatterns={"/learn"})
public class LearnCourseController extends HttpServlet {
   
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
            out.println("<title>Servlet LearnCourseController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LearnCourseController at " + request.getContextPath () + "</h1>");
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
        CourseDAO courseDAO = new CourseDAO();
        CourseSectionDAO courseSectionDAO = new CourseSectionDAO();
        
        String courseId_raw = request.getParameter("id");
        String sectionId = request.getParameter("sectionId");
        
        int couseId = Integer.parseInt(courseId_raw);
        Course course = courseDAO.getCourseByID(couseId);
        course.setListSection(courseSectionDAO.getListSectionByCourseID(couseId));
        courseSectionDAO.getStatusSection(course.getListSection());
        
        //check null value
        if(sectionId == null || sectionId.isEmpty()) {
            sectionId = course.getListSection().get(0).getSectionId() + "";
        }
        
        CourseSection section = courseSectionDAO.getSectionId(sectionId);
        
        request.setAttribute("sectionId", sectionId);
        request.setAttribute("section", section);
        request.setAttribute("course", course);
        request.getRequestDispatcher("learncourse.jsp").forward(request, response); 
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
        processRequest(request, response);
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
