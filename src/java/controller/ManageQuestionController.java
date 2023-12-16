/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.QuestionDAO;
import dao.TagDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import model.Question;
import model.Tag;

/**
 *
 * @author Pham Huong Ly
 */
@WebServlet(name = "ManageQuestionController", urlPatterns = {"/viewquestion"})
public class ManageQuestionController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TagDAO tagDAO = new TagDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        String indexPage = req.getParameter("index");
        Map<String, String[]> parameterMap = req.getParameterMap();

        //get list tag
        ArrayList<Tag> listTags = tagDAO.getListTags();
        
        //Get search content
        String searchContent = req.getParameter("searchContent");
        //check null value
        if(searchContent == null || searchContent.isEmpty()) {
            searchContent = "";
        }

        //Check for null value of indexPage
        if (indexPage == null) {
            indexPage = 1 + "";
        }
        int index = Integer.parseInt(indexPage);
        //Define total questions

        int totalQuestion = questionDAO.countAvailableQuestion(searchContent);
        if (parameterMap.get("tagsID") != null) {
            totalQuestion = questionDAO.countAvailableFilterQuestion(parameterMap, searchContent);
        }
        //Define number of questions display per page
        int questionPerPage = 6;
        //Find the index of last page
        int endPage = totalQuestion / questionPerPage;
        //Increase 1 page
        if (totalQuestion % questionPerPage != 0) {
            endPage++;
        }
        //Check if index user enterred over total pages
        if (index > endPage || index == 0) {
            String errorMsg = "Can not found the page you want to view.";
            req.setAttribute("errorMsg", errorMsg);
        } else {
            ArrayList<Question> listPagedQuestion;
            String[] tagsID = parameterMap.get("tagsID");

            //get list if use filter
            if (tagsID != null) {
                listPagedQuestion = questionDAO.getListPagingFilterQuestion(index, questionPerPage, parameterMap, searchContent);
            } else {
                //List question paged by index
                listPagedQuestion = questionDAO.getListPagingQuestion(index, questionPerPage, searchContent);
            }

            //Set data attribute
            req.setAttribute("tagsID", tagsID);
            req.setAttribute("tag", index);
            req.setAttribute("endPage", endPage);
            req.setAttribute("totalQuestion", totalQuestion);
            req.setAttribute("searchContent", searchContent);
            req.setAttribute("listPagedQuestion", listPagedQuestion);
        }
        req.setAttribute("listTags", listTags);
        req.getRequestDispatcher("allquestion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
