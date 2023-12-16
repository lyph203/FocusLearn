
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>All Course</title>
        <link rel="stylesheet" href="css/style.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="icon" href="images/logo.png">
        <link rel="stylesheet" href="css/allcourse.css">

        <style>
            #search:hover {
                cursor: pointer;
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
                <div class="search-bar" style="width: 40vw;"> </div>
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
                                    <img src="images/pro.png" alt="">
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

        <div class="main-content" style="height: fit-content">

            <div class="Title">
                <h2>All Public courses</h2>
                <p style="width: 60%; text-align: justify;">
                    See why millions of people turn to FocusLearn's real-world experts to learn new knowledge.
                    Learn at your own pace with hands-on exercises and quizzes. 
                    Our courses are frequently updated so you’ll always be working from the latest information. 
                    This is the training you’ll need to become a professional lecturer.
                </p>
            </div>
            <div class="tool-bar">
                <div class="submit">
                    <button class="btn-filter" onclick ="window.location.href = './viewCourse'">
                        <i class='bx bx-reset'></i>
                        Reset
                    </button>
                </div>
                <div class="search-bar">
                    <input id="searchText" type="text" placeholder="Search courses..." value="${search}"> 
                    <i id="search" class="bx bx-search" onclick="submitForm()"></i>
                </div>
            </div>

            <div class="list-courses">
                <div class="filter-sections">
                    <form id="filter" action="viewCourse">
                        <input id="searchInput" type="hidden" name="searchContent">
                        <div class="filter-option">
                            <h2>Ratings</h2>
                            <div>
                                <input type="radio" name="ratings" value="4.5"
                                       <c:forEach items="${ratings}" var="rating">
                                           ${rating eq 4.5 ? 'checked="checked"' : ''}  
                                       </c:forEach> onclick="submitForm()">
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <span>4.5 & up</span>
                            </div>
                            <div>
                                <input type="radio" name="ratings" value="4.0"
                                       <c:forEach items="${ratings}" var="rating">
                                           ${rating eq 4.0 ? 'checked="checked"' : ''}  
                                       </c:forEach> onclick="submitForm()">
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <span>4.0 & up</span>
                            </div>
                            <div>
                                <input type="radio" name="ratings" value="3.5"
                                       <c:forEach items="${ratings}" var="rating">
                                           ${rating eq 3.5 ? 'checked="checked"' : ''}  
                                       </c:forEach> onclick="submitForm()">
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <span>3.5 & up</span>
                            </div>
                            <div>
                                <input type="radio" name="ratings" value="3.0"
                                       <c:forEach items="${ratings}" var="rating">
                                           ${rating eq 3.0 ? 'checked="checked"' : ''}  
                                       </c:forEach> onclick="submitForm()">
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <i class="bx bx-star"></i>
                                <span>3.0 & up</span>
                            </div>

                        </div>

                        <div class="filter-option">
                            <h2>Level</h2>
                            <div class="filter-checkbox">
                                <input type="checkbox" name="level" value="beginner"
                                       <c:forEach items="${level}" var="level">
                                           ${level eq 'beginner' ? 'checked="checked"' : ''}  
                                       </c:forEach>   onclick="submitForm()">
                                <span>Beginner</span>
                            </div>
                            <div class="filter-checkbox">
                                <input type="checkbox" name="level" value="intermediate" 
                                       <c:forEach items="${level}" var="level">
                                           ${level eq 'intermediate' ? 'checked="checked"' : ''}  
                                       </c:forEach>   onclick="submitForm()">
                                <span>Intermediate</span>
                            </div>
                            <div class="filter-checkbox">
                                <input type="checkbox" name="level" value="advanced" 
                                       <c:forEach items="${level}" var="level">
                                           ${level eq 'advanced' ? 'checked="checked"' : ''}  
                                       </c:forEach>     onclick="submitForm()">
                                <span>Advanced</span>
                            </div>
                        </div>

                        <div class="filter-option">
                            <h2>Price</h2>
                            <div class="filter-checkbox">
                                <input type="checkbox" value="0" name="price"
                                       <c:forEach items="${price}" var="price">
                                           ${price eq 0 ? 'checked="checked"' : ''}  
                                       </c:forEach>   onclick="submitForm()">
                                <span>Free</span>
                            </div>
                            <div class="filter-checkbox">
                                <input type="checkbox" value="1000000" name="price"
                                       <c:forEach items="${price}" var="price">
                                           ${price eq 1000000 ? 'checked="checked"' : ''}  
                                       </c:forEach>   onclick="submitForm()">
                                <span>Up to 1,000,000</span>
                            </div>
                            <div class="filter-checkbox">
                                <input type="checkbox" value="1000001" name="price"
                                       <c:forEach items="${price}" var="price">
                                           ${price eq 1000001 ? 'checked="checked"' : ''}  
                                       </c:forEach> onclick="submitForm()">
                                <span>More than 1,000,000</span>
                            </div>
                        </div>
                        <h2>Tags</h2>   
                        <div class="filter-option" style="margin-bottom: 300px;
                             height: 200px;
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
                    </form>
                </div>

                <script>
                    function submitForm() {
                        document.getElementById('searchInput').value = document.getElementById('searchText').value;
                        document.getElementById('filter').submit();
                    }
                </script>

                <div class="courses">   
                    <div class="list-course" id="list">
                        <c:if test="${not empty requestScope.errorMsg}">
                            <span style="font-size: 60px;color: #777;">No courses found</span>
                        </c:if>
                        <c:forEach items="${listPagedCourse}" var="courseEntry">
                            <c:set var="courses" value="${courseEntry.key}" />
                            <c:set var="ratingData" value="${courseEntry.value}" />
                            <a href="coursedetail?id=${courses.getCourseId()}" class="course">
                                <div class="course-thumbnail">
                                    <img src="${courses.getImage()}" alt="Course Image">
                                </div>
                                <div class="course-details">
                                    <h2>${courses.getCourseName()}</h2>
                                    <p class="course-description">${courses.getDescription()}</p>
                                    <div class="course-meta">
                                        <p class="author">Author: ${courses.getAuthor().getFullName()}</p>
                                        <div class="rating">
                                            <c:forEach var="ratingEntry" items="${ratingData}">
                                                <c:set var="rating" value="${ratingEntry.key}"/>
                                                <span>Average Rating: ${String.format("%.2f", rating)}</span>
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
                                        <p class="level">${courses.getLevel()}</p>
                                    </div>
                                    <strong class="course-price">
                                        <span class="new" style="margin-right: 10px">${String.format("%,.0f", courses.getPrice() * (100 - courses.getDiscount())/100)} VND</span>
                                        <span class="old"> ${String.format("%,.0f", courses.getPrice())} VND</span>
                                    </strong>
                                </div>
                            </a>
                        </c:forEach>

                    </div>
                    <div class="pagination-container">
                        <div class="pagination">
                            <c:if test="${tag > 1}">
                                <a class="page" href="viewCourse?index=${tag - 1}&
                                   <c:if test="${ratings != null}">
                                       &ratings=${ratings[0]}
                                   </c:if>
                                   <c:if test="${level != null}">
                                       <c:forEach items="${level}" var="level">
                                           &level=${level}&
                                       </c:forEach>
                                   </c:if>
                                   <c:if test="${price != null}">
                                       <c:forEach items="${price}" var="price">
                                           &price=${price}&
                                       </c:forEach>
                                   </c:if>
                                   <c:if test="${tagsID != null}">
                                       <c:forEach items="${tagsID}" var="tagsID">
                                           &tagsID=${tagsID}&
                                       </c:forEach>
                                   </c:if>">Previous</a>
                            </c:if>
                            <c:forEach begin="1" end="${endPage}" var="i">
                                <c:choose>
                                    <c:when test="${i == tag}">
                                        <span class="page active">${i}</span>
                                    </c:when>
                                    <c:when test="${i <= 5 || i >= tag - 1 && i <= tag + 1 || i >= endPage - 4}">
                                        <a class="page" href="viewCourse?index=${i}&
                                           <c:if test="${ratings != null}">
                                               &ratings=${ratings[0]}
                                           </c:if>
                                           <c:if test="${level != null}">
                                               <c:forEach items="${level}" var="level">
                                                   &level=${level}&
                                               </c:forEach>
                                           </c:if>
                                           <c:if test="${price != null}">
                                               <c:forEach items="${price}" var="price">
                                                   &price=${price}&
                                               </c:forEach>
                                           </c:if>
                                           <c:if test="${tagsID != null}">
                                               <c:forEach items="${tagsID}" var="tagsID">
                                                   &tagsID=${tagsID}&
                                               </c:forEach>
                                           </c:if>">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="page">...</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${tag < endPage}">
                                <a class="page" href="viewCourse?index=${tag + 1}&
                                   <c:if test="${ratings != null}">
                                       &ratings=${ratings[0]}
                                   </c:if>
                                   <c:if test="${level != null}">
                                       <c:forEach items="${level}" var="level">
                                           &level=${level}&
                                       </c:forEach>
                                   </c:if>
                                   <c:if test="${price != null}">
                                       <c:forEach items="${price}" var="price">
                                           &price=${price}&
                                       </c:forEach>
                                   </c:if>
                                   <c:if test="${tagsID != null}">
                                       <c:forEach items="${tagsID}" var="tagsID">
                                           &tagsID=${tagsID}&
                                       </c:forEach>
                                   </c:if>">Next</a>
                            </c:if>
                        </div>
                    </div>
                    <div class="redirect" >
                        <form method="get" action="viewCourse">
                            <span class="go-to">Go to page:</span>
                            <c:if test="${ratings != null}">
                                <input type="hidden" name="ratings" value="${ratings[0]}">
                            </c:if>
                            <c:if test="${level != null}">
                                <c:forEach items="${level}" var="level">
                                    <input type="hidden" name="level" value="${level}">
                                </c:forEach>
                            </c:if>    
                            <c:if test="${price != null}">
                                <c:forEach items="${price}" var="price">
                                    <input type="hidden" name="price" value="${price}">
                                </c:forEach>
                            </c:if> 
                            <c:if test="${tagsID != null}">
                                <c:forEach items="${tagsID}" var="tagsID">
                                    <input type="hidden" name="tagsID" value="${tagsID}">
                                </c:forEach>
                            </c:if>         
                            <input type="number" name="index" id="pageInput" oninput="limit(this)"/>
                            <input type="submit" value="Go" />
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div style="clear: both"></div>
        <div class="footer" style="position: relative;
             top: 14vh; margin-top: 50px">
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
            const listCourse = document.getElementsByClassName('course');
            let arr = Array.from(listCourse);

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


            $("input[name='ratings']").click(function () {
                sendCheckboxValue(this);
            });

            $("input[name='level']").click(function () {
                sendCheckboxValue(this);
            });

            $("input[name='price']").click(function () {
                sendCheckboxValue(this);
            });
            $("input[name='tagsID']").click(function () {
                sendCheckboxValue(this);
            });

            function limit(input) {
                if (input.value > ${endPage}) {
                    input.value = ${endPage};
                } else if (input.value < 1 || input.value === '') {
                    input.value = 1;
                }
            }
        </script>
    </body>
</html>
