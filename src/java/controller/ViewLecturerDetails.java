/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import model.Course;
import model.User;

/**
 *
 * @author Pham Huong Ly
 */
@WebServlet(name="ViewLecturerDetails", urlPatterns={"/viewlecturerdetail"})
public class ViewLecturerDetails extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        CourseDAO courseDAO = new CourseDAO();
        
        //send lecturer info
        String username =  req.getParameter("username");
        User lecturer = userDAO.getUserByUsername(username);
        req.setAttribute("lecturer", lecturer);
        
        //get information of lecturer
        int totalcourses = userDAO.getTotalCourseOfLectureByUserName(username);
        int totalratings = userDAO.getTotalReviewOfLectureByUserName(username);
        
        //send list course belong to lecturer
        LinkedHashMap<Course, LinkedHashMap<Double, Integer>> listLecturerCourse = courseDAO.getListLecturerCourse(username);
        req.setAttribute("listLecturerCourse", listLecturerCourse);
        req.setAttribute("totalcourses", totalcourses);
        req.setAttribute("totalratings", totalratings);
        req.getRequestDispatcher("viewLecturerDetails.jsp").forward(req, resp);
    }
    
    
}
