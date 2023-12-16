<%-- 
    Document   : homepage
    Created on : Oct 1, 2023, 8:36:56 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>FocusLearnHome</title>
        <link rel="stylesheet" href="css/style.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
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

        <div class="contain-slider">
            <img src="images/banner.png" alt="banner" class="slide" style="width: 100%;">
            <img src="images/banner2.png" alt="banner" class="slide" style="width: 100%;">
            <button class="btn-prev" onclick="plusDivs(-1)">&#10094;</button>
            <button class="btn-next" onclick="plusDivs(+1)">&#10095;</button>
        </div>


        <div class="credit">
            <p>Trusted by over millions of learners around the world.</p>
            <p>Providing best material and document for learning in multiple platforms and fields.</p>
            <div class="contain-icons">
                <div>
                    <i class='bx bxl-java'></i>
                    <p>Java</p>
                </div>
                <div>
                    <i class='bx bxl-javascript'></i>
                    <p>Javascript</p>
                </div>
                <div>
                    <i class='bx bxl-python' ></i>
                    <p>Python</p>
                </div>
                <div>
                    <i class='bx bxl-spring-boot' ></i>
                    <p>Spring</p>
                </div>
                <div>
                    <i class='bx bxs-data' ></i>
                    <p>Database</p>
                </div>
                <div>
                    <i class='bx bxs-objects-vertical-bottom' ></i>
                    <p>Analysis</p>
                </div>
                <div>
                    <i class='bx bx-dollar' ></i>
                    <p>Business</p>
                </div>
                <div>
                    <i class='bx bx-shield-quarter' ></i>
                    <p>Security</p>
                </div>
            </div>
        </div>

        <div class="main-content">
            <div class="contain-courses">
                <h2 style="padding-left: 30px; font-size: 35px;">A broad selection of courses</h2>
                <p style="padding-left: 30px; font-size: 18px;">Choose from over 210,000 online video courses with new additions published every month</p>
                <div class="list-course">
                    <ul>
                        <c:forEach items="${listPreviewCourse}" var="courseEntry">
                            <c:set var="courses" value="${courseEntry.key}" />
                            <c:set var="ratingData" value="${courseEntry.value}" />
                            <li>
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
                                            <span>(${ratingEntry.value} rates)</span>
                                        </c:forEach>
                                    </div>
                                    <div style="text-align: left;">
                                        <span class="new" style="margin-right: 1px">${String.format("%,.0f", courses.getPrice() * (100 - courses.getDiscount())/100)} VND</span>
                                        <span class="old">${String.format("%,.0f",courses.getPrice())}VND</span>
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div style="clear: both"></div>
                <div class="show-more">
                    <a href="viewCourse">Show more</a>
                </div>
            </div>
        </div>
        <div class="pro-review">
            <ul>
                <li>
                    <div class="comment">
                        <p >
                            "With Focus Learn Business, employees were able to marry the two together, technology and consultant soft skills.
                            We're thankful that once they got in and took their key IT courses on AWS, Azure, Google Cloud, Big Data,
                            and DevOps that they efficiently moved over to Consulting courses to help drive their career forward."
                        </p>
                    </div>
                    <div class="pro-info">
                        <img src="images/pro.png" alt="">
                        <h2>Can Ngoc Huyen</h2>
                        <p>Lai thuong</p>
                    </div>
                </li>
            </ul>
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
            //initial slide
            var slideIndex = 1;
            //show slide when page is loaded
            showDivs(slideIndex);
            //change silde index then show
            function plusDivs(n) {
                showDivs(slideIndex += n);
            }

            function showDivs(n) {
                var i;
                //get all banner
                var x = document.getElementsByClassName("slide");
                //if index > total img --> return 1st image
                if (n > x.length) {
                    slideIndex = 1
                }
                //index < 1 --> return last slide
                if (n < 1) {
                    slideIndex = x.length
                }
                ;
                //display none for all slide
                for (i = 0; i < x.length; i++) {
                    x[i].style.display = "none";
                }
                //display slide at index choosen
                x[slideIndex - 1].style.display = "block";
            }

            const dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];

            function display() {
                if (dropdownMenu.style.display !== 'block') {
                    dropdownMenu.style.display = 'block';
                } else
                    dropdownMenu.style.display = 'none';
            }
        </script>

        <c:if test="${authenMsg ne null}">
            <script>
                alert('${authenMsg}');
            </script>
        </c:if>
    </body>
</html>
