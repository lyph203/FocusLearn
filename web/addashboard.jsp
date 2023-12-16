<%--
  Created by IntelliJ IDEA.
  User: Pham Huong Ly
  Date: 10/3/2023
  Time: 5:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/addashboard.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="icon" href="images/logo.png">
        <title>Administrator Dashboard</title>
    </head>
    <body>
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
                                <img src="images/pro.png" alt="">
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
        <!-- End of sidebar section -->

        <div class="main-content">
            <div class="analyse">
                <div class="sales">
                    <div class="status">
                        <h1>Total Income</h1>
                        <h3>${String.format("%,.0f", totalIncome)} VND</h3>
                    </div>
                </div>

                <div class="lecturer">
                    <div class="status">
                        <h1>Total Lecturers</h1>
                        <h3><c:out value="${totalLecturer}"/></h3>
                    </div>
                </div>

                <div class="student">
                    <div class="status">
                        <h1>Total Students</h1>
                        <h3><c:out value="${totalStudent}"/></h3>
                    </div>
                </div>
            </div>

            <div class="rank">
                <div class="lecturers">
                    <h1>Lecturers Rank</h1>
                    <table width="100%">
                        <tr style="height: 3rem;">
                            <td>Top</td>
                            <td>Name</td>
                            <td>Overall rating:</td>
                        </tr>
                        <c:set var="x" value="1"/>
                        <c:forEach items="${listTopLecturer}" var="user">
                            <tr style="height: 2rem;">
                                <td>${x}</td>
                                <c:set var="x" value="${x+1}"></c:set>
                                <td style="text-align: left;
                                    text-indent: 1.2rem;">${user.getKey().getUsername()}</td>
                                <td>${user.getValue()}</td>
                            </tr>    
                        </c:forEach>
                    </table>
                </div>

                <div class="courses">
                    <h1>Courses Rank</h1>

                    <table width="100%">
                        <tr style="height: 3rem;">
                            <td>Top</td>
                            <td>Name</td>
                            <td>Overall rating:</td>
                        </tr>
                        <c:set var="i" value="1"/>
                        <c:forEach items="${listTopCourse}" var="course">
                            <tr style="height: 2rem;">
                                <td>${i}</td>
                                <c:set var="i" value="${i+1}"></c:set>
                                <td style="text-align: left;
                                    text-indent: 1.2rem;">${course.getKey().getCourseName()}</td>
                                <td>${course.getValue()} </td>
                            </tr>    
                        </c:forEach>
                    </table>
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
        </script>
    </body>
</html>
