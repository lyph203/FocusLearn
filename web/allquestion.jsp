<%-- 
    Document   : allquestion
    Created on : Oct 21, 2023, 3:50:31 PM
    Author     : Pham Huong Ly
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>All Question</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="icon" href="images/logo.png">        
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/allquestion.css">
        <link rel="stylesheet" href="css/popupmodal.css">
        <link rel="icon" href="images/logo.png">
        <style>
            #search:hover {
                cursor: pointer;
            }
        </style>
    </head>
    <body style="font-family: 'Times New Roman';
          font-size: 16px;">
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
                    <input id="searchCourse" type="text" placeholder="Search">
                    <i id="search" class='bx bx-search' onclick="searchCourse()"></i>
                </div>

                <script>
                    function searchCourse() {
                        window.location.href = './viewCourse?searchContent=' + document.getElementById('searchCourse').value;
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

        <div class="main-content" style="height: 100vh; padding: 0">
            <div class="main-content" style="float: left; width: 100%;">
                <div class="Title" style="display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <h2>All Question</h2>
                        <p style="width: 60%; text-align: justify;">
                            An open environment ideal for continuous learning and development, questions will be provided
                            by a community of users and experts with the most accurate and useful answers for you.
                        </p>
                    </div>


                    <c:if test="${sessionScope.user != null}">
                        <button type="button" class="button-view" onclick="viewMyQuestions()">My Questions</button>
                        <span style="margin: 10px;"></span>
                        <button type="button" class="button-ask" data-toggle="modal" data-target="#myModal">Create Question</button>
                    </c:if>

                </div>

                <div class="tool-bar">
                    <div></div>
                    <div class="search-bar">
                        <input id="searchInput" type="text" placeholder="Search Questions..."> 
                        <i id="search" class="bx bx-search" onclick="submitSearch()"></i>
                    </div>
                </div>

                <div class="list-questions">
                    <div class="filter-sections">
                        <form id="filter" action="viewquestion">
                            <div class="filter-option">
                                <h2>Tags</h2>   
                                <div class="filter-option" style="height: 50vh;
                                     overflow: scroll;">

                                    <c:forEach items="${listTags}" var="tags">
                                        <div class="filter-checkbox">
                                            <input type="checkbox" value="${tags.getTagId()}" name="tagsID"
                                                   <c:forEach items="${tagsID}" var="tagID">
                                                       ${tagID eq tags.getTagId() ? 'checked="checked"' : ''} 
                                                   </c:forEach>   onclick="submitForm()">
                                            <span>${tags.getTagName()}</span>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>    
                        </form>
                    </div>
                </div>

                <!--Modal for create question-->        
                <div class="modal fade custom-modal" id="myModal" role="dialog">
                    <div class="modal-dialog">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Ask a Question</h4>
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                            </div>
                            <div class="modal-body">
                                <form action="createquestion" method="POST">
                                    <input type="hidden" value="add" name="act">
                                    <div class="form-group">
                                        <label for="name">Question Title:</label>
                                        <input type="text" class="form-control" name="qtitle" required value="${param.qtitle}">
                                    </div>
                                    <div class="form-group">
                                        <label for="name">Question Content:</label>
                                        <textarea class="form-control" name="qcontent" minlength="20" required>${param.qcontent}</textarea>
                                    </div>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-secondary" id="upload" style="height:40px">Create</button>
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
                    function submitSearch() {
                        window.location.href = './viewquestion?searchContent=' + document.getElementById('searchInput').value;
                    }
                    function submitForm() {
                        document.getElementById('filter').submit();
                    }
                </script>

                <div class="questions">   
                    <div class="list-question" id="list">
                        <c:if test="${not empty requestScope.errorMsg}">
                            <p class="text-center" style="font-size: 60px;color: #777;">No question found</p>
                        </c:if>
                        <c:forEach items="${listPagedQuestion}" var="question">
                            <c:set var="isCurrentUser" value="${question.getUser().getUsername() eq sessionScope.user.getUsername()}" />
                            <a href="questiondetail?id=${question.getQuestionId()}" class="question">
                                <div class="question-details">
                                    <h2>${question.getTitle()}</h2>
                                    <p class="question-content">${question.getContent()}</p>
                                    <div class="question-meta">
                                        <span class="author">Author: ${question.getUser().getFullName()} | </span>
                                        <span class="created-date">Created Date: ${question.getCreatedDate()}</span>
                                        <span class="update-status" style="opacity: 70%">
                                            <c:if test="${question.getUpdateDate() != null}"> | (Edited)</c:if></span>
                                        </div>
                                    </div>
                                </a>
                        </c:forEach>
                    </div>
                    <div class="pagination-container">
                        <div class="pagination">
                            <c:if test="${tag > 1}">
                                <a class="page" href="viewquestion?index=${tag - 1}&
                                   <c:if test="${tagsID != null}">
                                       <c:forEach items="${tagsID}" var="tagID">
                                           &tagsID=${tagID}&
                                       </c:forEach>
                                   </c:if>
                                   <c:if test="${searchContent != null}">
                                       &searchContent=${searchContent}&
                                   </c:if>">Previous</a>
                            </c:if>
                            <c:forEach begin="1" end="${endPage}" var="i">
                                <c:choose>
                                    <c:when test="${i == tag}">
                                        <span class="page active">${i}</span>
                                    </c:when>
                                    <c:when test="${i <= 5 || i >= tag - 1 && i <= tag + 1 || i >= endPage - 4}">
                                        <a class="page" href="viewquestion?index=${i}&
                                           <c:if test="${tagsID != null}">
                                               <c:forEach items="${tagsID}" var="tagID">
                                                   &tagsID=${tagID}&
                                               </c:forEach>
                                           </c:if>
                                           <c:if test="${searchContent != null}">
                                               &searchContent=${searchContent}&
                                           </c:if>">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="page">...</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${tag < endPage}">
                                <a class="page" href="viewquestion?index=${tag + 1}&
                                   <c:if test="${tagsID != null}">
                                       <c:forEach items="${tagsID}" var="tagID">
                                           &tagsID=${tagID}&
                                       </c:forEach>
                                   </c:if>
                                   <c:if test="${searchContent != null}">
                                       &searchContent=${searchContent}&
                                   </c:if>">Next</a>
                            </c:if>
                        </div>
                    </div>
                    <div class="redirect" >
                        <form method="get" action="viewquestion">
                            <span class="go-to">Go to page:</span>
                            <c:if test="${tagsID != null}">
                                <c:forEach items="${tagsID}" var="tagID">
                                    <input type="hidden" name="tagsID" value="${tagID}">
                                </c:forEach>
                            </c:if>         
                            <input type="number" name="index" id="pageInput" oninput="limit(this)"/>
                            <c:if test="${searchContent != null}">
                                <input type="hidden" name="searchContent" value="${searchContent}">
                            </c:if>        
                            <input type="submit" value="Go" />
                        </form>
                    </div>
                </div>
            </div>  
            <div style="clear: both"></div>
            <div class="footer" style="float: left;">
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
                // Get info to perform paging
                const listQuestion = document.getElementsByClassName('question');
                let arr = Array.from(listQuestion);

                const itemsPerPage = 6;
                const paginationContainer = "#paginationI";
                const temp = "#list";
                let currentPage = 1; // Initialize current page

                // Paging function
                pagination(arr, itemsPerPage, paginationContainer);

                function check(containerDiv) {
                    containerDiv.childNodes[1].click();
                }
                function display() {
                    let dropdownMenu = document.getElementsByClassName('dropdown-menu')[0];
                    if (dropdownMenu.style.display !== 'block') {
                        dropdownMenu.style.display = 'block';
                    } else {
                        dropdownMenu.style.display = 'none';
                    }
                }

                function sendCheckboxValue(element) {
                    element.setAttribute("href", window.location.href + "?" + element.value);
                }

                $("input[name='tagsID']").click(function () {
                    sendCheckboxValue(this);
                });

                function viewMyQuestions() {
                    window.location.href = "/FocusLearn/viewlistcreatedquestion";
                }

                function redirectLogin() {
                    window.location.href = "/FocusLearn/login.jsp";
                }
                function limit(input) {
                    if (input.value > ${endPage}) {
                        input.value = ${endPage};
                    } else if (input.value < 1 || input.value === '') {
                        input.value = 1;
                    }
                }
            </script>
            <script src="https://code.jquery.com/jquery-3.1.1.min.js" ></script>
            <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" ></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
    </body>
</html>
