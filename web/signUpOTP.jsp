<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <title>Register's OTP | FocusLearn</title>
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
                        <li><a href="historypayment" class="link">Community</a></li>
                        <li><a href="#" class="link">About</a></li>
                    </ul>
                </div>
                <div class="nav-button">
                    <button class="btn white-button" id="loginBtn" style="width:180%;">
                        Already have an account?
                    </button>
                </div>
                <div class="nav-menu-btn">
                    <i id="navMenuBtn" class="bx bx-menu" onclick="myMenuFunction()"></i>
                </div>
            </nav>

            <!-------------------- Form Box -------------------->
            <div class="form-box">
                <!-------------------- Register OTP -------------------->
                <div class="forgot-container" id="forgot">
                    <form action="confirmOTP" method="post" id="comfirmEmailForm">
                        <input type="hidden" name="genOTP" value="${generatedValue}">
                        <input type="hidden" name="fullname"  class="input-field" placeholder="Fullname" required value="${param.fullname}">
                        <div  style="display:none;" class="input-box">
                            <select class="condition" name="gender" required>
                                <option value="Male" ${param.gender == 'Male' ? 'selected' : ''}>Male</option>
                                <option value="Female" ${param.gender == 'Female' ? 'selected' : ''}>Female</option>
                            </select>
                        </div>
                        <input type="hidden" name="newusername" placeholder="Username" required value="${param.newusername}">
                        <input type="hidden" name="newpassword" placeholder="Password" required value="${param.newpassword}">
                        <input type="hidden" name="newemail"   placeholder="Email" required value="${param.newemail}" id="email">
                        <input type="hidden" name="dob" placeholder="Dob" required value="${param.dob}">
                        <div class="top">
                            <header>Enter received OTP</header>
                            <span>We sent an email for you that contains OTP with 6 digits</span>
                        </div>
                        <div class="input-box">
                            <input type="text" name="OTP" class="input-field" placeholder="6-digit OTP" value="">
                            <i class="bx bx-envelope"></i>
                        </div>
                        <div class="top" style="color: red">
                            <span>${otpErrorMsg}</span>
                        </div>
                        <div class="input-box forgot-btn">
                            <input type="submit" class="submit" value="Submit">
                        </div>
                    </form>
                </div>                        
            </div>
            <script>
                //button login
                var a = document.getElementById('loginBtn');
                //button register
                var b = document.getElementById('registerBtn');
                //form login
                var x = document.getElementById('login');
                //form register
                var y = document.getElementById('register');

                document.getElementById('loginBtn').addEventListener('click', function () {
                    window.location.href = 'login.jsp';
                });

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
            </script>

    </body>
</html>
