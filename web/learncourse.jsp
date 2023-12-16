<%-- 
    Document   : learncourse
    Created on : Oct 22, 2023, 11:09:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Learning</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="assets/bootstrap.min.css">
        <link rel="icon" href="images/logo.png">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <style>
            .contain-section {
                border: 1px solid #777;
            }

            .lesson {
                border: 1px solid #777;
                padding: 10px;
            }

            .lesson a {
                color: black;
            }

            .col-lg-3 {
                padding: 0;
            }

            a:hover {
                text-decoration: none;
                color: #888;
            }

            .active {
                background-color: #888;
            }
        </style>
    </head>

    <body>
        <div class="contain-navigation" style="position: relative;">
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo">
                </div>
                <ul>
                    <li><a href="home" class="link">Home</a></li>
                    <li><a href="viewCourse" class="link">Course</a></li>
                    <li><a href="viewquestion" class="link">Community</a></li>
                    <li><a href="#" class="link">About</a></li>
                </ul>
                <div class="search-bar">
                    <input id="searchInput" type="text" placeholder="Search">
                    <i id="search" class='bx bx-search' onclick="searchCourse()"></i>
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
                        <div class="dropdown-menu col-lg-2" style="left: 82%">
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

        <div style="padding-top: 30px; padding-left: 60px; font-size: 40px; font-weight: bold;">
            ${course.getCourseName()}
        </div>

        <div class="main-content d-flex justify-content-between" style=" height: 100vh;">

            <div class="contain-video col-lg-9">
                <iframe id="video" width="992" height="558" src="${section.getVideo()}"
                        title="${section.getTitle()}" frameborder="0"
                        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                        allowfullscreen></iframe>

                <script type="text/javascript">
                    var tag = document.createElement('script');
                    tag.id = 'iframe-demo';
                    tag.src = 'https://www.youtube.com/iframe_api';
                    var firstScriptTag = document.getElementsByTagName('script')[0];
                    firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('video', {
                            events: {
                                'onStateChange': onPlayerStateChange
                            }
                        });
                    }
                    function changeStatusProgress(playerStatus) {
                        if (playerStatus === 0) {
                            fetch(`./complete?sectionId=${section.getSectionId()}`);
                            let linkLesson = document.getElementById('lesson${section.getSectionId()}');
                            if (!linkLesson.textContent.includes('✔')) {
                                linkLesson.textContent += '✔';
                            }

                        }
                    }
                    function onPlayerStateChange(event) {
                        changeStatusProgress(event.data);
                    }
                </script>
                <h2 style="font-weight: bold; margin-top: 50px">Description</h2>
                <p>${section.getDescription()}</p>
            </div>

            <div class="col-lg-3 " style="height: fit-content;">
                <h2>Table of Contents</h2>
                <ul style="padding: 0; margin: 0; " class="contain-section">
                    <c:forEach items="${course.getListSection()}" var="sec">
                        <li class="lesson ${sec.getSectionId() eq sectionId ? 'active' : ''}">
                            <a id="lesson${sec.getSectionId()}" href="learn?id=${course.getCourseId()}&sectionId=${sec.getSectionId()}">
                                ${sec.getTitle()} ${sec.getStatus() eq 1 ? '✔' : ''}
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

            <div style="clear: both"></div>
        <div class="footer" style="margin-top: 100px;">
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
            function searchCourse() {
                window.location.href = './viewCourse?searchContent=' + document.getElementById('searchInput').value;
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
