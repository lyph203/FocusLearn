<%-- 
    Document   : coursedetail
    Created on : Oct 13, 2023, 10:18:05 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Course Details</title>
        <link rel="stylesheet" href="css/style.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="icon" href="images/logo.png">
        <link rel="stylesheet" href="css/coursedetail.css">

        <style>
            .course {
                display: flex;
                justify-content: center;
                align-items: center;
                border-bottom: 1px solid lightgrey;
                color: black;
                padding-top: 5px;
                margin-bottom: 10px;
                width: 80%;
                margin: auto;
            }
            .course:hover {
                box-shadow: 0 2px 4px rgba(0,0,0,.08), 0 4px 12px rgba(0,0,0,.08);
            }

            .course img {
                height: 100%;
            }

            .course div p {
                margin: 5px 0;
            }

            .main-content {
                padding-top: 100px
            }
            .old {
                text-decoration: line-through;
                font-weight: bold;
                color: #B2AFAF;
            }
            .new{
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="contain-navigation">
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo">
                </div>
                <ul>
                    <li><a href="home" class="link active">Home</a></li>
                    <li><a href="viewCourse" class="link">Course</a></li>
                    <li><a href="viewquestion" class="link">Community</a></li>
                    <li><a href="#" class="link">About</a></li>
                </ul>
                <div class="search-bar">   
                    <input type="text" placeholder="Search">
                    <i class='bx bx-search'></i>
                </div>

                <c:if test="${sessionScope.user == null}">
                    <div class="log-buttons">
                        <a href="login.jsp" class="btn-log">Log in</a>
                        <a href="login.jsp" class="btn-log">Registation</a>
                    </div>
                </c:if>

                <c:if test="${sessionScope.user != null}">
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
                                        <a href="profile">My account</a>
                                    </div>
                                    <div>
                                        <i class='bx bxs-extension' ></i>
                                        <a href="mycourses">My course</a>
                                    </div>

                                    <div>
                                        <i class='bx bx-history' ></i>
                                        <a href="historypayment">History payment</a>
                                    </div>
                                    <div style="padding-bottom: 15px;">
                                        <i class='bx bxs-wallet'></i>
                                        <a href="mywallet.jsp">My Wallet</a>
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
                </c:if>
            </nav>
        </div>

        <div class="main-content">
            <c:forEach items="${courseDetail}" var="courseEntry">
                <div class="course-content">
                    <div class="course-header">
                        <div class="course-title">
                            <c:set var="course" value="${courseEntry.key}" />
                            <h2 style="font-size: 35px;">
                                ${course.getCourseName()}
                            </h2>
                            <p style="font-size: 20px; width: 60%; margin-top: 20px;">
                                ${course.getDescription()}
                            </p>
                            <ul class="rating" style="margin-top: 20px; padding-left: 0">
                                <c:set var="ratingData" value="${courseEntry.value}" />
                                <c:forEach var="ratingEntry" items="${ratingData}">
                                    <c:set var="rating" value="${ratingEntry.key}"/>
                                    <span style="margin-right: 10px"><span style="margin-right: 20px">Rating: </span> ${String.format("%.2f", rating)}</span>
                                    <c:set var="roundedRating" value="${Math.floor(rating)}"/>
                                    <c:set var="hasHalfStar" value="${rating - roundedRating > 0}"/>
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= roundedRating}">
                                                <li><i class="bx bxs-star"></i></li>
                                                </c:when>
                                                <c:when test="${i == roundedRating + 1 && hasHalfStar}">
                                                <li><i class="bx bxs-star-half"></i></li>
                                                </c:when>
                                                <c:otherwise>
                                                <li><i class="bx bx-star"></i></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    <li style="margin-left: 20px">
                                        (${ratingEntry.value} rates)  
                                        <span style="margin-left: 10px">${studentsNumber} students</span>
                                    </li>
                                </c:forEach>

                            </ul>
                            <p style="margin-top: 20px;">
                                <span>
                                    Level : 
                                    <span style="background-color: rgb(24, 185, 24); color: #f4eeee; padding: 0.2rem;">
                                        ${course.getLevel()}
                                    </span>
                                </span>
                                <span style="margin-left: 20px;">
                                    Created by: <a style="color: white" href="viewlecturerdetail?username=${course.getAuthor().getUsername()}">${course.getAuthor().getFullName()}</a>
                                </span>
                            </p>
                            <p style="margin-top: 50px">Last updated: ${course.getUpdateDate()}</p>
                        </div>
                        <div class="course-img">
                            <img src="${course.getImage()}" alt="">
                        </div>
                    </div>
                </div>


                <div class="course-detail">
                    <div class="long-desctiption">
                        <h2>Description</h2>
                        <p style="text-align: justify">${course.getLongDescription()}</p>
                    </div>
                    <div class="course-enroll">
                        <div class="enroll">
                            <h2 style="font-size: 30px; margin-bottom: 40px; text-align: left;">
                                <span class="old">${String.format("%,.0f",course.getPrice())}</span><span style="margin-left: 8px; color: #B2AFAF;">vnd</span>
                                <span class="new" style="margin-right: 10px">${String.format("%,.0f", course.getPrice() * (100 - course.getDiscount())/100)}<span style="margin-left: 10px">vnd</span>
                            </h2>
                            <form action="enroll" method="post">
                                <input type="hidden" name="courseid" value="${course.getCourseId()}">
                                <input type="hidden" name="amount" value="${String.format("%.0f", course.getPrice() * (100 - course.getDiscount())/100)}">
                                <c:choose>
                                    <c:when test="${not empty isEnrolled && isEnrolled}">
                                        <input type="text" style="text-align: center;font-size: 20px; padding: 15px;color: white; background-color: #2d2f31;width: 100%; box-sizing: border-box; " readonly value="Enrolled">  
                                    </c:when>
                                    <c:otherwise>
                                        <input type="submit" value="Enroll Now">
                                    </c:otherwise>
                                </c:choose>

                            <div style="margin-top: 50px;">
                                <h3 style="margin-bottom: 20px; text-align: left;">Top companies using: </h3>
                                <div>
                                    <img style="margin-bottom: 20px;" src="https://s.udemycdn.com/partner-logos/v4/nasdaq-dark.svg" alt="">
                                    <img style="margin-bottom: 20px;" src="https://s.udemycdn.com/partner-logos/v4/netapp-dark.svg" alt="">
                                    <img style="margin-bottom: 20px;" src="https://s.udemycdn.com/partner-logos/v4/eventbrite-dark.svg" alt="">
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div style="clear: both;"></div>
                <div class="pro-review">
                    <ul>
                        <li>
                            <div class="comment">
                                <table>
                                    <tr>
                                        <td><i class='bx bxs-star' ></i></td>
                                        <td>${String.format("%.2f",lecturerRating)} Over Rating</td>    
                                    </tr>
                                    <tr>
                                        <td><i class='bx bx-line-chart-down'></i></td>
                                        <td>${totalReview} Reviews</td>
                                    </tr>
                                    <tr>
                                        <td><i class='bx bxs-user'></i></td>
                                        <td>${totalStudent} Students</td>
                                    </tr>
                                    <tr>
                                        <td><i class='bx bx-play-circle'></i></td>
                                        <td>${totalCourse} Courses</td>
                                    </tr>
                                </table>
                            </div>
                            <div class="pro-info">
                                <img src="${course.getAuthor().getImg()}" alt=""><br>
                                <a class="author" href="viewlecturerdetail?username=${course.getAuthor().getUsername()}">${course.getAuthor().getFullName()}</a>
                            </div>

                        </li>

                    </ul>
                </div>
            </c:forEach>

            <div class="suggest-course">
                <div style="margin: 2rem 0">
                    <h2>Student also bought</h2>
                </div>
                <div class="list-course" style="margin-bottom: 50px">
                    <c:forEach items="${listSuggested}" var="course">
                        <a href="coursedetail?id=${course.getKey().getCourseId()}" >
                            <div class="course">
                                <div style="width: 25%;">
                                    <img style="width: 150px;" src="${course.getKey().getImage()}"/>
                                </div>
                                <div style="width: 25%;"> 
                                    <h2 style="width: 90%;">${course.getKey().getCourseName()}</h2>

                                    <p style="background-color: rgb(24, 185, 24); color: #f4eeee; padding: 0.1rem; width: fit-content;">${course.getKey().getLevel()}</p>
                                </div>
                                <div style="width: 25%;">
                                    <p style="margin-right: 100px;"> ${String.format("%.2f",course.getValue())} <i class="bx bx-star"></i></p>
                                </div>
                                <div style="width: 25%;">
                                    <strong>  ${String.format("%,.0f", course.getKey().getPrice())} VND</strong>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="footer">
            <div class="contain-component">
                <div class="component">
                    <h2>Categories</h2>
                    <ul>
                        <li>Java</li>
                        <li>JavaScript</li>
                        <li>.Net</li>
                        <li>Business</li>
                    </ul>
                </div>
                <div class="component">
                    <h2>Help</h2>
                    <ul>
                        <li>Java</li>
                        <li>JavaScript</li>
                        <li>.Net</li>
                        <li>Business</li>
                    </ul>
                </div>
                <div class="component">
                    <h2>Get in touch</h2>
                    <p>
                        Any questions? Let us know in store at 8th floor, 379 Hudson St, New York, NY 10018 or call us on (+1) 96 716 6879
                    </p>
                    <div class="social">
                        <i class='bx bxl-facebook-circle'></i>
                        <i class='bx bxl-instagram' ></i>
                        <i class='bx bxl-pinterest' ></i>
                    </div>
                </div>
                <div class="component">
                    <h2>NEWSLETTER</h2>
                    <p>
                        ohnononono
                    </p>
                </div>
            </div>

            <div class="copy-right">
                © 2023 FocusLearn, Inc.
            </div>

        </div>

        <script>
            function check(containerDiv) {
                containerDiv.childNodes[1].click();
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
