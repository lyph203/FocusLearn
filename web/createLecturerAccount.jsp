<%-- 
    Document   : createLecturerAccount
    Created on : Oct 13, 2023, 10:49:08 AM
    Author     : Pham Huong Ly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/create.css">
        <title>Create Lecturer Account</title>
    </head>

    <body>
        <!-- start navigation -->
        <div class="contain-navigation">
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo">
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
                                    <i class='bx bxs-user'></i>
                                    <a href="userprofile.jsp">My account</a>
                                </div>
                            </li>
                            <li id="option2">
                                <div style="padding-top: 15px;">
                                    <i class='bx bx-log-out'></i>
                                    <a href="log">Log out</a>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
        <!-- end of navigation -->

        <!-- form create lecturer account -->
        <div class="container">
            <form action="createlectureraccount" method="POST">
                <h1 style="color: #0e4ead;
                           text-align: center;">Create New Lecturer Account</h1>
                <div class="row">
                    <h4>Account</h4>
                    <div class="input-group input-group-icon">
                        <div class="input-icon"><i class='bx bx-user'></i></div>
                        <input type="text" name="username" placeholder="Username" value="${username}" required>
                    </div>
                    <div class="input-group input-group-icon">
                        <div class="input-icon"><i class='bx bxs-user'></i></div>
                        <input type="text" name="fullname" placeholder="Fullname" value="${fullname}" required>
                    </div>
                    <div class="input-group input-group-icon">
                        <div class="input-icon"><i class='bx bx-envelope'></i></div>
                        <input type="text" name="email" placeholder="Email Address" value="${email}" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-half">
                        <h4>Date of Birth</h4>
                        <div class="input-group">
                            <input style="opacity: 60%;" type="date" name="dob" placeholder="dd/mm/yyyy" required><br>
                        </div>
                    </div>
                    <div class="col-half">
                        <h4>Gender</h4>
                        <div class="input-group">
                            <input id="gender-male" type="radio" name="gender" value="0" checked/>
                            <label for="gender-male">Male</label>
                            <input id="gender-female" type="radio" name="gender" value="1" />
                            <label for="gender-female">Female</label>
                        </div>
                    </div>
                </div>

                <div class="row" style="margin-bottom: 1em;">
                    <h4>Description</h4>
                    <textarea name="description" placeholder="Description"></textarea><br>
                </div>
                <input type="hidden" name="role" value="2">
                <input style="background-color: #0e4ead;
                       color: white;
                       font-weight: 600;" type="Submit" value="CREATE">
                <a class="cancel" href="manageLecturer">CANCEL</a>
            </form>
        </div>
        <!-- end of form -->
        
        <script>
            // Check if errorMsg is not empty
            var errorMsg = "${errorMsg}";
            if (errorMsg.trim() !== "") {
                window.alert(errorMsg);
            }
            
            const dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];

            function display() {
                if (dropdownMenu.style.display !== 'block') {
                    dropdownMenu.style.display = 'block';
                } else
                    dropdownMenu.style.display = 'none';
            }
        </script>
    </body>
</html>
