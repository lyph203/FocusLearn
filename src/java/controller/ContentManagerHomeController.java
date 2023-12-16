/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.Course;
import util.EmailUtil;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "ContentManagerHomeController", urlPatterns = {"/cmhome"})
public class ContentManagerHomeController extends HttpServlet {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

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
            out.println("<title>Servlet ContentManagerHomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ContentManagerHomeController at " + request.getContextPath() + "</h1>");
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
        CourseDAO courseDAO = new CourseDAO();
        String indexPage = request.getParameter("index");
        
        String searchContent = request.getParameter("search");
        
        //Check for null value of searchContent
        if(searchContent == null || searchContent.isEmpty()) {
            searchContent = "";
        }
        
        //Check for null value of indexPage
        if (indexPage == null) {
            indexPage = 1 + "";
        }
        int index = Integer.parseInt(indexPage);
        //Define total courses
        int totalCourse = courseDAO.countTotalCourse(searchContent);
        //Define total courses that is verified (both active and deactive)
        int verifiedCourse = courseDAO.countVerifiedCourse(searchContent) - courseDAO.countTotalPendingCourse(searchContent);
        //Define number of courses display per page
        int coursePerPage = 10;
        //Find the index of last page
        int endPage = verifiedCourse / coursePerPage;
        //Increase 1 page
        if (verifiedCourse % coursePerPage != 0) {
            endPage++;
        }
        //Check if index user enterred over total pages
        if (index > endPage || index == 0) {
            String errorMsg = "Can not found the page you want to view.";
            request.setAttribute("errorMsg", errorMsg);
        } else {
            //List course paged by index
            LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listCourse = courseDAO.getListPagingCourse(index, coursePerPage, 10, searchContent);
            LinkedHashMap<Course, Integer> listNumberOfUser = courseDAO.getNumberOfUserEachCourse();
            //Set data attribute
            request.setAttribute("endPage", endPage);
            request.setAttribute("tag", index);
            request.setAttribute("search", searchContent);
            request.setAttribute("totalCourse", totalCourse);
            request.setAttribute("listCourse", listCourse);
            request.setAttribute("listNumberOfUser", listNumberOfUser);
        }
        request.setAttribute("totalPendingCourse", courseDAO.countTotalPendingCourse(searchContent));
        request.getRequestDispatcher("contentmanagerhome.jsp").forward(request, response);
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
        CourseDAO courseDao = new CourseDAO();
        EmailUtil emailUtil = new EmailUtil();
        try {
            String courseID = request.getParameter("cid");
            Course course = courseDao.getCourseByID(Integer.parseInt(courseID));
            String lecturerMail = request.getParameter("lecturerEmail");
            String status = request.getParameter("status");
            //Get rating infomation
            String avgRate = request.getParameter("avgRate");
            String numberOfRates = request.getParameter("numberOfRates");
            //Get content of email
            String mailMSG = request.getParameter("message");
            //Check for status is ban or unban, 0:unban -> need to operate ban
            if (status.equals("Active")) {
                //Ban course
                courseDao.banCourse(courseID);
                //Send email
                String emailType = "bancourse";
                executorService.submit(() -> emailUtil.sendWarningEmail(lecturerMail, emailType, course, mailMSG, avgRate, numberOfRates));
                request.setAttribute("banMsg", "Ban Course Successful, warning email will be sent to lecturer.");
            } else {
                //Unan course
                courseDao.unbanCourse(courseID);
                //Send email
                String emailType = "unbancourse";
                executorService.submit(() -> emailUtil.sendWarningEmail(lecturerMail, emailType, course, mailMSG, avgRate, numberOfRates));
                request.setAttribute("banMsg", "Unban Course Successful, warning email will be sent to lecturer.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("banMsg", "Ban/Unban Course Failed, Check all the information again." + e.getMessage());
            System.out.println("BanCourseController: " + e.getMessage());
        }
        doGet(request, response);
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

    @Override
    public void destroy() {
        super.destroy();
        // Shutdown the thread pool when the servlet is destroyed
        executorService.shutdown();
    }
}
