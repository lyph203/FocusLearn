/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

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
import model.User;

/**
 *
 * @author nhatm
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // Set the file size threshold
        maxFileSize = 1024 * 1024 * 5, // Set the maximum file size
        maxRequestSize = 1024 * 1024 * 10 // Set the maximum request size
)
public class ProfileController extends HttpServlet {

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
            out.println("<title>Servlet ProfileController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileController at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("userprofile.jsp").forward(request, response);
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

        //Get session
        HttpSession session = request.getSession();

        //Get Attribute
        String fullname = request.getParameter("fullname");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String description = request.getParameter("description");
        String email = request.getParameter("email");
        String imgUrl = request.getParameter("imgUrl");

        System.out.println(imgUrl);
        UserDAO userDAO = new UserDAO();

        //Check if user not enter dob
        if (!dob.isEmpty()) {
            userDAO.updateUserProfilebyEmail(fullname, dob, gender, description, email, imgUrl);

            // Retrieve the user object from the session
            User user = (User) session.getAttribute("user");

            //Update user object with new information
            if (user != null) { //Check if user not null
                user.setFullName(fullname);
                user.setDob(dob);
                user.setGender(Integer.parseInt(gender));
                user.setDescription(description);
                user.setImg(imgUrl);
            }

            //Update session with updated user
            session.setAttribute("user", user);

            //Call doGet() function to redirect to userprofile.jsp
            doGet(request, response);
        } else {
            //Redirect to userprofile with err message
            request.setAttribute("err", "You need to fill all the fields");
            request.getRequestDispatcher("userprofile.jsp").forward(request, response);
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
