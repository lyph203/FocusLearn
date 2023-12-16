/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.CourseDAO;
import dao.PaymentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Course;
import model.Payment;
import model.User;
import vnpay.Config;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        //format datetime for vnpay in vietnam
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        //get courseid
        String courseId = request.getParameter("courseid");
        //get couse by id
        CourseDAO courseDAO = new CourseDAO();
        Course course = new Course();
        if (courseId != null && !courseId.isEmpty()) {
            course = courseDAO.getCourseByID(Integer.parseInt(courseId));
        }
        //get course name for order info
        String courseName = course.getCourseName();
        String bank_code = request.getParameter("bankCode");
        if (bank_code.equalsIgnoreCase("wallet")) {
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            request.setAttribute("info", "Enroll course: " + courseName);
            request.setAttribute("courseid", course.getCourseId() + "");
            request.setAttribute("author", course.getAuthor().getUsername());
            request.setAttribute("paydate", formatter2.format(cld.getTime()));
            request.setAttribute("amount", request.getParameter("amount").replaceAll(",", ""));
            request.getRequestDispatcher("walletpay.jsp").forward(request, respone);
            return;
        }
        request.getSession().setAttribute("author", course.getAuthor());
        request.getSession().setAttribute("courseid", course.getCourseId() + "");
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_OrderInfo;
        if (request.getParameter("info") != null) {
            vnp_OrderInfo = request.getParameter("info");
        } else {
            vnp_OrderInfo = "Enroll course: " + courseName;
        }
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;

        int amount = Integer.parseInt(request.getParameter("amount").replaceAll(",", "")) * 100;
        System.out.println(amount);
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        } else {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        com.google.gson.JsonObject job = new JsonObject();
        job.addProperty("code", "00");
        job.addProperty("message", "success");
        job.addProperty("data", paymentUrl);
        //Gson gson = new Gson();
        //respone.getWriter().write(gson.toJson(job));

        //Section : Add new payment
        //Get user payment
        User user = (User) request.getSession().getAttribute("user");
        //Get bill amount
        String billAmount = request.getParameter("amount").replaceAll(",", "");
        //Get bill info
        if (vnp_OrderInfo.contains("Enroll")) {
            billAmount = "-" + billAmount;
        }
        //Get bill Date
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String payDate = formatter2.format(cld.getTime());
        //Create bill
        new PaymentDAO().addPayment(user.getUsername(), billAmount, payDate, vnp_OrderInfo, "2", vnp_TxnRef, courseId);

        respone.sendRedirect(paymentUrl);
    }

}
