<%-- 
    Document   : updatecourse
    Created on : Oct 17, 2023, 11:12:56 PM
    Author     : khoa2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">
        <link href="css/bancoursedetail.css" rel="stylesheet" media="all">
        <link href="css/newcourse.css" rel="stylesheet" media="all">
        <title>Update Course</title>
        <style>
            #sectionFields{
                width: 100%;
            }
            #tagFields{
                width: 90%;
            }
        </style>
    </head>
    <body>

        <div class="page-wrapper bg-dark p-t-100 p-b-50">
            <div class="wrapper wrapper--w900">
                <div class="card card-6">
                    <div class="card-heading">
                        <h2 class="title">Course Detail</h2>
                    </div>
                    <div class="card-body">
                        <form action="updatecourse" method="POST">
                            <input class="input--style-6" type="hidden" name="cid" readonly value="${course.getCourseId()}">
                            <div class="form-row">
                                <div class="name">Course Name:</div>
                                <div class="value">
                                    <div class="input-group">
                                        <input class="input--style-6" type="text" required name="courseName" value="${course.getCourseName()}">
                                    </div>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="name">Course Image Link:</div>
                                <div class="value">
                                    <div class="input-group">
                                        <input class="input--style-6" type="text" required name="courseImg" value="${course.getImage()}">
                                    </div>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="name">Course Description:</div>
                                <div class="value">
                                    <div class="input-group">
                                        <input class="input--style-6" type="text" required name="courseDescription" value="${course.getDescription()}">
                                    </div>
                                </div>
                            </div>        

                            <div class="form-row">
                                <div class="name">Detail Description:</div>
                                <div class="value">
                                    <div class="input-group">
                                        <textarea class="input--style-6" required name="longDescription" style="height:500px;">${course.getLongDescription()}</textarea>
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="name">Price: </div>
                                <div class="value">
                                    <div class="input-group">
                                        <input class="input--style-6" type="text" required name="price" value="${course.getPrice()}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="name">Discount:</div>
                                <div class="value">
                                    <div class="input-group">
                                        <input class="input--style-6" type="text" required name="discount" value="${course.getDiscount()}">
                                    </div>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="name">Level:</div>
                                <select name="level" id="level">
                                    <c:choose>
                                        <c:when test="${course.getLevel() eq 'Beginner'}">
                                            <option value="Beginner" selected="selected">Beginner</option>
                                            <option value="Intermediate">Intermediate</option>
                                            <option value="Advanced">Advanced</option>
                                        </c:when>
                                        <c:when test="${course.getLevel() eq 'Intermediate'}">
                                            <option value="Beginner">Beginner</option>
                                            <option value="Intermediate" selected="selected">Intermediate</option>
                                            <option value="Advanced">Advanced</option>
                                        </c:when>
                                        <c:when test="${course.getLevel() eq 'Advanced'}">
                                            <option value="Beginner">Beginner</option>
                                            <option value="Intermediate">Intermediate</option>
                                            <option value="Advanced" selected="selected">Advanced</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="Beginner">Beginner</option>
                                            <option value="Intermediate">Intermediate</option>
                                            <option value="Advanced">Advanced</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                            <div class="form-row">
                                <div class="name">Status:</div>
                                <div class="value">
                                    <div class="input-group" style="margin-top: 11px;">
                                        <c:if test="${course.getStatus() == 1}">
                                            <input style="color: green;" name="status" value="Active">
                                        </c:if>
                                        <c:if test="${course.getStatus() == 0}">
                                            <input style="color: red;"  name="status" value="Banned">
                                        </c:if>
                                        <c:if test="${course.getStatus() == 2}">
                                            <input style="color: blue;"  name="status" value="Pending">
                                        </c:if>
                                        <c:if test="${course.getStatus() == 3}">
                                            <input style="color: grey;"  name="status" value="Draft Saved">
                                        </c:if>
                                        <c:if test="${course.getStatus() == 4}">
                                            <input style="color: red;"  name="status" value="Rejected">
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <!--Section part-->
                            <div class="form-row" id="sectionFields">
                                <c:choose>
                                    <c:when test="${not empty nullMSG}">
                                        <div class="name">${nullMSG}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="courseSection" items="${listSection}" varStatus="loopStatus">
                                            <input type="hidden" name="sectionID" value="${courseSection.getSectionId()}">
                                            <div class="name">Title of Section ${loopStatus.index + 1}:</div>
                                            <div class="value">
                                                <div class="input-group">
                                                    <input class="input--style-6" type="text" name="sectionTitle" value="${courseSection.getTitle()}">
                                                </div>
                                                <span style="margin:2px;"></span>
                                            </div>    
                                            <div class="name">Video link:</div>
                                            <div class="value">
                                                <div class="input-group">
                                                    <input type="hidden" name="sectionVideo" value="${courseSection.getVideo()}">
                                                    <div class="link">
                                                        <a href="${courseSection.getVideo()}" target="_blank">
                                                            ${courseSection.getVideo()} 
                                                        </a>
                                                    </div>
                                                </div>
                                                <span style="margin:2px;"></span>
                                            </div>
                                            <div class="name">Section Description:</div>
                                            <div class="value">
                                                <div class="input-group">
                                                    <textarea class="input--style-6 sectionArea" type="text" name="sectionDescription" style="height:500px;">${courseSection.getDescription()}</textarea>
                                                </div>
                                                <span style="margin:15px;"></span>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                <button type="button" id="addSectionButton">Add New Section</button>            
                            </div>
                            <!--Tag part-->
                            <div class="form-row" id="tagFields">
                                <c:choose>
                                    <c:when test="${not empty tagNullMSG}">
                                        <div class="name">${tagNullMSG}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="courseTag" items="${listTag}" varStatus="loopStatus">
                                            <input type="hidden" name="tagID" value="${courseTag.getTagId()}">
                                            <div class="name">Tag Name ${loopStatus.index + 1}:</div>
                                            <div class="value">
                                                <div class="input-group">
                                                    <input class="input--style-6" type="text" name="tagName" value="${courseTag.getTagName()}" readonly>
                                                    <a id="deleteButton" href="deletetag?did=${courseTag.getTagId()}&courseID=${course.getCourseId()}&modeD=1">Delete tag</a>
                                                </div>
                                                <span style="margin:2px;"></span>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                                <button type="button" id="addTagButton">Add New Tag</button>
                            </div>

                            <div class="card-footer">
                                <button class="btn btn--radius-2 btn--blue-2" type="submit" name="save" onclick="changeButton(0)">Save Draft</button>
                                <input class="btnMode" type="radio" name="mode" value="3" style="display: none" checked>
                                <input class="btnMode" type="radio" name="mode" value="2" style="display: none">
                                <span style="margin: 0 10px;"></span>
                                <button class="btn btn--radius-2 btn--green" type="submit" name="update" onclick="changeButton(1)">Send Pending Request</button>
                                <span style="margin: 0 10px;"></span>
                                <a href="lecturerhome" class="button-link">Back to home</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script>
            // Function to add a new section
            function addSection() {
                const sectionFields = document.getElementById('sectionFields');
                const newSection = document.createElement('div');
                newSection.classList.add('sectionfield', 'form-row');
                newSection.innerHTML = `
                <input type="hidden" name="sectionID" value="add">
            <div class="name">Title of Section:</div>
            <div class="value">
                <div class="input-group">
                    <input class="input--style-6" type="text" name="sectionTitle">
                </div>
                <span style="margin:2px;"></span>
            </div>
            <div class="name">Video link:</div>
            <div class="value">
                <div class="input-group">
                    <input class="input--style-6" type="text" name="sectionVideo">
                </div>
                <span style="margin:2px;"></span>
            </div>
            <div class="name">Section Description:</div>
            <div class="value">
                <div class="input-group">
                    <input class="input--style-6" type="text" name="sectionDescription">
                </div>
                <span style="margin:15px;"></span>
            </div>
        `;
                sectionFields.appendChild(newSection);
            }
            document.getElementById('addSectionButton').addEventListener('click', addSection);

            // Function to add a new tag
            function addTag() {
                const tagFields = document.getElementById('tagFields');
                const newTag = document.createElement('div');
                newTag.classList.add('tagfield', 'form-row');
                newTag.innerHTML = `
                    <input type="hidden" name="tagID" value="add">
                        <div class="name">Tag Name:</div>
                        <div class="value">
                            <div class="input-group">
                                <input class="input--style-6" type="text" name="tagName">
                            </div>
                        <span style="margin:2px;"></span>
                        </div> 
        `;
                tagFields.appendChild(newTag);
            }
            document.getElementById('addTagButton').addEventListener('click', addTag);

        </script>

        <script>

            function changeButton(mode) {
                let btn = document.getElementsByClassName('btnMode');
                btn[mode].checked = true;
            }
            // Get references to the button and the textarea
            const toggleButton = document.getElementById("toggleButton");
            const textarea = document.querySelector(".sectionArea");

            let isTextAreaVisible = false;

            function toggleVisibility() {
                isTextAreaVisible = !isTextAreaVisible;
                if (isTextAreaVisible) {
                    textarea.style.display = "none";
                    toggleButton.textContent = "View Detail";
                } else {
                    textarea.style.display = "block";
                    toggleButton.textContent = "Hide Detail";
                }
            }

            toggleButton.addEventListener("click", toggleVisibility);

        </script>
        <script>
            const successMsg = "${successMsg}";
            if (successMsg.trim() !== "" && successMsg.includes("success")) {
                const confirmation = confirm(successMsg);
                if (confirmation) {
                    // If OK is clicked, redirect to /lecturerhome
                    window.location.href = "./lecturerhome";
                }
            }else if (successMsg.trim() !== ""){
                const confirmation = confirm(successMsg);
                if (confirmation) {
                    window.location.href = "./updatecourse?cid=${course.getCourseId()}";
                }
            }
        </script>

    </body>
</html>
