<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lecturer Home</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="css/lecturerhome.css">
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
        </style>
    </head>
    <body>
        <div class="sidebar" id="mySidebar">
            <div class="side-header">
                <img src="images/pro.png" width="100" height="100" alt=""> 
                <h5 style="margin-top:10px;">Hello,${sessionScope.user.getFullName()}</h5>
            </div>
            <hr style="border:1px solid; background-color:#3b9dc3; border-color:#3b9dc3;">
            <a style="padding-right: 30px;" href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
            <a href="lecturerhome" >Dashboard</a>
        </div>

        <div class="contain-navigation">
            <nav class="navigation-bar">
                <div class="logo" id="main">
                    <button class="openbtn" onclick="openNav()"><img src="images/logo.png" alt="logo"/></button>
                </div>
                <div class="search-bar" style="min-width: 500px; margin-left: 500px">
                    <form action="lecturerhome" method="post" id="search">
                        <i class='bx bx-search' style="position: absolute;margin-top: 12px;margin-left: 12px;"></i>
                        <div class="search-bar" style="min-width: 500px"> 
                            <input id="searchContent" type="text" placeholder="Search" name="inputSearch" value="${inputSearch}" form="search" style="width: 100%">
                            <i id="search-icon" class='bx bx-search' onclick="search()" style="margin: 0"></i>
                        </div>
                        <input type="hidden" value="1" name="search">
                    </form>
                </div>
                <script>
                    function search() {
                        let searchContent = document.getElementById('searchContent');
                        window.location.href = './lecturerhome?inputSearch=' + searchContent.value + '&search=1';
                    }
                </script>
                <div class="account" >
                    <img style="position: absolute; right: 0; top: 32%" src="${sessionScope.user.getImg()}" alt="" onclick="display()">
                    <div class="col-lg-2 dropdown-menu" style="left: 82%">
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
                                <div style="padding-bottom: 15px;">
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
            </nav>
        </div>
        <div class="span-part">
            <c:if test= "${not empty totalCourse}">
                <h2>Total: ${totalCourse} courses</h2>
                <button type="button" class="button-create" data-toggle="modal" data-target="#myModal">Create new course</button>
            </c:if> 
            <c:if test= "${empty totalCourse}">
                <button type="button" class="button-create" onclick="window.history.back()">Back to homepage</button>
                <button type="button" class="button-create" data-toggle="modal" data-target="#myModal">Create new course</button>
            </c:if>
        </div>


        <table class="table">
            <thead>
                <tr>
                    <th class="text-center">Image</th>
                    <th class="text-center">Course Name</th>
                    <th class="text-center">Rating Star</th>
                    <th class="text-center">Number of Rates</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">Operations</th>
                </tr>
            </thead>
            <c:if test="${empty totalCourse}">
                <tr>
                    <td colspan="6"><p class="text-center" style="font-size: 60px;color: #777;">No course found</p></td>
                </tr>

            </c:if>
            <c:forEach items="${listLecturerCourse}" var="courseEntry">
                <tr>
                    <c:set var="courses" value="${courseEntry.key}" />
                    <c:set var="ratingData" value="${courseEntry.value}" />
                <input type="hidden" name="id" value="${courses.getCourseId()}"/>
                <c:choose>
                    <c:when test="${courses.getImage().startsWith('https://')}">
                        <td><img height='120px' src="${courses.getImage()}" alt="Course Image"></td>
                        </c:when>
                        <c:otherwise>
                        <td><img height='120px' src="images/${courses.getImage()}" alt="Course Image"></td>
                        </c:otherwise>
                    </c:choose>
                <td>${courses.getCourseName()}</td>
                <c:forEach var="ratingEntry" items="${ratingData}">
                    <c:set var="rating" value="${ratingEntry.key}"/>
                    <td><span>${String.format("%.2f", rating)}</span></td>
                    <td><span>${ratingEntry.value}</span></td>
                </c:forEach>
                <td>
                    <c:if test="${courses.getStatus() == 1}">
                        <input class="status-input status-active" type="text" value="Active" readonly>
                    </c:if>
                    <c:if test="${courses.getStatus() == 0}">
                        <input class="status-input status-banned" type="text" value="Deactive" readonly>
                    </c:if>
                    <c:if test="${courses.getStatus() == 2}">
                        <input class="status-input status-pending" type="text" value="Pending" readonly>      
                    </c:if> 
                    <c:if test="${courses.getStatus() == 3}">
                        <input class="status-input status-saved"  type="text" readonly value="Draft Saved">
                    </c:if>
                    <c:if test="${courses.getStatus() == 4}">
                        <input class="status-input status-rejected"  type="text" readonly value="Rejected">
                    </c:if>
                </td>
                <td>
                    <div class="btn-group">
                        <a class="btn btn-primary" style="height:40px" href="updatecourse?cid=${courses.getCourseId()}">View & Update detail</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        <div style="clear: both"></div>
    </table>
    <div class="pagination-container">
        <div class="pagination">
            <c:if test="${tag > 1}">
                <a class="page" href="lecturerhome?index=${tag - 1}">Previous</a>
            </c:if>
            <c:forEach begin="1" end="${endPage}" var="i">
                <c:choose>
                    <c:when test="${i == tag}">
                        <span class="page active">${i}</span>
                    </c:when>
                    <c:when test="${i <= 5 || i >= tag - 1 && i <= tag + 1 || i >= endPage - 4}">
                        <a class="page" href="lecturerhome?index=${i}">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <span class="page">...</span>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${tag < endPage}">
                <a class="page" href="lecturerhome?index=${tag + 1}">Next</a>
            </c:if>
        </div>
    </div>
    <div class="redirect" >
        <form method="get" action="lecturerhome">
            <span class="go-to">Go to page:</span>
            <input type="number" name="index" id="pageInput" oninput="limit(this)"/>
            <input type="submit" value="Go" />
        </form>
    </div>

    <c:if test="${not empty requestScope.msg}">
        <script>
            alert("${msg}");
        </script>
    </c:if>

    <!--Modal for add course-->        
    <div class="modal fade custom-modal" id="myModal" role="dialog">
        <div class="modal-dialog">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Primary Properties of Course</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <form action="createcourse" method="POST" enctype="multipart/form-data">
                        <input type="hidden" value="add" name="act">
                        <div class="form-group">
                            <label for="name">Course Name:</label>
                            <input type="text" class="form-control" name="name" required value="${param.name}">
                        </div>
                        <div class="form-group">
                            <label for="qty">Short Description:</label>
                            <input type="text" class="form-control" name="shortDescription" required value="${param.shortDescription}">
                        </div>
                        <div class="form-group">
                            <label for="file">Thumbnail Image Link:</label>
                            <input type="text" class="form-control-file" id="file" name="img" required value="${param.img}">
                        </div>
                        <div class="form-group">
                            <label for="name">Price:</label>
                            <input type="text" class="form-control" name="price" required value="${param.price}">
                        </div>
                        <div class="form-group">
                            <label for="level">Level:</label>
                            <select name="level" id="level" required>
                                <option value="Beginner">Beginner</option>
                                <option value="Intermediate">Intermediate</option>
                                <option value="Advanced">Advanced</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="qty">Detail Description:</label>
                            <input type="text" class="form-control" name="longDescription" required value="${param.longDescription}">
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-secondary" id="upload" style="height:40px">Next Step</button>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal" style="height:40px">Close</button>
                </div>
            </div>
        </div>
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
    <script src="https://code.jquery.com/jquery-3.1.1.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
