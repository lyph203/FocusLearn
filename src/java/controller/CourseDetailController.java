/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import model.Course;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CourseDetailController", urlPatterns = {"/coursedetail"})
public class CourseDetailController extends HttpServlet {

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
            out.println("<title>Servlet CourseDetailController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CourseDetailController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        //Initial Dao instance
        CourseDAO courseDAO = new CourseDAO();
        UserDAO userDAO = new UserDAO();

        //Get course id param
        String courseId = request.getParameter("id");

        //Get Course detail information by id
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> courseDetail = courseDAO.getCourseDetail(courseId);

        //Get list suggest course
        LinkedHashMap<Course, Double> listSuggested = courseDAO.getListSuggestedCourse(courseId);

        //Get username of the creator of course
        String username = courseDetail.keySet().iterator().next().getAuthor().getUsername();

        //get average information of creater
        double lecturerRating = userDAO.getAverageRatingOfLectureByUserName(username);
        int totalReview = userDAO.getTotalReviewOfLectureByUserName(username);
        int totalStudent = userDAO.getTotalStudentOfLectureByUserName(username);
        int totalCourse = userDAO.getTotalCourseOfLectureByUserName(username);

        //get student of current course
        int studentsNumber = courseDAO.getTotalStudentOfCourseById(courseId);

        //get username of student logged in
        User user = (User) session.getAttribute("user");
        String studentUsername = null;
        boolean isEnrolled = false;
        //Check if user is not logged in
        if (user != null) {
            studentUsername = user.getUsername();
            isEnrolled = courseDAO.checkEnrolledCourseByUsername(courseId, studentUsername);
            //Only send if user enrolled course
            if (isEnrolled)
                request.setAttribute("isEnrolled", isEnrolled);
        }

        //set data for send to frontend
        request.setAttribute("courseDetail", courseDetail);
        request.setAttribute("listSuggested", listSuggested);
        request.setAttribute("lecturerRating", lecturerRating);
        request.setAttribute("totalReview", totalReview);
        request.setAttribute("totalStudent", totalStudent);
        request.setAttribute("totalCourse", totalCourse);
        request.setAttribute("studentsNumber", studentsNumber);
        request.getRequestDispatcher("coursedetail.jsp").forward(request, response);
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
