<%-- 
    Document   : userprofile
    Created on : Oct 2, 2023, 3:42:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link rel="stylesheet" href="css/style.css">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>

        <link rel="icon" href="images/logo.png">
        <!-- Custom styles for this template -->
        <link href="assets/jumbotron-narrow.css" rel="stylesheet">
        <link rel="stylesheet" href="css/userprofile.css">
        <style>
            .btn:hover {
                cursor: pointer;
                background-color: #333
            }

            .btn {
                background-color: #101010;
                color: white
            }
        </style>
    </head>
    <body>
        <div class="contain-navigation">

            <!-------------------- Navbar and sidebar -------------------->
            <nav class="navigation-bar">
                <div class="logo">
                    <img src="images/logo.png" alt="logo">
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

            <!-------------------- Profile -------------------->
            <div class="contain-profile" style="width: 70%; padding-left:100px; height: 100vh">
                <div class="text-center" style="margin-bottom: 50px">
                    <h2 style="font-weight: bold">User Wallet</h2>
                </div>
                <div style="border: 1px solid; padding: 1rem; margin-bottom: 80px">
                    <p><strong>Full Name:</strong> ${sessionScope.user.getFullName()}</p>
                    <p><strong>Current Money in Wallet:</strong> ${String.format("%,.0f",sessionScope.user.getWallet())} vnd</p>

                </div>
                <div class="text-center" style="margin-bottom: 20px">
                    <h2 style="font-weight: bold">Add Money To Wallet</h2>
                </div>
                <div class="table-responsive" style="width: 100%;">
                    <form action="payment" id="frmCreateOrder" method="post">  
                        <input type="hidden" name="info" value="Add money to wallet">
                        <div class="form-group" style="margin-bottom: 15px">
                            <label for="amount">Amount: </label>                      
                            <input style="width: 100%; padding: 0.5rem" class="form-control" data-val="true" pattern="[0-9]+" title="The field Amount must be a number." 
                                   id="amount" name="amount" type="number" onchange="checkAmount()"/>
                        </div>
                        <h4 style="font-size: 20px; margin: 1rem 0; color: #777">Choose payment method</h4>
                        <div class="form-group" style="margin-bottom: 20px">
                            <h5 style="margin: 10px 0; margin-bottom: 15px"> Redirect to VNPAY Portal to choose payment method</h5>
                            <input type="radio" Checked="True" id="bankCode" name="bankCode" value="VNBANK">
                            <label for="bankCode">Payment portal VNPAYQR</label><br>
                        </div>
                        <div class="form-group" style="margin-bottom: 20px">
                            <h5 style="margin-bottom: 15px">Select payment interface language:</h5>
                            <input type="radio" id="language" Checked="True" name="language" value="vn">
                            <label for="language">Vietnamese</label><br>
                            <input type="radio" id="language" name="language" value="en">
                            <label for="language">English</label><br>

                        </div>
                        <input id="btn" style="padding: 0.8rem" type="submit" class="btn btn-default" value="Send Payment">
                    </form>
                </div>
            </div>


        </div>

        <div style="clear: both;"></div>


        <!-------------------- Footer -------------------->
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

            function admin() {
                window.location.href = './addashboard';
            }

            function content() {
                window.location.href = './cmhome';
            }

            function lecturer() {
                window.location.href = './lecturerhome';
            }
            const amountInput = document.getElementById('amount');
            function checkAmount() {
                let amount = amountInput.value;
                if (amount > 100000000) {
                    amountInput.value = '100000000';
                }
                if (amount < 5000) {
                    amountInput.value = '5000';
                }
            }

            amountInput.addEventListener("focus", function () {
                document.getElementById('btn').disabled = true;
            });
            
            // Add a blur event listener to the input element
            amountInput.addEventListener("blur", function () {
               setTimeout(handleBlur, 1000);
            });
            
            function handleBlur() {
                document.getElementById('btn').disabled = false;
            }

        </script>

    </body>
</html>
