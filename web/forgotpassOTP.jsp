<%-- 
    Document   : forgotPassword
    Created on : Oct 2, 2023, 1:38:43 PM
    Author     : nhatm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <title>Forgot Password | FocusLearn</title>
        <link rel="stylesheet" href="css/stylelog.css"/>
    </head>
    <body>
        <div class="wrapper">
            <nav class="nav">
                <div class="nav-logo">
                    <p>LOGO</p>
                </div>
                <div class="nav-menu" id="navMenu">
                    <ul>
                        <li><a href="home" class="link active">Home</a></li>
                        <li><a href="viewCourse" class="link">Course</a></li>
                        <li><a href="viewquestion" class="link">Community</a></li>
                        <li><a href="#" class="link">About</a></li>
                    </ul>
                </div>
                <div class="nav-button">
                    <button class="btn white-button" id="loginBtn" onclick="navigateURL()">
                        Sign in
                    </button>
                    <button class="btn" id="registerBtn"  onclick="navigateURL()">
                        Sign up
                    </button>
                </div>
                <div class="nav-menu-btn">
                    <i id="navMenuBtn" class="bx bx-menu" onclick="myMenuFunction()"></i>
                </div>
            </nav>

            <!-------------------- Form Box -------------------->
            <div class="form-box">
                <!--------------------  Sucess send email -------------------->
                <div class="forgot-container" id="forgot">
                    <div class="top">
                        <header>Check your email</header>
                        <span>We've sent an email to your email</span>
                        <span>Please check your spam folder if you don't see the email in your box</span>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function myMenuFunction() {
                var i = document.getElementById('navMenu');
                var menuBtn = document.getElementById('navMenuBtn');
                if (i.className === 'nav-menu') {
                    i.className += ' responsive';
                    menuBtn.className = 'bx bx-x';
                } else {
                    i.className = 'nav-menu';
                    menuBtn.className = 'bx bx-menu';
                }
            }

            function navigateURL() {
                window.location.href = 'login.jsp';
            }
        </script>
    </body>
</html>
