/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.CourseSectionDAO;
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
import model.CourseSection;
import model.User;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "CreateCourseSectionController", urlPatterns = {"/createsection"})
public class CreateCourseSectionController extends HttpServlet {

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
            out.println("<title>Servlet CreateCourseSectionController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCourseSectionController at " + request.getContextPath() + "</h1>");
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
        //Get param
        String indexPage = request.getParameter("index");
        //Get username of lecturer logged in
        User user = (User) session.getAttribute("user");
        //Get user name of lecturer logged in
        String username = user.getUsername();
        //get average information of creater
        double lecturerRating = userDAO.getAverageRatingOfLectureByUserName(username);
        int totalReview = userDAO.getTotalReviewOfLectureByUserName(username);
        int totalStudent = userDAO.getTotalStudentOfLectureByUserName(username);
        //Check for null value of indexPage
        if (indexPage == null) {
            indexPage = 1 + "";
        }
        int index = Integer.parseInt(indexPage);
        //Define total courses
        int totalCourse = userDAO.getTotalCourseOfLectureByUserName(username);
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
            //List course paged by index of lecturer
            LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listLecturerCourse = courseDAO.getListLecturerCourseForPaging(username, index, coursePerPage);
            //Set data attribute
            request.setAttribute("endPage", endPage);
            request.setAttribute("tag", index);
            request.setAttribute("totalCourse", totalCourse);
            request.setAttribute("lecturerRating", lecturerRating);
            request.setAttribute("totalReview", totalReview);
            request.setAttribute("totalStudent", totalStudent);
            request.setAttribute("listLecturerCourse", listLecturerCourse);
        }
        request.getRequestDispatcher("lecturerhome.jsp").forward(request, response);
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
        // Get section names, video source links, and section descriptions from request
        String[] sectionNames = request.getParameterValues("sectionName[]");
        String[] videoSources = request.getParameterValues("videoSource[]");
        String[] sectionDescriptions = request.getParameterValues("sectionDescription[]");
        String courseName = request.getParameter("cname");
        //Get course inforamation
        Course course;
        CourseDAO courseDAO = new CourseDAO();
        course = courseDAO.getCourseByName(courseName);
        //Course Section initial
        CourseSection cs;
        CourseSectionDAO csDAO = new CourseSectionDAO();
        String msg = "";
        //Add each session part to database
        for (int i = 0; i < sectionNames.length; i++) {
            //Check if all fields is not empty at the same time
            if (!sectionNames[i].isEmpty() || !videoSources[i].isEmpty() || !sectionDescriptions[i].isEmpty()) {
                //Initial course section
                cs = new CourseSection(sectionNames[i], videoSources[i], sectionDescriptions[i], course);
                //Add couse section to database
                csDAO.addCourseSection(cs);
                msg = "Course Detail Added Successfully";
                request.setAttribute("msg", msg);
                doGet(request, response);
            }
        }
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
