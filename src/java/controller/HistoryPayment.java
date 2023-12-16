/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.PaymentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import model.Payment;
import model.User;

/**
 *
 * @author nhatm
 */
@WebServlet(name = "HistoryPayment", urlPatterns = {"/historypayment"})
public class HistoryPayment extends HttpServlet {

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
            out.println("<title>Servlet HistoryPayment</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HistoryPayment at " + request.getContextPath() + "</h1>");
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

        User user = (User) request.getSession().getAttribute("user");
        PaymentDAO paymentDAO = new PaymentDAO();

        if (user != null) {

            //Get param
            String indexPage = request.getParameter("index");
            //Check for null value of indexPage
            if (indexPage == null) {
                indexPage = 1 + "";
            }
            int index = Integer.parseInt(indexPage);
            String username = user.getUsername();
            //Define total courses
            int totalPayment = paymentDAO.getTotalPaymentHistorybyUsername(username);
            //Define number of courses display per page
            int paymentPerPage = 10;
            //Find the index of last page
            int endPage = totalPayment / paymentPerPage;
            //Increase 1 page
            if (totalPayment % paymentPerPage != 0) {
                endPage++;
            }
            //Check if index user enterred over total pages
            if (index > endPage || index == 0) {
                String errorMsg = "Can not found the page you want to view.";
                request.setAttribute("errorMsg", errorMsg);
            } else {
                ArrayList<Payment> listPayment = paymentDAO.getListPaymentforPaging(username, index, paymentPerPage);
                request.setAttribute("listPayment", listPayment);

            }
            
            //Set data attribute
            request.setAttribute("endPage", endPage);
            request.setAttribute("tag", index);
            request.setAttribute("totalPayment", totalPayment);
            
            request.getRequestDispatcher("historyPayment.jsp").forward(request, response);
        }

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
        processRequest(request, response);
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
