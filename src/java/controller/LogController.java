/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import util.PasswordUtil;
import util.Validation;

/**
 *
 * @author Admin
 */
public class LogController extends HttpServlet {

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
            out.println("<title>Servlet LogController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LogController at " + request.getContextPath() + "</h1>");
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
        request.getSession().invalidate();
        response.sendRedirect("home");
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
        //Get parameter from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        //get hashed password
        String hashedPassword = (new PasswordUtil()).hashPasswordMD5(password);
        
        //Array list store cookies
        ArrayList<Cookie> listCookie = new ArrayList<>();

        //Create cookie for account and remember option
        Cookie usernameCookie = new Cookie("username", username);
        Cookie passwordCookie = new Cookie("password", password);
        Cookie rememberCookie = new Cookie("remember", remember);

        //Add cookie to list
        listCookie.add(usernameCookie);
        listCookie.add(passwordCookie);
        listCookie.add(rememberCookie);

        UserDAO userDAO = new UserDAO();

        //Get session
        HttpSession session = request.getSession();
        new Validation().checkExistedGeneratedPass(request, response);
        String generatedPass = (String) session.getAttribute("generatedPass");
        
        //If temp password exist in session then login by temp password, else login by normal
        if (generatedPass != null) {
            //Check if password equals temp password
            if (password.equals(generatedPass)) {
                User user = userDAO.getUserByUsername(username);
                //check if account is active --> status = 1 
                if (user.getStatus() != 0) {
                    //store user object in the session
                    request.getSession().setAttribute("user", user);
                    
                    //check remember option
                    if (remember != null) {
                        setCookieTimeOut(listCookie, response, 60 * 60 * 24);
                    } else {
                        setCookieTimeOut(listCookie, response, 0);
                    }

                    //direct role admin
                    if (user.getRole() == 0) {
                        response.sendRedirect("addashboard");
                        return;
                    }
                    
                    if (user.getRole() == 1) {
                        response.sendRedirect("cmhome");
                        return;
                    }
                    
                    if (user.getRole() == 2) {
                        response.sendRedirect("lecturerhome");
                        return;
                    }
                }else {
                    request.setAttribute("username", username);
                    request.setAttribute("password", password);
                    request.setAttribute("remember", remember);
                    eraseCookie(request, response);
                    request.setAttribute("err", "The account is banned!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }else {
                request.setAttribute("username", username);
                request.setAttribute("password", password);
                request.setAttribute("remember", remember);
                eraseCookie(request, response);
                request.setAttribute("err", "Username or password is wrong!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        } else {
            //check existed account in database
            if (userDAO.checkExistedUserWithUsernameAndPassword(username, hashedPassword)) {
                //get user in database
                User user = userDAO.getUserByUsername(username);
                //check if account is active --> status = 1 
                if (user.getStatus() != 0) {
                    //store user object in the session
                    request.getSession().setAttribute("user", user);

                    //check remember option
                    if (remember != null) {
                        setCookieTimeOut(listCookie, response, 60 * 60 * 24);
                    } else {
                        setCookieTimeOut(listCookie, response, 0);
                    }

                    //direct role admin
                    if (user.getRole() == 0) {
                        response.sendRedirect("addashboard");
                        return;
                    }
                          
                    if (user.getRole() == 1) {
                        response.sendRedirect("cmhome");
                        return;
                    }   
                    
                    if (user.getRole() == 2) {
                        response.sendRedirect("lecturerhome");
                        return;
                    }
                } else {
                    request.setAttribute("username", username);
                    request.setAttribute("password", password);
                    request.setAttribute("remember", remember);
                    eraseCookie(request, response);
                    request.setAttribute("err", "The account is banned!");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("username", username);
                request.setAttribute("password", password);
                request.setAttribute("remember", remember);
                request.setAttribute("err", "Username or password is wrong");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        }        
        
        request.getRequestDispatcher("home").forward(request, response);
    }

    private void setCookieTimeOut(ArrayList<Cookie> listCookie, HttpServletResponse response, int timeExist) {
        for (Cookie cookie : listCookie) {
            cookie.setMaxAge(timeExist);
            response.addCookie(cookie);
        }
    }

    private void eraseCookie(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
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
