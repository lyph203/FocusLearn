
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="css/contenthome.css">
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
        </style>
        <title>List Pending Course</title>
    </head>
    <body>
        <c:if test="${not empty msg}">
            <script>
                var message = "${msg}";
                alert(message);
            </script>
        </c:if>
        <div class="sidebar" id="mySidebar">
            <div class="side-header">
                <img src="images/pro.png" width="100" height="100" alt=""> 
                <h5 style="margin-top:10px;">Hello, ${sessionScope.user.getFullName()}</h5>
            </div>
            <hr style="border:1px solid; background-color:#3b9dc3; border-color:#3b9dc3;">
            <a style="padding-right: 30px;" href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
            <a href="cmhome" >Dashboard</a>
            <a href=""><i class="fa fa-book"></i> Pending Course</a>
        </div>

        <div class="contain-navigation" style="height: 120px">
            <nav class="navigation-bar">
                <div class="logo" id="main">
                    <button class="openbtn" onclick="openNav()"><img src="images/logo.png" alt="logo"/></button>
                </div>

                <div class="col-lg-3 account">
                    <img style="position: absolute; right: 0; top: -30px" src="${sessionScope.user.getImg()}" alt="" onclick="display()">
                    <div class="dropdown-menu" style="top: 90%">
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
        <div style="display: flex; padding-top: 8rem; justify-content: space-between;">
            <div class="span-part">
                <c:if test= "${not empty totalCourse}">
                    <h2>Total: ${totalCourse} courses</h2>
                </c:if> 
                <c:if test= "${empty totalCourse}">
                    <button type="button" class="button-create" onclick="window.location.href = './cmhome'">Back to homepage</button>
                </c:if>
            </div>            
            <div class="search-bar" style="width: 40%">  
                <input id="searchContent" type="text" placeholder="Search" value="${search}">
                <i id="search-icon" class='bx bx-search' onclick="search()"></i>
            </div>
            <script>
                function search() {
                    let searchContent = document.getElementById('searchContent');
                    window.location.href = './pendingcourse?search=' + searchContent.value;
                }
            </script>
        </div>
        <c:if test="${not empty sessionScope.banMsg && !sessionScope.banMsgDisplayed}">
            <c:set var="sessionScope.banMsgDisplayed" value="true" />
            <script>
                alert("${sessionScope.banMsg}");
            </script> 
        </c:if>
        <table class="table">
            <thead>
                <tr>
                    <th class="text-center">Image</th>
                    <th class="text-center">Course Name</th>
                    <th class="text-center">Author</th>
                    <th class="text-center">Verify</th>
                </tr>
            </thead>
            <c:if test="${empty totalCourse}">
                <tr>
                    <td colspan="4"><p class="text-center" style="font-size: 60px;color: #777;">No course found</p></td>
                </tr>
            </c:if>
            <c:forEach items="${listCourse}" var="courseEntry">
                <tr>
                    <c:set var="courses" value="${courseEntry.key}" />
                <input type="hidden" name="id" value="${courses.getCourseId()}"/>
                <td><img height='120px' src="${courses.getImage()}" alt="Course Image"></td>
                <td>${courses.getCourseName()}</td>
                <td>${courses.getAuthor().getFullName()}</td>
                <td><a class="btn" style="height:40px;
                       background-color: #6f49f5;
                       color: white;" href="pendingdetail?cid=${courses.getCourseId()}&modeVerify=1">Verify</a></td>
            </tr>
        </c:forEach>    
        <div style="clear: both"></div>
    </table>
    <div class="pagination-container">
        <div class="pagination">
            <c:if test="${tag > 1}">
                <a class="page" href="pendingcourse?index=${tag - 1}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${endPage}" var="i">
                <c:choose>
                    <c:when test="${i == tag}">
                        <span class="page active">${i}</span>
                    </c:when>
                    <c:when test="${i <= 5 || i >= tag - 1 && i <= tag + 1 || i >= endPage - 4}">
                        <a class="page" href="pendingcourse?index=${i}">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <span class="page">...</span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${tag < endPage}">
                <a class="page" href="pendingcourse?index=${tag + 1}">Next</a>
            </c:if>
        </div>
    </div>
    <div class="redirect" >
        <form method="get" action="pendingcourse">
            <span class="go-to">Go to page:</span>
            <input type="number" name="index" id="pageInput" oninput="limit(this)" />
            <input type="submit" value="Go" />
        </form>
    </div>
    <script>
        
        function openNav() {
            document.getElementById("mySidebar").style.width = "250px";
            document.getElementById("main").style.marginLeft = "250px";
            document.getElementById("main-content").style.marginLeft = "250px";
            document.getElementById("main").style.display = "none";
        }
        function closeNav() {
            document.getElementById("mySidebar").style.width = "0";
            document.getElementById("main").style.marginLeft = "0";
            document.getElementById("main").style.display = "block";
        }
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
