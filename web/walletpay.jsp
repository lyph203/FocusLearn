
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dao.PaymentDAO"%>
<%@page import="model.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@page import="dao.UserDAO"%>
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
        <title>WALLET PAY RESPONSE</title>
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
        <!--Begin display -->
        <div class="container">
            <div class="header clearfix">
                <h3 class="text-muted">WALLET PAY RESPONSE</h3>
            </div>
            <div class="table-responsive">
                <table class="table-responsive">
                    
                    <tr>
                        <td class="title">Amount:</td>
                        <td class="info">${String.format("%,.0f",Double.parseDouble(amount))}</td>
                    </tr>  
                    <tr>
                        <td class="title">Customer:</td>
                        <td class="info">${sessionScope.user.getUsername()}</td>
                    </tr>
                    <tr>
                        <td class="title">Order info:</td>
                        <td class="info">${info}</td>
                    </tr>
                    <tr>
                        <td class="title">Pay Date:</td>
                        <td class="info">${paydate}</td>
                    </tr> 
                    <tr>
                        <td class="title">Payment Status:</td>
                        <td class="info">
                            Success
                            <%
                                    User user = (User) request.getSession().getAttribute("user");
                                    String amount = request.getParameter("amount").replace(",", "");
                                    String info = (String) request.getAttribute("info");
                                    String courseid = (String) request.getAttribute("courseid");
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                                    String payDate = formatter.format(cld.getTime());
                                    new PaymentDAO().addPayment(user.getUsername(), "-" +amount, payDate, info, "1", "From wallet", courseid);
                                    new PaymentDAO().updateUserWallet(user, amount + "00");
                                    new CourseDAO().enrollCouse(courseid,user.getUsername(), payDate);
                                    String authorUsername = (String) request.getAttribute("author");
                                    new PaymentDAO().updateUserWallet(authorUsername, amount);
                                    new PaymentDAO().addPayment(authorUsername, String.valueOf(Double.parseDouble(amount)*90/100), payDate, "Tution fee", "1", "From wallet", courseid);
                                    
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
