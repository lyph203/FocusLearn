<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="vnpay.Config"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="dao.PaymentDAO"%>
<%@page import="model.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@page import="dao.CourseDAO"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <title>VNPAY RESPONSE</title>
        <!-- Bootstrap core CSS -->
        <link href="assets/bootstrap.min.css" rel="stylesheet"/>
        <!-- Custom styles for this template -->
        <link href="assets/jumbotron-narrow.css" rel="stylesheet"> 
        <script src="assets/jquery-1.11.3.min.js"></script>
        <link rel="icon" href="images/logo.png">
        <style>
            .title {
                font-weight: bold
            }
            .info {
                padding-left: 20px;
            }

            table {
                margin: auto
            }
        </style>
    </head>
    <body>
        <%
            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);

        %>
        <!--Begin display -->
        <div class="container">
            <div class="header clearfix">
                <h3 class="text-muted">VNPAY RESPONSE</h3>
            </div>
            <div class="table-responsive">
                <table class="table-responsive">
                    <tr>
                        <td class="title">Merchant Transaction Code:</td>
                        <td class="info"><%=request.getParameter("vnp_TxnRef")%></td>
                    </tr> 
                    <tr>
                        <td class="title">Amount:</td>
                        <td class="info"><%=String.format("%,.0f",Double.parseDouble(request.getParameter("vnp_Amount"))/100)%></td>
                    </tr>  
                    <tr>
                        <td class="title">Order info:</td>
                        <td class="info"><%=request.getParameter("vnp_OrderInfo")%></td>
                    </tr> 
                    <tr>
                        <td class="title">VNPAY Response Code:</td>
                        <td class="info"><%=request.getParameter("vnp_ResponseCode")%></td>
                    </tr> 
                    <tr>
                        <td class="title">VNPAY Transaction Code:</td>
                        <td class="info"><%=request.getParameter("vnp_TransactionNo")%></td>
                    </tr> 
                    <tr>
                        <td class="title">Bank Code:</td>
                        <td class="info"><%=request.getParameter("vnp_BankCode")%></td>
                    </tr> 
                    <tr>
                        <td class="title">Pay Date:</td>
                        <td class="info"><%=request.getParameter("vnp_PayDate")%></td>
                    </tr> 
                    <tr>
                        <td class="title">Payment Status:</td>
                        <td class="info">
                            <%
                                    User user = (User) request.getSession().getAttribute("user");
                                    String amount; 
                                    String info = request.getParameter("vnp_OrderInfo");
                                    if(info.contains("Enroll")) {
                                        amount = "-" + request.getParameter("vnp_Amount"); 
                                    } else amount = request.getParameter("vnp_Amount");
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                                    String payDate = formatter.format(cld.getTime());
                                    if (signValue.equals(vnp_SecureHash)) {
                                        if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                                            out.print("Success");
                                            String secureHash = (String)request.getParameter("vnp_TxnRef");
                                            new PaymentDAO().updatePayment(1, secureHash);
                                            if(!info.contains("Enroll")) {
                                                new PaymentDAO().updateUserWallet(user, "-" + amount);
                                            } else {
                                                User author = (User) request.getSession().getAttribute("author");
                                                String courseid = (String) request.getSession().getAttribute("courseid");
                                                new CourseDAO().enrollCouse(courseid,user.getUsername(),payDate);
                                                new PaymentDAO().updateUserWallet(author, String.valueOf(Double.parseDouble(amount)*90/100));
                                                new PaymentDAO().addPayment(author.getUsername(), String.valueOf(Double.parseDouble(amount.replace("-", ""))*90/10000), payDate, "Tution fee", "1", "From: " + secureHash, courseid);
                                                request.getSession().removeAttribute("author");
                                                request.getSession().removeAttribute("courseid");
                                                request.getSession().removeAttribute("vnp_SecureHash");
                                            }
                                            
                                        } else {
                                            out.print("Failed");
                                            new PaymentDAO().updatePayment(0, vnp_SecureHash);
                                        }

                                    } else {
                                        new PaymentDAO().updatePayment(0, vnp_SecureHash);
                                        out.print("invalid signature");
                                    }
                            %>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <a style="margin-top: 20px" href="home" type="button" class="btn btn-lg btn-default md-btn-flat mt-2 mr-3">Back to shopping</a>
                        </td>
                    </tr>
            </div> 
        </table>

    </div>

    <p>
        &nbsp;
    </p>
    <footer class="footer">
        <p>&copy; VNPAY 2020</p>
    </footer>
</div>  
</body>
</html>
