<%-- 
    Document   : enrolledcourses
    Created on : Oct 12, 2023, 9:22:35 AM
    Author     : nhatm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My Course</title>
        <link rel="stylesheet" href="css/style.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

        <!-- Bootstrap CSS-->
<!--        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">-->

        <link rel="stylesheet" href="css/userprofile.css">
        <link rel="stylesheet" href="css/enrolledcourses.css"/>
        <link rel="icon" href="images/logo.png">        
    </head>
    <body>

        <div class="contain-navigation">

            <!-------------------- Navbar and sidebar -------------------->
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo">
                </div>
                <div class="search-bar">   
                    <input type="text" placeholder="search">
                    <i class='bx bx-search'></i>
                </div>
                <div class="account">
                    <img src="${sessionScope.user.getImg()}" alt="" onclick="display()">
                    <div class="dropdown-menu">
                        <ul>
                            <li id="info">
                                <img src="${sessionScope.user.getImg()}" alt="">
                                <div class="account-name">
                                    <strong>${sessionScope.user.getFullName()}</strong>
                                    <p>${sessionScope.user.getUsername()}</p>
                                </div>
                            </li>
                            <li id="option1">
                                <div>
                                    <i class='bx bxs-user' ></i>
                                    <a href="userprofile.jsp">My account</a>
                                </div>
                                <div>
                                    <i class='bx bxs-extension' ></i>
                                    <a href="#">My course</a>
                                </div>
                                <div style="padding-bottom: 15px;">
                                    <i class='bx bx-history' ></i>
                                    <a href="#">History payment</a>
                                </div>
                            </li>
                            <li id="option2">
                                <div style="padding-top: 15px;">
                                    <i class='bx bx-log-out' ></i>
                                    <a href="log">Log out</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>

        <!-------------------- Main content -------------------->                        
        <div class="main-content">

            <!-------------------- Side bar --------------------> 
            <div class="side-bar">
                <h2>Profile & Setting</h2>
                <div class="user-option">
                    <i class='bx bx-user-circle'></i>
                    <p>User profile</p>
                </div>
                <div class="user-option">
                    <i class='bx bxs-extension' ></i>
                    <p>My course</p>
                </div>
                <div class="user-option">
                    <i class='bx bx-history' ></i>
                    <p>History Payment</p>
                </div>
                <div class="user-option" onclick="home()">
                    <i class='bx bx-home' ></i>
                    <p>Home</p>
                </div>
            </div>

            <!-------------------- Enrolled Courses -------------------->
            <div class="contain-profile">
                <div class="my-course">
                    <div class="course-header">
                        <h2>My Courses</h2>
                        <span>This is your courses, kepp learning !</span>
                    </div>
                    <div class="course-list">
                        
                    </div>
                </div>
            </div>
        </div>
        <script>
            const dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];

            function display() {
                if (dropdownMenu.style.display !== 'block') {
                    dropdownMenu.style.display = 'block';
                } else
                    dropdownMenu.style.display = 'none';
            }

            const changePassForm = document.getElementById('changepass');
            const profileForm = document.getElementById('profile');

            if (document.getElementById('message').innerHTML.trim() !== '') {
                changePass();
            }

            function changePass() {
                changePassForm.style.display = 'block';
                profileForm.style.display = 'none';
            }

            function home() {
                window.location.href = './home';
            }
        </script>
        <!-- Bootstrap JS -->
<!--        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>-->
    </body>
</html>
