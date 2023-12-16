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
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import model.Course;
import model.User;
import util.Validation;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "CreateCourseController", urlPatterns = {"/createcourse"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, 
        maxFileSize = 1024 * 1024 * 5, 
        maxRequestSize = 1024 * 1024 * 10 
)
public class CreateCourseController extends HttpServlet {

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
            out.println("<title>Servlet CreateCourseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateCourseController at " + request.getContextPath() + "</h1>");
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
    private static final String uploadFolder = "images";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        //Get parameter
        String act = request.getParameter("act");
        String name = request.getParameter("name");
        String shortDescription = request.getParameter("shortDescription");
        String price = request.getParameter("price");
        String level = request.getParameter("level");
        String longDescription = request.getParameter("longDescription");
        String fileName = request.getParameter("img");
        //Get username of lecturer logged in
        User author = (User) session.getAttribute("user");
        String msg = "";
        //Check if creating course
        if ("add".equals(act)) {
            // Perform input validation
            Validation validate = new Validation();
            String errorMsg = validate.validateCourseInformationInput(name, price);
            //Check if error message is received
            if (errorMsg != null && !errorMsg.isEmpty()) {
                msg = errorMsg;
                request.setAttribute("msg", msg);
                doGet(request, response);
            } else {
                LocalDateTime createdDate = LocalDateTime.now();
                LocalDateTime updatedDate = createdDate;
                CourseDAO courseDao = new CourseDAO();
                Course c = new Course(name, shortDescription, Double.parseDouble(price), createdDate, updatedDate, 0, level, fileName, longDescription, 2, author);
                courseDao.addCourse(c);
                msg = "Add Course Successfully";
                request.setAttribute("c", c);
                request.setAttribute("nameAdded", name);
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("updatecourse").forward(request, response);
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
