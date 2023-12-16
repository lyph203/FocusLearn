/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
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
public class ViewDashboardController extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //calculate total income
        CourseDAO courseDAO = new CourseDAO();
        double totalIncome = courseDAO.getTotalIncome();
        req.setAttribute("totalIncome", totalIncome);
        
        //count number of lecturer
        UserDAO userDAO = new UserDAO();
        int totalLecturer = userDAO.getTotalLecturer();
        req.setAttribute("totalLecturer", totalLecturer);
        
        //count number of student
        int totalStudent = userDAO.getTotalStudent();
        req.setAttribute("totalStudent", totalStudent);
        
        //top course rating
        LinkedHashMap<Course, Double> listTopCourse = courseDAO.getListTopCourse(5);
        req.setAttribute("listTopCourse", listTopCourse);
        
        //top lecturer rating
        LinkedHashMap<User, Double> listTopLecturer = userDAO.getListTopUser();
        req.setAttribute("listTopLecturer", listTopLecturer);
        
        req.getRequestDispatcher("addashboard.jsp").forward(req, resp);
    }
}
