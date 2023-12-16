<%-- 
    Document   : questiondetail
    Created on : Oct 22, 2023, 10:36:44 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Question Detail</title>
        <link rel="icon" href="images/logo.png">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="assets//bootstrap.min.css">
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/questiondetail.css">
        <style>
            .img-course {
                max-width: 70px;
                object-fit: contain
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
                        <div class="col-lg-2 dropdown-menu"style="left: 82%">
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
        <!-- Content page -->
        <section class="bg0 p-t-52 p-b-20" style="padding-top: 100px; padding-bottom: 50px;">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 col-lg-9 p-b-80">
                        <div class="p-r-45 p-r-0-lg">
                            <!--  -->
                            <div  class="wrap-pic-w how-pos5-parent">
                                <img id="ques-img" src="images/question.jpg" alt="IMG-BLOG">

                            </div>

                            <div class="p-t-32" style="color: #333; margin-top: 20px;" >
                                <span class="flex-w flex-m stext-111 cl2 p-b-19">
                                    <span>
                                        <span class="cl4">By</span> ${question.getUser().getFullName()}  
                                        <span class="cl12 m-l-4 m-r-6">|</span>
                                    </span>

                                    <span>
                                        Cre: ${question.getCreatedDate()}
                                        <span class="cl12 m-l-4 m-r-6">|</span>
                                    </span>

                                    <span>
                                        Update: ${question.getUpdateDate()}
                                        <span class="cl12 m-l-4 m-r-6">|</span>
                                    </span>

                                    <span>
                                        ${totalAnswer} answers
                                    </span>
                                </span>

                                <h4 class="ltext-109 cl2 p-b-28" style="margin: 20px 0;">
                                    ${question.getTitle()}
                                </h4>

                                <c:forEach items="${question.getContent().split('<br>')}" var="para">
                                    <p class="stext-117 cl6 p-b-26">
                                        ${para}
                                    </p>
                                </c:forEach>


                            </div>

                            <div class="flex-w flex-t p-t-16">
                                <span class="size-216 stext-116 cl8 p-t-4" style="margin-right: 10px; margin-top: 7px">
                                    Tags:
                                </span>

                                <div class="flex-w size-217">
                                    <c:forEach items="${question.getListTag()}" var="tag">
                                        <a href="viewquestion?tagsID=${tag.getTagId()}" class="flex-c-m stext-107 cl6 size-301 bor7 p-lr-15 hov-tag1 trans-04 m-r-5 m-b-5">
                                            ${tag.getTagName()}
                                        </a>
                                    </c:forEach>

                                </div>
                            </div>
                            <div class="p-t-40 " style="margin-top: 30px;">
                                <c:forEach items="${listAnswer}" var="answer">
                                    <div class="flex-r-m justify-content-between" style="margin: 20px 0">
                                        <img class="rounded-circle" style="width:60px; height: 60px; object-fit: cover;"  src="${answer.getUser().getImg()}" alt="IMG-BLOG">
                                        <div class="flex-col-c justify-content-center col-lg-11" style="border: 1px solid; border-radius: 20px; padding-top: 10px; margin-left: 20px" >
                                            <p style="margin: 0;"><strong>${answer.getUser().getFullName()}</strong></p>
                                            <p style="margin: 0;">${answer.getContent()}</p>
                                            <p style="margin: 0; font-size: 10px; text-align: right;">${answer.getCreateDate()}</p>
                                        </div>
                                    </div>
                                </c:forEach>

                                <c:if test="${listAnswer.size() ne 0}">
                                    <form action="questiondetail">
                                        <div class="flex-row justify-content-center" style="margin-top: 20px;">
                                            <input type="hidden" name="id" value="${question.getQuestionId()}">
                                            <ul class="flex-m justify-content-center" style="padding: 0; margin: auto;">
                                                <c:if test="${index ne 1}">
                                                    <li style="margin-right: 5px;"><a href="questiondetail?id=${question.getQuestionId()}&index=${index - 1}">Previous</a></li>
                                                    </c:if>
                                                <li style="margin-right: 5px;"><input class="text-center" type="text" value="${index}" style="width:20px;" name="index" oninput="limit(this)" onchange="this.form.submit()" ></li>
                                                <li style="margin-right: 5px;">/</li>
                                                <li style="margin-right: 5px;"><input class="text-center" type="text" value="${String.format("%.0f", endPage)}" readonly style="width:20px; background-color: rgb(232, 230, 230); border-color: rgb(232, 230, 230);"></li>

                                                <c:if test="${index ne endPage}">
                                                    <li style="margin-right: 5px;"><a href="questiondetail?id=${question.getQuestionId()}&index=${index + 1}">Next</a></li>
                                                    </c:if>
                                            </ul>
                                        </div>
                                    </form>
                                </c:if>
                                <c:if test="${listAnswer.size() eq 0}">
                                    <div class="flex-row justify-content-center" style="margin-top: 20px;">
                                        <p class="text-center">No Comments</p>
                                    </div>
                                </c:if>

                            </div>
                            <!--  -->

                            <c:if test="${sessionScope.user ne null && question.status == 1}">
                                <div class="p-t-40" style="margin-top: 30px;">
                                    <h5 class="mtext-113 cl2 p-b-12">
                                        Leave a Comment
                                    </h5>

                                    <p class="stext-107 cl6 p-b-40">
                                        Your email address will not be published. Required fields are marked *
                                    </p>

                                    <form action="answer">
                                        <div class="bor19 m-b-20">
                                            <textarea class="stext-111 cl2 plh3 size-124 p-lr-18 p-tb-15" name="answer" placeholder="Comment..."></textarea>
                                        </div>
                                        <input type="hidden" name="id" value="${question.getQuestionId()}">
                                        <input id="post-comment-btn" type="submit" class="flex-c-m stext-101 cl0 size-125 bg3 bor2 hov-btn3 p-lr-15 trans-04" value="Post Comment">
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${question.status == 0}">
                                <h2>
                                    This question is closed.
                                </h2>
                            </c:if>
                        </div>
                    </div>

                    <div class="col-md-4 col-lg-3 p-b-80">
                        <div class="side-menu">

                            <div class="p-t-65">
                                <h4 class="mtext-112 cl2 p-b-33">
                                    Featured Courses
                                </h4>

                                <ul>
                                    <c:forEach items="${listRelatedCourse}" var="course">
                                        <li class="flex-w flex-t p-b-30">
                                            <a href="coursedetail?id=${course.getCourseId()}" class="wrao-pic-w size-214 hov-ovelay1 m-r-20">
                                                <img class="img-course"  src="${course.getImage()}" alt="PRODUCT">
                                            </a>

                                            <div class="size-215 flex-col-t p-t-8">
                                                <a href="coursedetail?id=${course.getCourseId()}" class="stext-116 cl8 hov-cl1 trans-04">
                                                    ${course.getCourseName()}
                                                </a>
                                                <span class="stext-116 cl6 p-t-20 old">
                                                    ${String.format("%,.0f",course.getPrice())} vnd
                                                </span>
                                                <span class="stext-116 cl6 p-t-20 new">
                                                    ${String.format("%,.0f", course.getPrice() * (100 - course.getDiscount())/100)} vnd
                                                </span>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>


                            <div class="p-t-50">
                                <h4 class="mtext-112 cl2 p-b-27">
                                    Tags
                                </h4>

                                <div class="flex-w m-r--5 contain-tags">
                                    <c:forEach items="${listAvailableTag}" var="tag">
                                        <a href="viewquestion?tagsID=${tag.getTagId()}" class="flex-c-m stext-107 cl6 size-301 bor7 p-lr-15 hov-tag1 trans-04 m-r-5 m-b-5">
                                            ${tag.getTagName()}
                                        </a>
                                    </c:forEach>


                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
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
            function limit(input) {
                if (input.value > ${endPage}) {
                    input.value = ${endPage};
                } else if (input.value < 1 || input.value === '') {
                    input.value = 1;
                }
            }
        </script>
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
