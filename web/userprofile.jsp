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
        <link rel="stylesheet" href="css/userprofile.css">
        <link rel="icon" href="images/logo.png">
        <style>
            .loader {
                position: absolute;
                right: 0;
                left: 0;
                bottom: 0;
                top: 0;
                background-color: rgba(0,0,0,.65);
                backdrop-filter: blur(5px);
                justify-content: center;
                align-items: center;
                display: none;
            }

            .loader:after {
                content: "";
                height: 48px;
                width: 48px;
                display: block;
                border: 2px solid white;
                border-radius: 48px;
                border-right-color: transparent;
                animation: infinite spinner .5s linear;
            }

            @keyframes spinner {
                0% {
                    transform: rotate(0deg);
                }
                100% {
                    transform: rotate(360deg);
                }
            }
        </style>
    </head>
    <body>

        <div id="loader" class="loader"></div>

        <div class="contain-navigation" style="z-index: 1">

            <!-------------------- Navbar and sidebar -------------------->
            <nav class="navigation-bar" >
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
            <div class="contain-profile">
                <div id="avatar">
                    <div id="background">
                        <img src="${sessionScope.user.getImg()}" id="image-preview">
                        <h2 style="margin: 20px 0px;">${sessionScope.user.getFullName()}</h2>
                        <input type="button" name="updated_img" value="Upload Image" id="update-btn">
                        <input type="file" id="image-upload" accept="image/*" style="display: none;">
                        <span style="color: red">${err}</span>
                    </div>
                </div>               
                <div class="contain-form">
                    <form id="profile" action="profile" method="post" enctype="multipart/form-data">
                        <input type="hidden" id="imgUrl" name="imgUrl" value="${sessionScope.user.getImg()}" >
                        <table>
                            <tr class="input-field">
                                <td>Fullname: </td>
                                <td><input type="text" value="${sessionScope.user.getFullName()}" name="fullname" required></td>
                                <td>Email: </td>
                                <td><input type="text" value="${sessionScope.user.getEmail()}" name="email" readonly></td>
                            </tr>
                            <tr class="input-field">
                                <td>Dob: </td>
                                <td><input type="date" value="${sessionScope.user.getDob()}" name="dob"></td>
                                <td>Gender: </td>
                                <td>
                                    <div class="gender">
                                        <c:choose>
                                            <c:when test="${sessionScope.user.getGender() == 1}">
                                                <input type="radio" name="gender" checked value="1"> Male
                                                <input type="radio" name="gender" value="0"> Female                                            
                                            </c:when>
                                            <c:when test="${sessionScope.user.getGender() == 0}">
                                                <input type="radio" name="gender" value="1"> Male
                                                <input type="radio" name="gender" checked value="0"> Female                                            
                                            </c:when>    
                                        </c:choose>
                                    </div>
                                </td>
                            </tr>
                            <c:if test="${sessionScope.user.getRole() eq 2 or sessionScope.user.getRole() eq 3}">
                                <tr class="input-field">
                                    <td>My Wallet:</td>
                                    <td><input type="text" name="name" readonly value="${String.format("%,.0f",sessionScope.user.getWallet())} vnd"></td>
                                </tr>
                            </c:if>

                            <tr class="input-field">
                                <td>Description: </td>
                                <td colspan="3">
                                    <textarea style="width: 100%;" name="description" id="myTextarea" rows="10" placeholder="Say something about yourself ...">${sessionScope.user.getDescription()}</textarea>
                                </td>
                            </tr>
                            <tr class="submit-btn">
                                <td style="text-align: right; padding-right: 10px;" colspan="2"><input type="submit" value="Save Changes"></td>
                                <td style="padding-left: 10px;" colspan="2"><input type="button" value="Change password" onclick="changePass()"></td>
                            </tr>
                        </table>
                    </form>

                    <!-------------------- Change Password -------------------->
                    <form action="changepass" style="display: none;" id="changepass">
                        <table style="margin: auto;">
                            <tr>
                                <td colspan="2" id="message" style="text-align: center; color: ${message.equals("Change password successfully") ? 'green' : 'red'}">
                                    ${message}
                                </td>
                            </tr>
                            <tr class="input-field">
                                <td>Old password: </td>
                                <td><input name="oldpass" type="password"></td>
                            </tr>
                            <tr class="input-field">
                                <td>New password: </td>
                                <td><input name="newpass" type="password"></td>
                            </tr>
                            <tr class="input-field">
                                <td>Re-new password: </td>
                                <td><input name="renewpass" type="password"></td>
                            </tr>
                            <tr class="submit-btn">
                                <td style="text-align: center;" colspan="2">
                                    <input type="submit" value="Save Changes" style="margin-right: 20px;">
                                    <input type="reset" value="Reset">
                                </td>
                            </tr>
                        </table>
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

            const changePassForm = document.getElementById('changepass');
            const profileForm = document.getElementById('profile');

            if (document.getElementById('message').innerHTML.trim() !== '') {
                changePass();
            }

            function changePass() {
                changePassForm.style.display = 'block';
                profileForm.style.display = 'none';
            }

            function home() {
                window.location.href = './home';
            }
            function my_courses() {
                window.location.href = './mycourses';
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
        </script>

        <script>
            const textarea = document.getElementById("myTextarea");
            var text = textarea.value.replace(/\s+/g, ' ').trim();
            textarea.value = text;
        </script>

        <script>
            // JavaScript to control image update
            const chooseImageButton = document.getElementById('update-btn');
            const imageUpload = document.getElementById('image-upload');
            const imagePreview = document.getElementById('image-preview');
            const imgUrl = document.getElementById('imgUrl');
            
            chooseImageButton.addEventListener('click', function () {
                imageUpload.click();
            });

            var data = {
                key: '9d43938508e0396449ee020fde6f3b19',
                imageUrl: ''
            };

            imageUpload.addEventListener('change', function () {
                const file = imageUpload.files[0];
                if (file) {
                    const reader = new FileReader();

                    reader.onload = function (e) {
                        imagePreview.src = e.target.result;
                    };

                    reader.readAsDataURL(file);
                    const formData = new FormData();
                    formData.append('image', file);
                    document.getElementById('loader').style.display = 'flex';
                    // Make body overflow hidden so it's not scrollable
                    document.documentElement.style.overflow = 'hidden';
                    fetch('https://api.imgbb.com/1/upload?key=' + data.key, {
                        method: 'POST',
                        body: formData
                    })
                            .then(response => response.json())
                            .then(result => {
                                const imageUrl = result.data.url;
                                console.log('Uploaded image URL:', imageUrl);
                                data.imageUrl = imageUrl;
                                imgUrl.value = imageUrl;
                                // Remove body overflow hidden
                                document.documentElement.style.overflow = '';
                                //remove loading effect
                                document.getElementById('loader').style.display = 'none';
                                // Update the data object with the new image URL.

                            })
                            .catch(error => {
                                console.error('Error uploading image:', error);
                                document.documentElement.style.overflow = '';
                                //remove loading effect
                                document.getElementById('loader').style.display = 'none';
                                // Update the data object with the new image URL.
                            });
                }
            });


        </script>
    </body>
</html>
