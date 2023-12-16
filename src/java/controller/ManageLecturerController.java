package controller;

import dao.CourseDAO;
import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import model.User;
import util.EmailUtil;

public class ManageLecturerController extends HttpServlet {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        CourseDAO courseDAO = new CourseDAO();
        EmailUtil emailUtil = new EmailUtil();
        ArrayList<User> listLecturer = userDAO.getListLecturer();
        req.setAttribute("listLecturer", listLecturer);
        
        //ban lecturer
        if(req.getParameter("modeBan")!=null){
            String username = req.getParameter("name");
            User u = userDAO.getUserByUsername(username);
            userDAO.banLecturer(username);
            courseDAO.banCourseByUserName(username);
            //Send email and redirect
            executorService.submit(() -> emailUtil.sendEmail(u.getEmail(), "Banned account", ""));
            String successMsg = "Ban Successfully";
            req.setAttribute("successMsg", successMsg);
        }
        
        //unban lecturer
        if(req.getParameter("modeUnBan")!=null){
            String username = req.getParameter("name");
            userDAO.unbanLecturer(username);
            User u = userDAO.getUserByUsername(username);
             //Send email and redirect
            executorService.submit(() -> emailUtil.sendEmail(u.getEmail(), "UnBanned account", ""));
            String successMsg = "UnBan Successfully";
            req.setAttribute("successMsg", successMsg);
        }
        
        req.getRequestDispatcher("listlectureraccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();

        //search lecturer
        if (req.getParameter("search") != null) {
            String inputSearch = req.getParameter("inputSearch");
            ArrayList<User> listLecturer = userDAO.searchListLecturer(inputSearch);
            req.setAttribute("listLecturer", listLecturer);

            req.getRequestDispatcher("listlectureraccount.jsp").forward(req, resp);
        }
    }
}
