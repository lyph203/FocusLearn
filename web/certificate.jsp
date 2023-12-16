<%-- 
    Document   : certificate
    Created on : Oct 27, 2023, 9:03:41 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Certificates</title>
        <link rel="stylesheet" href="css/style.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="icon" href="images/logo.png">
        <link rel="stylesheet" href="css/certificates.css">
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
                                        <i class='bx bxs-user'></i>
                                        <a href="profile">My account</a>
                                    </div>
                                    <div>
                                        <i class='bx bxs-extension'></i>
                                        <a href="mycourses">My course</a>
                                    </div>
                                    <div>
                                        <i class='bx bx-history'></i>
                                        <a href="historypayment">History payment</a>
                                    </div>
                                    <div style="padding-bottom: 15px;">
                                        <i class='bx bxs-wallet'></i>
                                        <a href="mywallet.jsp">My Wallet</a>
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
                </c:if>
            </nav>
        </div>

        <div class="main-content" style="padding-top: 150px; height: 100vh;">
            <h2 style="margin-left: 100px; font-size: 30px; margin-bottom: 50px;">
                Congratigation on finishing our course,
                here is your certificate</h2>
            <div class="contain-certificate">

                <div style="width: 45%; margin-right: 40px; height: fit-content; height: 100vh;">
                    <div class="user">
                        <img src="${sessionScope.user.getImg()}" alt="">
                        <div style="margin-left: 30px;">
                            <p>
                                <strong>
                                    Completed by ${sessionScope.user.getFullName()}
                                </strong>
                            </p>
                            <p>
                                <strong>Enroll Date:</strong> ${enrollDate}
                            </p>
                            <p>
                                <strong>Finished Date:</strong> ${finishDate}
                            </p>
                            <p style="max-width: 450px;">
                                ${sessionScope.user.getFullName()}'s account is verified. FocusLearn certifies their successful completion
                                of ${course.getCourseName()}
                            </p>
                        </div>
                    </div>

                    <div style="margin-top: 30px; ">
                        <div class="course">
                            <img src="${course.getImage()}" alt="">
                            <div>
                                <p style="max-width: 450px; font-weight: bold;">
                                    ${course.getCourseName()}
                                </p>
                                <a href="viewlecturerdetail?username=${course.getAuthor().getUsername()}">${courses.getAuthor().getFullName()}</a>
                                <ul style="margin: 0; padding: 0; display: flex;">
                                    <c:forEach items="${courseDetail}" var="courseEntry">
                                        <c:set var="ratingData" value="${courseEntry.value}" />
                                        <c:forEach var="ratingEntry" items="${ratingData}">
                                            <c:set var="rating" value="${ratingEntry.key}"/>
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
                                            <li>${String.format("%.2f", rating)} (${ratingEntry.value} ratings) | ${studentsNumber} students</li>
                                            </c:forEach>
                                        </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div style="padding:30px; border: 1px solid; margin-top: 30px;">
                            <h2 style="margin-bottom: 20px;">Description </h2>
                            <p>
                                ${course.getLongDescription()}
                            </p>
                        </div>
                    </div>
                </div>

                <div style="width: 40%; height: 100vh;">
                    <div id="loader" class="loader"></div>
                    <img id="ceritificate" src="${certificateImage eq '' ? 'images/blank-img.jpg' : certificateImage}" alt=""
                         style="width: 550px; aspect-ratio: 1.5/1; object-fit: cover; margin-bottom: 30px;">
                    <div style="display: flex; justify-content: center; align-items: center;">
                        <a id="download-btn" class="btn-download" href="#" download>Download certificate</a>
                    </div>

                </div>

            </div>

        </div>
        <div style="clear: both"></div>

        <div class="footer" style="margin-top: 250px;">
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
                        Any questions? Let us know in store at 8th floor, 379 Hudson St, New York, NY 10018 or call us on
                        (+1) 96 716 6879
                    </p>
                    <div class="social">
                        <i class='bx bxl-facebook-circle'></i>
                        <i class='bx bxl-instagram'></i>
                        <i class='bx bxl-pinterest'></i>
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
            const certificateImage = document.getElementById('ceritificate');
            const loader = document.getElementById('loader');
            const downloadBtn = document.getElementById('download-btn');
            const dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];
            downloadBtn.style.pointerEvents = "none";
            function display() {
                if (dropdownMenu.style.display !== 'block') {
                    dropdownMenu.style.display = 'block';
                } else
                    dropdownMenu.style.display = 'none';
            }
        </script>

        <c:if test="${certificateImage eq ''}">
            <script>

                let data = {
                    "template": "RnxGpW5lj8jqbEXrJ1",
                    "modifications": [
                        {
                            "name": "name",
                            "text": "${sessionScope.user.getFullName()}"
                        },
                        {
                            "name": "course",
                            "text": "${course.getCourseName()}"
                        }
                    ]
                };

                loader.style.display = 'flex';

                fetch('https://sync.api.bannerbear.com/v2/images', {
                    method: 'POST',
                    body: JSON.stringify(data),
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer bb_pr_db02338abb9b189ebef13742756569'
                    }
                }).then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                        .then(data => {
                            // Handle the response from Bannerbear, which may include image details
                            console.log('Image generated:', data);
                            console.log('ImageURL: ', data.image_url);
                            certificateImage.src = data.image_url;
                            fetch(`./addcertificate?url=` + data.uid + `courseId=${course.getCourseId()}`);
                            loader.style.display = 'none';
                            downloadBtn.href = 'data.image_url';
                            downloadBtn.style.pointerEvents = "auto";
                        })
                        .catch(error => {
                            console.error('Error generating image:', error);
                        });
            </script>
        </c:if>

        <c:if test="${certificateImage ne ''}">
            <script>

                fetch('https://api.bannerbear.com/v2/images/${certificateImage}', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer bb_pr_db02338abb9b189ebef13742756569'
                    }
                }).then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                        .then(data => {
                            // Handle the response from Bannerbear, which may include image details
                            console.log('Image generated:', data);
                            console.log('Uid', data.uid);
                            console.log('ImageURL: ', data.image_url);
                            certificateImage.src = data.image_url;
                            loader.style.display = 'none';
                            downloadBtn.href = data.image_url;
                            downloadBtn.style.pointerEvents = "auto";
                        })
                        .catch(error => {
                            console.error('Error generating image:', error);
                        });
            </script>
        </c:if>



    </body>

</html>
