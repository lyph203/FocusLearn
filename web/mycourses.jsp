<%-- 
    Document   : mycourses
    Created on : Oct 16, 2023, 1:47:27 PM
    Author     : nhatm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>My Courses</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/userprofile.css"/>
        <link rel="stylesheet" href="css/allcourse.css"/>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="css/mycourse.css"/>
        <link rel="icon" href="images/logo.png">

        <style>
            .bxs-star {
                color: gold;
            }
        </style>
    </head>
    <body>
        <div class="contain-navigation">

            <!------------------- Navbar and sidebar ------------------->
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo"/>
                </div>
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
                                <c:choose>
                                    <c:when test="${sessionScope.user.getRole() eq 1 or sessionScope.user.getRole() eq 0}">
                                        <div>
                                            <i class='bx bxs-user' ></i>
                                            <a href="profile">My account</a>
                                        </div>
                                    </c:when>
                                    <c:when test="${sessionScope.user.getRole() eq 2}">
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
                                    </c:when>
                                    <c:otherwise>
                                        <div>
                                            <i class='bx bxs-user' ></i>
                                            <a href="profile">My account</a>
                                        </div>
                                        <div>
                                            <i class='bx bxs-extension' ></i>
                                            <a href="mycourses">My course</a>
                                        </div>
                                        <div style="padding-bottom: 15px;">
                                            <i class='bx bx-history' ></i>
                                            <a href="historypayment">History payment</a>
                                        </div>
                                        <div style="padding-bottom: 15px;">
                                            <i class='bx bxs-wallet'></i>
                                            <a href="mywallet.jsp">My Wallet</a>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

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

        <!-------------------- Main content -------------------->
        <div class="main-content">

            <!-------------------- Side bar -------------------->
            <div class="side-bar">
                <h2>Profile & Setting</h2>
                <c:choose>
                    <c:when test="${sessionScope.user.getRole() eq 1 or sessionScope.user.getRole() eq 0}">
                        <div class="user-option" onclick="profile()">
                            <i class='bx bx-user-circle'></i>
                            <p>User profile</p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.user.getRole() eq 2}">
                        <div class="user-option" onclick="profile()">
                            <i class='bx bx-user-circle'></i>
                            <p>User profile</p>
                        </div>
                        <div class="user-option" onclick="payment_history()">
                            <i class='bx bx-history' ></i>
                            <p>History Payment</p>
                        </div>
                        <div class="user-option" onclick="my_wallet()">
                            <i class='bx bxs-wallet'></i>
                            <p>My Wallet</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="user-option" onclick="profile()">
                            <i class='bx bx-user-circle'></i>
                            <p>User profile</p>
                        </div>
                        <div class="user-option" onclick="my_courses()">
                            <i class='bx bxs-extension' ></i>
                            <p>My course</p>
                        </div>
                        <div class="user-option" onclick="payment_history()">
                            <i class='bx bx-history' ></i>
                            <p>History Payment</p>
                        </div>
                        <div class="user-option" onclick="my_wallet()">
                            <i class='bx bxs-wallet'></i>
                            <p>My Wallet</p>
                        </div>
                        <div class="user-option" onclick="home()">
                            <i class='bx bx-home' ></i>
                            <p>Home</p>
                        </div>                        
                    </c:otherwise>
                </c:choose>
            </div>

            <!-------------------- Courses -------------------->
            <div class="">
                <div class="head" style="padding: 8px">
                    <h1>My Courses</h1>
                    <div class="tool-bar" style="justify-content: flex-end">
                        <div class="search-bar">
                            <input type="text" placeholder="search courses..."> 
                            <i class="bx bx-search"></i>
                        </div>
                    </div>
                </div>
                <div class="" id="list">
                    <div class="container">
                        <c:forEach items="${listPagedCourse}" var="courseEntry">
                            <c:set var="courses" value="${courseEntry.key}" />
                            <c:set var="ratingData" value="${courseEntry.value}" />
                            <div class="vote">
                                <a href="learn?id=${courses.getCourseId()}" class="course">
                                    <div class="course-info" style="display: flex">
                                        <div class="image">
                                            <img src="${courses.getImage()}" alt="alt" style="width: 150px; height: 150px"/>
                                        </div>
                                        <div class="course-description">
                                            <h2>${courses.getCourseName()}</h2>
                                            <span>${courses.getAuthor().getFullName()}</span>
                                            <span style="background-color: rgb(24, 185, 24); color: #f4eeee; padding: 0.1rem; width: fit-content; margin-top: 8px">${courses.getLevel()}</span>

                                            <c:forEach items="${listCourseProgress}" var="progressEntry">
                                                <c:set var="progress" value="${progressEntry.key}"/>
                                                <c:set var="progressValue" value="${progressEntry.value}"/>
                                                <c:if test="${courses.getCourseId() eq progress.getCourseId()}">
                                                    <div class="progress-container">
                                                        <c:if test="${progressValue == 0}">
                                                            <div class="progress" style="width: ${progressValue}%; color: black">${Math.floor(progressValue)}%</div>
                                                        </c:if>
                                                        <c:if test="${progressValue != 0}">
                                                            <div class="progress" style="width: ${progressValue}%; color: white">${Math.floor(progressValue)}%</div>
                                                        </c:if>
                                                    </div>
                                                </c:if>
                                            </c:forEach>

                                        </div>
                                    </div>
                                    <div class="course-rating">
                                        <c:forEach var="ratingEntry" items="${ratingData}">
                                            <c:set var="rating" value="${ratingEntry.key}"/>
                                            <span>Average Rating: ${String.format("%.2f", rating)}</span>
                                            <c:set var="roundedRating" value="${Math.floor(rating)}"/>
                                            <c:set var="hasHalfStar" value="${rating - roundedRating > 0}"/>
                                            <div>
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
                                            </div>
                                            <span>(${ratingEntry.value} rates)</span>
                                        </c:forEach>
                                    </div>
                                </a>
                                <div class="rating" style="background: lightgray; margin-bottom: 10px">
                                    <a href="#" data-modal-trigger aria-controls="model-${courses.getCourseId()}" aria-expanded="false">
                                        Rate course
                                    </a>
                                </div>
                                   
                                <c:forEach items="${listCourseProgress}" var="progressEntry">
                                    <c:set var="progress" value="${progressEntry.key}"/>
                                    <c:set var="progressValue" value="${progressEntry.value}"/>
                                    <c:if test="${courses.getCourseId() eq progress.getCourseId()}">
                                        <c:if test="${Math.floor(progressValue) eq '100.0'}">
                                            <div class="rating" style="background: lightgray" style="margin-top: 0">
                                                <a href="certificatedisplay?courseId=${courses.getCourseId()}" >Get certificate</a>
                                            </div>
                                        </c:if>
                                    </c:if>

                                </c:forEach>        


                            </div>
                            <section class="modal" id="model-${courses.getCourseId()}" data-modal-target>
                                <div class="modal__overlay" data-modal-close tabindex="-1"></div>
                                <div class="modal__wrapper">
                                    <div class="modal__header">
                                        <div class="modal__title">
                                            Rate this course
                                        </div>
                                        <button class="modal__close" data-modal-close aria-label="Close Modal"></button>
                                    </div>
                                    <div class="modal__content" style="text-align: center; font-size: 25px">
                                        <i class="bx bx-star rate-star" onclick="rating(${courses.getCourseId()}, 1)"></i>
                                        <i class="bx bx-star rate-star" onclick="rating(${courses.getCourseId()}, 2)"></i>
                                        <i class="bx bx-star rate-star" onclick="rating(${courses.getCourseId()}, 3)"></i>
                                        <i class="bx bx-star rate-star" onclick="rating(${courses.getCourseId()}, 4)"></i>
                                        <i class="bx bx-star rate-star" onclick="rating(${courses.getCourseId()}, 5)"></i>
                                    </div>
                                </div>
                            </section>             


                        </c:forEach>
                    </div>
                </div>


                <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
                <script>
                                            $(document).ready(function () {
                                                $(".rate-star").hover(
                                                        function () {
                                                            $(this).css("color", "gold");
                                                            $(this).prevAll().css("color", "gold");
                                                            $(this).nextAll().css("color", "gray");
                                                            let filledStars = $(this).index() + 1;
                                                            console.log(filledStars); // You can replace this with your desired action
                                                        },
                                                        function () {
                                                            $(".bx-star").css("color", "gray");
                                                        }
                                                );
                                            });
                </script>

                <script>
                    function rating(id, star) {
                        window.location.href = './rating?id=' + id + '&star=' + star;
                    }
                </script>

                <c:if test="${listPagedCourse.size() ne 0}">
                    <div class="pagination-container">
                        <div class="pagination">
                            <c:if test="${tag > 1}">
                                <a class="page" href="viewCourse?index=${tag - 1}">Previous</a>
                            </c:if>
                            <c:forEach begin="1" end="${endPage}" var="i">
                                <c:choose>
                                    <c:when test="${i == tag}">
                                        <span class="page active">${i}</span>
                                    </c:when>
                                    <c:when test="${i <= 5 || i >= tag - 1 && i <= tag + 1 || i >= endPage - 4}">
                                        <a class="page" href="mycourses?index=${i}">${i}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="page">...</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${tag < endPage}">
                                <a class="page" href="mycourses?index=${tag + 1}">Next</a>
                            </c:if>
                        </div>
                    </div>
                    <div class="redirect" >
                        <form method="get" action="mycourses">
                            <span class="go-to">Go to page:</span>
                            <input type="number" name="index" id="pageInput" oninput="limit(this)"/>
                            <input type="submit" value="Go" />
                        </form>
                    </div>
                </c:if>

                <c:if test="${listPagedCourse.size() eq 0}">
                    <div style="margin-top: 100px">
                        <p style="text-align: center; font-size: 40px; color: #888">You haven't enrolled any course</p>
                    </div>
                </c:if>


            </div>
        </div>

        <div style="clear: both"></div>
        <div class="footer">
            <div class="contain-component">
                <div class="component">
                    <h3>Categories</h3>
                    <ul>
                        <li>Java</li>
                        <li>JavaScript</li>
                        <li>.Net</li>
                        <li>Business</li>
                    </ul>
                </div>
                <div class="component">
                    <h3>Help</h3>
                    <ul>
                        <li>Java</li>
                        <li>JavaScript</li>
                        <li>.Net</li>
                        <li>Business</li>
                    </ul>
                </div>
                <div class="component">
                    <h3>Get in touch</h3>
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
                    <h3>NEWSLETTER</h3>
                    <p>
                        ohnononono
                    </p>
                </div>
            </div>

            <div class="copy-right">
                © 2023 FocusLearn, Inc.
            </div>
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
        function my_courses() {
            window.location.href = './mycourses';
        }
        function profile() {
            window.location.href = './profile';
        }
        function payment_history() {
            window.location.href = './historypayment';
        }
        function my_wallet() {
            window.location.href = 'mywallet.jsp';
        }
    </script>

    <script>
        function limit(input) {
            if (input.value > ${endPage}) {
                input.value = ${endPage};
            } else if (input.value < 1 || input.value === '') {
                input.value = 1;
            }
        }
        var modal = function () {
            /**
             * Element.closest() polyfill
             * https://developer.mozilla.org/en-US/docs/Web/API/Element/closest#Polyfill
             */
            if (!Element.prototype.closest) {
                if (!Element.prototype.matches) {
                    Element.prototype.matches = Element.prototype.msMatchesSelector || Element.prototype.webkitMatchesSelector;
                }
                Element.prototype.closest = function (s) {
                    var el = this;
                    var ancestor = this;
                    if (!document.documentElement.contains(el))
                        return null;
                    do {
                        if (ancestor.matches(s))
                            return ancestor;
                        ancestor = ancestor.parentElement;
                    } while (ancestor !== null);
                    return null;
                };
            }

            //
            // Settings
            //
            var settings = {
                speedOpen: 50,
                speedClose: 250,
                activeClass: 'is-active',
                visibleClass: 'is-visible',
                selectorTarget: '[data-modal-target]',
                selectorTrigger: '[data-modal-trigger]',
                selectorClose: '[data-modal-close]',
            };

            //
            // Methods
            //
            // Toggle accessibility
            var toggleccessibility = function (event) {
                if (event.getAttribute('aria-expanded') === 'true') {
                    event.setAttribute('aria-expanded', false);
                } else {
                    event.setAttribute('aria-expanded', true);
                }
            };
            // Open Modal
            var openModal = function (trigger) {
                // Find target
                var target = document.getElementById(trigger.getAttribute('aria-controls'));
                // Make it active
                target.classList.add(settings.activeClass);
                // Make body overflow hidden so it's not scrollable
                document.documentElement.style.overflow = 'hidden';
                // Toggle accessibility
                toggleccessibility(trigger);
                // Make it visible
                setTimeout(function () {
                    target.classList.add(settings.visibleClass);
                }, settings.speedOpen);
            };
            // Close Modal
            var closeModal = function (event) {
                // Find target
                var closestParent = event.closest(settings.selectorTarget),
                        childrenTrigger = document.querySelector('[aria-controls="' + closestParent.id + '"');
                // Make it not visible
                closestParent.classList.remove(settings.visibleClass);
                // Remove body overflow hidden
                document.documentElement.style.overflow = '';
                // Toggle accessibility
                toggleccessibility(childrenTrigger);
                // Make it not active
                setTimeout(function () {
                    closestParent.classList.remove(settings.activeClass);
                }, settings.speedClose);
            };
            // Click Handler
            var clickHandler = function (event) {
                // Find elements
                var toggle = event.target,
                        open = toggle.closest(settings.selectorTrigger),
                        close = toggle.closest(settings.selectorClose);
                // Open modal when the open button is clicked
                if (open) {
                    openModal(open);
                }
                // Close modal when the close button (or overlay area) is clicked
                if (close) {
                    closeModal(close);
                }
                // Prevent default link behavior
                if (open || close) {
                    event.preventDefault();
                }
            };
            // Keydown Handler, handle Escape button
            var keydownHandler = function (event) {
                if (event.key === 'Escape' || event.keyCode === 27) {
                    // Find all possible modals
                    var modals = document.querySelectorAll(settings.selectorTarget),
                            i;
                    // Find active modals and close them when escape is clicked
                    for (i = 0; i < modals.length; ++i) {
                        if (modals[i].classList.contains(settings.activeClass)) {
                            closeModal(modals[i]);
                        }
                    }
                }
            };

            //
            // Inits & Event Listeners
            //
            document.addEventListener('click', clickHandler, false);
            document.addEventListener('keydown', keydownHandler, false);

        };
        modal();
    </script>
</body>
</html>

