<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Required meta tags-->
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Title Page-->
        <title>Pending Course Detail</title>

        <!-- Font special for pages-->
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i,800,800i" rel="stylesheet">

        <!-- Main CSS-->
        <link href="css/bancoursedetail.css" rel="stylesheet" media="all">
    </head>

    <body>
        <div class="page-wrapper bg-dark p-t-100 p-b-50">
            <div class="wrapper wrapper--w900">
                <div class="card card-6">
                    <div class="card-heading">
                        <h2 class="title">Course Detail</h2>
                    </div>
                    <div class="card-body">
                        <form action="verify" method="POST">
                            <c:forEach items="${course}" var="courseEntry">
                                <c:set var="courses" value="${courseEntry.key}" />
                                <c:set var="ratingData" value="${courseEntry.value}" />
                                <div class="form-row">
                                    <div class="name">Course ID:</div>
                                    <div class="value">
                                        <input class="input--style-6" type="text" name="cid" readonly value="${courses.getCourseId()}">
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="name">Course Name:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" name="courseName" readonly value="${courses.getCourseName()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="name">Lecturer Name:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" name="lecturerName" readonly value="${courses.getAuthor().getFullName()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="name">Lecturer Email:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" name="lecturerEmail" readonly value="${courses.getAuthor().getEmail()}">
                                        </div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="name">Course Image Link:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" name="courseImg" readonly  value="${courses.getImage()}">
                                        </div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="name">Course Description:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" name="courseDescription" readonly  value="${courses.getDescription()}">
                                        </div>
                                    </div>
                                </div>        

                                <div class="form-row">
                                    <div class="name">Detail Description:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <textarea class="input--style-6" readonly  name="longDescription" style="height: 500px;">${courses.getLongDescription()}</textarea>
                                        </div>
                                    </div>
                                </div>


                                <div class="form-row">
                                    <div class="name">Price: </div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" readonly  name="price" value="${courses.getPrice()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="name">Discount:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <input class="input--style-6" type="text" readonly  name="discount" value="${courses.getDiscount()}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="name">Level:</div>
                                    <select name="level" id="level" disabled>
                                        <c:choose>
                                            <c:when test="${courses.getLevel() eq 'Beginner'}">
                                                <option value="Beginner" selected="selected">Beginner</option>
                                                <option value="Intermediate">Intermediate</option>
                                                <option value="Advanced">Advanced</option>
                                            </c:when>
                                            <c:when test="${courses.getLevel() eq 'Intermediate'}">
                                                <option value="Beginner">Beginner</option>
                                                <option value="Intermediate" selected="selected">Intermediate</option>
                                                <option value="Advanced">Advanced</option>
                                            </c:when>
                                            <c:when test="${courses.getLevel() eq 'Advanced'}">
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
                                    <c:forEach var="ratingEntry" items="${ratingData}">
                                        <c:set var="rating" value="${ratingEntry.key}"/>
                                        <div class="name">Average rating:</div>
                                        <div class="value">
                                            <div class="input-group">
                                                <input class="input--style-6" type="text" name="avgRate" readonly value="${String.format("%.2f", rating)}">
                                            </div> 
                                            <span style="margin:10px;"></span>
                                        </div>
                                        <div class="name">Number of Rates:</div>
                                        <div class="value">
                                            <div class="input-group">
                                                <input class="input--style-6" type="text" name="numberOfRates" readonly value="${ratingEntry.value}">
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="form-row">
                                    <div class="name">Status:</div>
                                    <div class="value">
                                        <div class="input-group" style="margin-top: 11px;">
                                            <c:if test="${courses.getStatus() == 2}">
                                                <input class="status-input status-active" type="text" value="Pending" readonly>
                                            </c:if>
                                            <c:if test="${courses.getStatus() == 0}">
                                                <input class="status-input status-banned" type="text" value="" readonly>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-row">
                                    <div class="name">Message:</div>
                                    <div class="value">
                                        <div class="input-group">
                                            <textarea class="textarea--style-6" name="message" placeholder="Message sent to the lecturer" required></textarea>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <!--Section part-->
                            <div class="form-row">
                                <c:choose>
                                    <c:when test="${not empty nullMSG}">
                                        <div class="name">${nullMSG}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="courseSection" items="${listSection}" varStatus="loopStatus">
                                            <div class="name">Title of Section ${loopStatus.index + 1}:</div>
                                            <div class="value">
                                                <div class="input-group">
                                                    <input class="input--style-6" type="text" name="sectionTitle" readonly value="${courseSection.getTitle()}">
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
                                                    <textarea class="input--style-6 sectionArea" type="text" name="sectionDescription" style="height:500px;" readonly>${courseSection.getDescription()}</textarea>
                                                </div>
                                                <span style="margin:15px;"></span>
                                            </div>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!--Tag part-->
                            <div class="form-row">
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
                                                    <input class="input--style-6" type="text" readonly name="tagName" value="${courseTag.getTagName()}">
                                                </div>
                                                <span style="margin:2px;"></span>
                                            </div>    
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </div>                        

                            <div class="card-footer">
                                <input class="btnMode" type="radio" name="statusBtn" value="1" style="display: none" checked>
                                <input class="btnMode" type="radio" name="statusBtn" value="4" style="display: none">
                                <button class="btn btn--radius-2 btn--blue-2" type="submit" onclick="changeButton(0)">Approve</button>
                                <span style="margin: 0 10px;"></span>
                                <button class="btn btn--radius-2 btn--blue-2" type="submit" onclick="changeButton(1)">Reject</button>
                                <span style="margin: 0 10px;"></span>
                                <a href="pendingcourse" class="button-link">Back to home</a>
                            </div>
                        </form>
                        <script>
                            function changeButton(status) {
                                let btn = document.getElementsByClassName('btnMode');
                                btn[status].checked = true;
                            }
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

