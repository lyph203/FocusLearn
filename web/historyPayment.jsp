<%-- 
    Document   : historyPayment
    Created on : Oct 18, 2023, 2:10:35 PM
    Author     : nhatm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>History Payment</title>
        <link rel="stylesheet" href="css/style.css">
        <link rel="stylesheet" href="css/userprofile.css"/>
        <link rel="stylesheet" href="css/allcourse.css"/>
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="icon" href="images/logo.png">
        <link rel="stylesheet" href="css/historyPayment.css"/>
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
                    <c:when test="${sessionScope.user.getRole() eq 0}">
                        <div class="user-option" onclick="profile()">
                            <i class='bx bx-user-circle'></i>
                            <p>User profile</p>
                        </div>
                        <div class="user-option" onclick="admin()">
                            <i class='bx bx-home' ></i>
                            <p>Home</p>
                        </div> 
                    </c:when>
                    <c:when test="${sessionScope.user.getRole() eq 1}">
                        <div class="user-option" onclick="profile()">
                            <i class='bx bx-user-circle'></i>
                            <p>User profile</p>
                        </div>
                        <div class="user-option" onclick="content()">
                            <i class='bx bx-home' ></i>
                            <p>Home</p>
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
                        <div class="user-option" onclick="lecturer()">
                            <i class='bx bx-home' ></i>
                            <p>Home</p>
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

            <!-------------------- History Payment -------------------->
            <div>
                <h2 style="text-align: center">Payment History</h2>
                <table style="width: 77%;
                       margin-left: 23%;
                       margin-top: 1rem;
                       border-collapse: collapse;" class="history">
                    <tr>
                        <th>Username</th>
                        <th>Amount</th>
                        <th>Created Date</th>
                        <th>Info</th>
                        <th>Status</th>
                    </tr>
                    <c:forEach items="${listPayment}" var="payment">
                        <tr>
                            <td>${payment.getUsername()}</td>
                            <td>
                                <c:if test="${payment.getAmount() < 0}">
                                    <span style="color: red"><fmt:formatNumber value="${payment.getAmount()}" pattern="###,###"/> vnd</span>
                                </c:if>
                                <c:if test="${payment.getAmount() > 0}">
                                    <span style="color: green">+<fmt:formatNumber value="${payment.getAmount()}" pattern="###,###"/> vnd</span>
                                </c:if>
                            </td>
                            <td>
                                <c:set var="dateString" value="${payment.getCreatedDate()}"/>
                                <c:set var="formattedDate" value="${fn:replace(dateString, 'T', ' ')}"/>
                                ${formattedDate}
                            </td>
                            <td>${payment.getInfo()}</td>
                            <td>
                                <c:if test="${payment.getStatus() == 1}">
                                    <span style="color: green;">Success</span>
                                </c:if>
                                <c:if test="${payment.getStatus() == 0}">
                                    <span style="color: red;">Fail</span>
                                </c:if>
                                <c:if test="${payment.getStatus() == 2}">
                                    <span style="color: grey;">Created</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="pagination-container">
                    <div class="pagination">
                        <c:if test="${tag > 1}">
                            <a class="page" href="historypayment?index=${tag - 1}">Previous</a>
                        </c:if>
                        <c:forEach begin="1" end="${endPage}" var="i">
                            <c:choose>
                                <c:when test="${i == tag}">
                                    <span class="page active">${i}</span>
                                </c:when>
                                <c:when test="${i <= 5 || i >= tag - 1 && i <= tag + 1 || i >= endPage - 4}">
                                    <a class="page" href="historypayment?index=${i}">${i}</a>
                                </c:when>
                                <c:otherwise>
                                    <span class="page">...</span>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${tag < endPage}">
                            <a class="page" href="historypayment?index=${tag + 1}">Next</a>
                        </c:if>
                    </div>
                </div>
                <div class="redirect" >
                    <form method="get" action="historypayment">
                        <span class="go-to">Go to page:</span>
                        <input type="number" name="index" id="pageInput" oninput="limit(this)" />
                        <input type="submit" value="Go" />
                    </form>
                </div>
            </div>
        </div>
        <div style="clear:both;"></div>
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
        <script>
            function chooseNavi(button) {
                // Remove styling of all buttons
                var buttons = document.querySelectorAll('.choose button');
                buttons.forEach(function (btn) {
                    btn.style.color = 'black';
                    btn.style.fontWeight = 'normal';
                    btn.style.borderBottom = '2px solid transparent';
                });

                // Apply the styling to the clicked button
                button.style.color = 'black';
                button.style.fontWeight = 'bold';
                button.style.borderBottom = '2px solid black';
            }

            chooseNavi(document.querySelector('.choose button'));

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
            function admin() {
                window.location.href = './addashboard';
            }

            function content() {
                window.location.href = './cmhome';
            }

            function lecturer() {
                window.location.href = './lecturerhome';
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
