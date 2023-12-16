/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

/**
 *
 * @author Admin
 */
@WebServlet(name="EnrollCourseController", urlPatterns={"/enroll"})
public class EnrollCourseController extends HttpServlet {
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //get payment amount after discount
        String amount = request.getParameter("amount");
        //get user object from session
        User user = (User) request.getSession().getAttribute("user");
        //check wallet of user with amount
        String isGreater = user.getWallet() >= Double.parseDouble(amount) ? "true" : "false";

        //set data and send to payment function
        request.setAttribute("isGreater", isGreater);
        request.setAttribute("courseid", request.getParameter("courseid"));
        request.setAttribute("amount", String.format("%,.0f", Double.parseDouble(amount)));
        request.getRequestDispatcher("VNPAY.jsp").forward(request, response);
    }


}
