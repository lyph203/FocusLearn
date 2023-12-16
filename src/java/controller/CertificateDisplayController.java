/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CertificateDAO;
import dao.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import model.Course;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name="CertificateDisplayController", urlPatterns={"/certificatedisplay"})
public class CertificateDisplayController extends HttpServlet {
   
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
            out.println("<title>Servlet CertificateDisplayController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CertificateDisplayController at " + request.getContextPath () + "</h1>");
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
        String courseId = request.getParameter("courseId");
        CourseDAO courseDAO = new CourseDAO();
        Course course = courseDAO.getCourseByID(Integer.parseInt(courseId));
        User user = (User) request.getSession().getAttribute("user");
        String enrollDate = courseDAO.getEnrollDate(user.getUsername(), courseId);
        String finishDate = courseDAO.getFinishDate(user.getUsername(), courseId);
        
        
        //Get Course detail information by id
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> courseDetail = courseDAO.getCourseDetail(courseId);
        
        //get student of current course
        int studentsNumber = courseDAO.getTotalStudentOfCourseById(courseId);
        
        //Get certificate
        String certificateImage = new CertificateDAO().getCertificateImage(user.getUsername(), courseId);
        
        
        request.setAttribute("course", course);
        request.setAttribute("studentsNumber", studentsNumber);
        request.setAttribute("enrollDate", enrollDate);
        request.setAttribute("finishDate", finishDate);
        request.setAttribute("courseDetail", courseDetail);
        request.setAttribute("certificateImage", certificateImage);
        
        request.getRequestDispatcher("certificate.jsp").forward(request, response);
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
