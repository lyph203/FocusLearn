/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import util.EmailUtil;
import util.PasswordUtil;

/**
 *
 * @author nhatm
 */
@WebServlet(name="ForgotPassController", urlPatterns={"/forgot"})
public class ForgotPassController extends HttpServlet {
   private final ExecutorService executorService = Executors.newFixedThreadPool(5);
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
            out.println("<title>Servlet ForgotPassController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotPassController at " + request.getContextPath () + "</h1>");
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
        request.setAttribute("emailExisted", false);
        request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
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
        UserDAO userDAO = new UserDAO();
        EmailUtil emailUtil = new EmailUtil();
        PasswordUtil passUtil = new PasswordUtil();
        
        //Get session
        HttpSession session = request.getSession();
        
        //Get parameter
        String email = request.getParameter("email");
        
        //Check if email existed
        boolean emailExists = userDAO.checkExistedEmail(email);
        
        if (emailExists) {
            String emailType ="forgotpass";           
            //Generate Temp Password and store it in session
            String generatedPass = passUtil.generatePassword();
            session.setAttribute("generatedPass", generatedPass);
            //store generatedPassTimestamp in session for checking expired
            long currentTime = System.currentTimeMillis();
            session.setAttribute("generatedPassTimestamp", currentTime);
            
            //Send email
            executorService.submit(() -> emailUtil.sendEmail(email, emailType, generatedPass));
            //Set Attribute and redirect to page
            request.setAttribute("emailExisted", emailExists);
            request.getRequestDispatcher("forgotpassOTP.jsp").forward(request, response);
        } else {
            
            //Send err message back to forgotPassword,jsp
            request.setAttribute("err", "Email not found. Please enter again");
            request.setAttribute("emailExisted", email);
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
    }

    /** 
     * Returns a short description of the servlet.
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
