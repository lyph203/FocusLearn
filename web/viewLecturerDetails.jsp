<%-- 
    Document   : viewLecturerDetail
    Created on : Oct 13, 2023, 10:58:28 PM
    Author     : Pham Huong Ly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${user.getFullName()} | FocusLearn</title>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/lecturerdetail.css">
        <link rel="icon" href="images/logo.png">
        <style>
            .old {
                text-decoration: line-through;
                font-weight: bold;
                color: #B2AFAF;
            }
            .new{
                font-weight: bold;
            }

            #search:hover {
                cursor: pointer
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
                    <input id="searchInput" type="text" placeholder="Search">
                    <i id="search" class='bx bx-search' onclick="searchCourse()"></i>
                </div>

                <script>
                    function searchCourse() {
                        window.location.href = './viewCourse?searchContent=' + document.getElementById('searchInput').value;
                    }
                </script>

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

        <!-------------------- Main content -------------------->                        
        <div style="margin-top: 6rem;" class="main-content">
            <!-------------------- Profile --------------------> 
            <div class="profile">   
                <div class="contain-info">
                    <h4>LECTURER</h4>
                    <h1>${lecturer.getFullName()}</h1>
                    <div class="statistics">
                        <table>
                            <tr>
                                <td><h3>Total of Courses</h3></td>
                                <td><h3>Total of Ratings</h3></td>
                            </tr>
                            <tr>
                                <td><h1>${totalcourses}</h1></td>
                                <td><h1>${totalratings}</h1></td>
                            </tr>
                        </table>
                        
                    </div>
                    <h3>Introduce for me</h3>
                    <p class="description">${lecturer.getDescription()}</p>
                </div>
                <div class="avatar">
                    <img style="width: 70%;" src="${lecturer.getImg()}" id="image-preview">
                    <h2 style="margin: 20px 0px;">${lecturer.getFullName()}</h2>
                    <p>${lecturer.getEmail()}</p>
                    <span style="color: red">${err}</span>
                </div>          
                <div style="clear: both"></div>
            </div>
                
            <!-------------------- List Course belong to Lecturer -------------------->     
            <div style="margin-left: 15%;
                 margin-top: 3%;" class="contain-courses">
                <h2>List Courses Of Lecturer</h2>
                <div class="list-course">
                    <ul>
                        <c:forEach items="${listLecturerCourse}" var="course">
                            <c:set var="courses" value="${course.key}" />
                            <c:set var="ratingData" value="${course.value}" />
                            <li style="margin: 10px 20px;
                                padding: 0px 15px;
                                width: 30%;">
                                <a href="coursedetail?id=${courses.getCourseId()}" class="course" style="color: black">
                                    <img src="${courses.getImage()}" alt="Course Image">
                                    <h2 style="text-align: left;">${courses.getCourseName()}</h2>
                                    <p style="text-align: left;
                                       margin-left: 0;">
                                        <a style="color: black" href="viewlecturerdetail?username=${courses.getAuthor().getUsername()}">${courses.getAuthor().getFullName()}</a>
                                    </p>    
                                    <div class="contain-stars" style="text-align: left;">
                                        <c:forEach var="ratingEntry" items="${ratingData}">
                                            <c:set var="rating" value="${ratingEntry.key}"/>
                                            <span>${String.format("%.2f", rating)}</span>
                                            <c:set var="roundedRating" value="${Math.floor(rating)}"/>
                                            <c:set var="hasHalfStar" value="${rating - roundedRating > 0}"/>
                                            <c:forEach begin="1" end="5" var="i">
                                                <c:choose>
                                                    <c:when test="${i <= roundedRating}">
                                                        <i class="bx bxs-star"></i>
                                                    </c:when>
                                                    <c:when test="${i == roundedRating + 1 && hasHalfStar}">
                                                        <i class="bx bxs-star-half"></i>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <i class="bx bx-star"></i>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                            <span>(${ratingEntry.value})</span>
                                        </c:forEach>
                                    </div>
                                    <div style="text-align: left;">
                                        <span class="new" style="margin-right: 10px">${String.format("%,.0f", courses.getPrice() * (100 - courses.getDiscount())/100)} vnd</span>
                                        <span class="old">${String.format("%,.0f",courses.getPrice())} vnd</span>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>    
        </div>

        <div class="footer" style="float: left;
             width: 100%;
             margin-top: 75px;">
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
            const dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];

            function display() {
                if (dropdownMenu.style.display !== 'block') {
                    dropdownMenu.style.display = 'block';
                } else
                    dropdownMenu.style.display = 'none';
            }

            function home() {
                window.location.href = './home';
            }
        </script>
    </body>
</html>
