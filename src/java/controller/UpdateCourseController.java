/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CourseDAO;
import dao.CourseSectionDAO;
import dao.TagDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.CourseSection;
import model.Tag;

/**
 *
 * @author khoa2
 */
@WebServlet(name = "UpdateCourseController", urlPatterns = {"/updatecourse"})
public class UpdateCourseController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateCourseController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateCourseController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CourseDAO courseDAO = new CourseDAO();
        Course course;
        String courseNameAF = (String) request.getAttribute("courseNameAF");
        String did = (String) request.getAttribute("did");
        String courseIDDelete = (String) request.getAttribute("courseIDDelete");
        //Check if course is added
        if (courseNameAF != null) {
            // Get course by name
            course = courseDAO.getCourseByName(courseNameAF);
        } else if (did != null) {
            course = courseDAO.getCourseByID(Integer.parseInt(courseIDDelete));
            int intCourseID = course.getCourseId();
            //Get course sections information
            CourseSectionDAO csDAO = new CourseSectionDAO();
            List<CourseSection> listSection = csDAO.getListSectionByCourseID(intCourseID);
            //Check if empty section of course
            if (listSection.isEmpty()) {
                String nullMSG = "No section available";
                request.setAttribute("nullMSG", nullMSG);
            } else {
                request.setAttribute("listSection", listSection);
            }
            //Get list course tag
            TagDAO tagDAO = new TagDAO();
            course.setListTag(tagDAO.getListTagByCourseID(intCourseID));
            //Check if empty tag of course
            if (course.getListTag().isEmpty()) {
                String tagNullMSG = "No tag added";
                request.setAttribute("tagNullMSG", tagNullMSG);
            } else {
                request.setAttribute("listTag", course.getListTag());
            }
        } else {
            String courseID = request.getParameter("cid");
            //Get course information
            int intCourseID = Integer.parseInt(courseID);
            course = courseDAO.getCourseByID(intCourseID);
            //Get course sections information
            CourseSectionDAO csDAO = new CourseSectionDAO();
            List<CourseSection> listSection = csDAO.getListSectionByCourseID(intCourseID);
            //Check if empty section of course
            if (listSection.isEmpty()) {
                String nullMSG = "No section available";
                request.setAttribute("nullMSG", nullMSG);
            } else {
                request.setAttribute("listSection", listSection);
            }
            //Get list course tag
            TagDAO tagDAO = new TagDAO();
            course.setListTag(tagDAO.getListTagByCourseID(intCourseID));
            //Check if empty tag of course
            if (course.getListTag().isEmpty()) {
                String tagNullMSG = "No tag added";
                request.setAttribute("tagNullMSG", tagNullMSG);
            } else {
                request.setAttribute("listTag", course.getListTag());
            }
        }
        //Set data attribute to send to jsp
        request.setAttribute("course", course);
        request.getRequestDispatcher("updatecourse.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int courseId = 0;
        Course course = new Course();
        CourseSectionDAO sectionDAO = new CourseSectionDAO();
        CourseDAO courseDAO = new CourseDAO();
        String successMsg = "";
        //Get button status mode
        String mode = request.getParameter("mode");
        // Get course ID if new course is added (in alternative flow from add -> update)
        String courseNameAF = (String) request.getAttribute("nameAdded");
        String did = (String) request.getAttribute("did");
        String courseIDDelete = (String) request.getAttribute("courseIDDelete");
        //Flow 1: Added Course -> Go to Update
        if (courseNameAF != null) {
            course = courseDAO.getCourseByName(request.getParameter("nameAdded"));
            request.setAttribute("courseNameAF", courseNameAF);
        } //Flow 2: Delete Tag -> Go to Update
        else if (did != null) {
            course = courseDAO.getCourseByID(Integer.parseInt(request.getParameter("courseIDDelete")));
            successMsg = "Delete tag successfully";
        } else {
            courseId = Integer.parseInt(request.getParameter("cid"));
            // Get form fields
            String courseName = request.getParameter("courseName");
            String imageUrl = request.getParameter("courseImg");
            String shortDesc = request.getParameter("courseDescription");
            String longDesc = request.getParameter("longDescription");
            double price = Double.parseDouble(request.getParameter("price"));
            int discount = Integer.parseInt(request.getParameter("discount"));
            String level = request.getParameter("level");
            // Course and Section objects
            course.setCourseId(courseId);
            course.setCourseName(courseName);
            course.setImage(imageUrl);
            course.setDescription(shortDesc);
            course.setLevel(level);
            course.setDiscount(discount);
            course.setPrice(price);
            course.setLongDescription(longDesc);
            successMsg = "Your course is updated successfully, click OK to go homepage";
        }
        List<CourseSection> sections = new ArrayList<>();
        TagDAO tagDAO = new TagDAO();
        //Get original values of Tag
        List<Tag> originalTags = tagDAO.getListTagByCourseID(courseId);
        int originalNumTags = originalTags.size();
        String[] originalTagNames = new String[originalNumTags];
        //Get original Tag Name
        for (int i = 0; i < originalNumTags; i++) {
            originalTagNames[i] = originalTags.get(i).getTagName();
        }
        // Get section titles, videos, descriptions
        String[] sectionID = request.getParameterValues("sectionID");
        String[] sectionTitles = request.getParameterValues("sectionTitle");
        String[] sectionVideos = request.getParameterValues("sectionVideo");
        String[] sectionDescs = request.getParameterValues("sectionDescription");
        //Get tag id, tag name
        String[] tagID = request.getParameterValues("tagID");
        String[] tagName = request.getParameterValues("tagName");
        int newNumTags = 0;
        if (tagName != null) {
            newNumTags = tagName.length;
        }
        //Check if section title is not null
        if (sectionTitles != null) {
            //Iterate through section array
            for (int i = 0; i < sectionTitles.length; i++) {
                //Check if all fields is not empty at the same time
                if (!sectionTitles[i].isEmpty() || !sectionVideos[i].isEmpty() || !sectionDescs[i].isEmpty()) {
                    CourseSection section = new CourseSection();
                    section.setTitle(sectionTitles[i]);
                    section.setVideo(sectionVideos[i]);
                    section.setDescription(sectionDescs[i]);
                    section.setCourse(course);
                    sections.add(section);
                    //Set update/add course section to DB
                    if (!sectionID[i].equals("add")) {
                        sectionDAO.updateSections(sections, sectionID[i]);
                    } else {
                        sectionDAO.addCourseSection(section);
                    }
                }
            }
        }
        //Check if TAG name is not null
        if (tagName != null) {
            //Iterate through added Tag array for add
            for (int i = originalNumTags; i < newNumTags; i++) {
                //Check if tag name is not null
                if (!tagName[i].isEmpty()) {
                    //Check if tag name is not exist in database
                    if (tagDAO.checkExistTagName(tagName[i])) {
                        //Check if course has not contain tag name
                        if (courseDAO.hasTag(courseId, tagName[i])) {
                            successMsg = "Update fail, tag name is included";
                            break;
                        } else {
                            // Get existing tag ID  
                            int tagId = tagDAO.getTagIdByName(tagName[i]);
                            // Add course tag only
                            courseDAO.addCourseTag(courseId, tagId);
                        }
                    } else {
                        Tag tag = new Tag();
                        tag.setTagName(tagName[i]);
                        //Set update/add course tag to DB
                        if (tagID[i].equals("add")) {
                            tagDAO.addTag(tag);
                            courseDAO.addCourseTag(courseId, tagDAO.getTagIdByName(tagName[i]));
                        }
                    }
                }
            }
            //Iterate through Tag array for update 
            for (int i = 0; i < originalNumTags; i++) {
                //Check for update all new tags added
                if (!originalTagNames[i].equals(tagName[i]) && !tagID[i].equals("add")) {
                    Tag updatedTag = new Tag();
                    updatedTag.setTagName(tagName[i]);
                    updatedTag.setTagId(originalTags.get(i).getTagId());
                    tagDAO.updateTag(updatedTag);
                }
            }
        }
        // Save course to DB
        //Mode 3: Save draft
        //Mode 2: Send pending request if update course successfully
        if (successMsg.contains("success")) {
            courseDAO.updateCourse(course, mode);
        }
        //Set attribute 
        request.setAttribute("successMsg", successMsg);
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
