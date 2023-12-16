/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import util.EmailUtil;
import util.PasswordUtil;
import util.Validation;

/**
 *
 * @author Pham Huong Ly
 */
@WebServlet(name = "CreateLecturerAccountController", urlPatterns = {"/createlectureraccount"})
public class CreateLecturerAccountController extends HttpServlet {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //redirect to page creation
        req.getRequestDispatcher("createLecturerAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        EmailUtil emailUtil = new EmailUtil();
        PasswordUtil passUtil = new PasswordUtil();

        //Get session
        HttpSession session = req.getSession();

        //Set the session's expiration time (15 mins)
        session.setMaxInactiveInterval(15 * 60);

        try {
            //get information of lecturer
            String username = req.getParameter("username");
            String fullname = req.getParameter("fullname");
            String email = req.getParameter("email");

            //Generate Temp Password and store it in session
            String password = passUtil.generatePassword();
            session.setAttribute("password", password);

            String dob = req.getParameter("dob");
            int gender = Integer.parseInt(req.getParameter("gender"));
            String description = req.getParameter("description");
            
            //validate data input for create lecturer account
            Validation validate = new Validation();
            String errorMsg = validate.validateCreateLecturerAccountInput(username, fullname, email);

            //create lecturer account
            if (errorMsg.equals("")) {
                if(userDAO.createLecturerAccount(username, fullname, email, password, dob, gender, description)){
                    //send password to lecturer's email
                    String emailType = "lecturerpass";

                    //Send email and redirect
                    executorService.submit(() -> emailUtil.sendEmail(email, emailType, password));
                    String successMsg = "Create Lecturer Account Successfully";
                    req.setAttribute("successMsg", successMsg);
                    req.getRequestDispatcher("listlectureraccount.jsp").forward(req, resp);
                }
            } else{
                req.setAttribute("username", username);
                req.setAttribute("fullname", fullname);
                req.setAttribute("email", email);
                req.setAttribute("errorMsg", errorMsg);
                req.getRequestDispatcher("createLecturerAccount.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            System.out.println("e: " + e.toString());
        }
    }
}
