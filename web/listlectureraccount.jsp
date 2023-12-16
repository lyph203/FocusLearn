<%--
  Created by IntelliJ IDEA.
  User: Pham Huong Ly
  Date: 10/4/2023
  Time: 8:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/addashboard.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20,500,0,0" />
        <link rel="icon" href="images/logo.png">
        <title>Manage Lecturer Account</title>

        <style>
            .list-lecturer table .header td{
                font-weight: 600;
                font-size: 1.5rem;
            }
        </style>
    </head>
    <body>
        <!-------------------- Navbar -------------------->
        <div class="contain-navigation">
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo">
                </div>

                <div class="search-bar"> 
                    <form action="manageLecturer" method="post" id="search">
                        <input type="text" placeholder="Search" name="inputSearch" 
                               onkeyup="submitForm()" form="search"
                               style="width: 26rem;">
                        <i class='bx bx-search'></i>
                        <input type="hidden" value="1" name="search">
                    </form>
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

        <!-------------------- Side bar -------------------->                         
        <div class="sidebar">
            <a href="addashboard">
                <span><i class='bx bx-home-alt-2'></i></span>
                <h3>Dashboard</h3>
            </a>
            <a href="createlectureraccount">
                <span><i class='bx bx-message-square-add'></i></span>
                <h3>Create Lecturer Account</h3>
            </a>
            <a href="manageLecturer">
                <span><i class='bx bx-edit'></i></span>
                <h3>Manage Lecturer Account</h3>
            </a>
        </div>

        <!-------------------- Main content -------------------->
        <div class="list-lecturer">
            <h1 style="margin-top: 5rem;
                margin-left: 15%;
                font-size: 3rem;
                text-align: center;">List Lecturer</h1>

            <table style="width: 77%;
                   margin-left: 23%;
                   margin-top: 1rem;
                   border-spacing: 1rem;">
                <tr class="header">
                    <td width="10%">Avatar</td>
                    <td width="13%">Full Name</td>
                    <td width="10%">DOB</td>
                    <td width="30%">Email</td>
                    <td>Gender</td>
                    <td>Status</td>
                </tr>

                <!-- start list lecturer -->
                <c:forEach items="${listLecturer}" var="lecturer">
                    <tr style="height: 4rem;">
                        <td><c:if test="${lecturer.getImg() != 'none'}">
                                <img src="${lecturer.getImg()}" alt="alt" width="100%"/>
                            </c:if>
                        </td>
                        <td><p style="text-align: left;
                               margin-left: 0;">
                                <a style="color: black">${lecturer.getFullName()}</a>
                            </p> 
                        </td>
                        <td>${lecturer.getDob()}</td>
                        <td>${lecturer.getEmail()}</td>
                        <td>
                            <c:choose>
                                <c:when test="${lecturer.getGender() == 0}"> Male</c:when>
                                <c:when test="${lecturer.getGender() == 1}"> Female</c:when>
                            </c:choose>
                        </td>
                        <td width="10%">
                            <c:choose>
                                <c:when test="${lecturer.getStatus() == 1}"> 
                                    <a class="status btn1" href="manageLecturer?modeBan=1&name=${lecturer.getUsername()}">Active</a>
                                </c:when>
                                <c:when test="${lecturer.getStatus() == 0}"> 
                                    <a class="status btn2" href="manageLecturer?modeUnBan=1&name=${lecturer.getUsername()}">Banned</a>
                                </c:when>
                            </c:choose>
                        </td>
                        <td><a href="viewlecturerdetail?username=${lecturer.getUsername()}">
                                <span class="vision material-symbols-outlined" style="opacity: 60%;">
                                    visibility
                                </span>
                            </a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <script>
            // Check if errorMsg is not empty
            var successMsg = "${successMsg}";
            if (successMsg.trim() !== "") {
                window.location.href = "/FocusLearn/manageLecturer";
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
