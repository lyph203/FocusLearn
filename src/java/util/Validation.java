/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import dao.CourseDAO;
import dao.UserDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Admin
 */
public class Validation {

    public String validateSignUpInput(String username, String password, String repass, String email) {
        String errorMsg = "";
        UserDAO user = new UserDAO();
        // Check if username,email existed in the database or violating rules and password violating rules or not match
        if (user.checkExistedUserWithUsername(username)) {
            errorMsg = "Username is already existed, please use another username.";
        } else if (!validateUsername(username)) {
            errorMsg = "Username must contains at least 6 non-special characters, please use another username.";
        } else if (!password.equals(repass)) {
            errorMsg = "Password re-enter is not match";
        } else if (!validatePassword(password)) {
            errorMsg = "Password must contains at least 6 characters";
        } else if (user.checkExistedEmail(email)) {
            errorMsg = "Email is already used, please use another email.";
        } else if (!validateEmail(email)) {
            errorMsg = "Email is not valid, please a valid email.";
        }
        return errorMsg;
    }

    public boolean validateUsername(String username) {
        // Define the regular expression pattern
        String pattern = "^[a-zA-Z0-9_]{6,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(username);
        return matcher.find();
    }

    public boolean validateEmail(String email) {
        // Define the regular expression pattern
        String regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        // Check if the username has at least non-space 6 characters
        return password.trim().length() >= 6;
    }

    public String validateCourseInformationInput(String name,String price) {
        String errorMsg = "";
        CourseDAO courseDAO= new CourseDAO();
        try {
            float priceValue = Float.parseFloat(price);
            if (priceValue <= 0) {
                errorMsg = "Price must be a positive number.";
            }else if (courseDAO.isDuplicateCourseName(name)){
                errorMsg = "Course name is existed. Choose another name.";
            }
        } catch (NumberFormatException e) {
            errorMsg = "Invalid numeric format for price.";
        }
        return errorMsg;
    }
    
    public void checkExistedGeneratedPass(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String generatedPass = null;
        long timestamp = 0;
        if(session.getAttribute("generatedPass") != null){
            generatedPass = (String) session.getAttribute("generatedValue");
            timestamp = (Long) session.getAttribute("generatedPassTimestamp");
        }
        
        
        
        long currentTime = System.currentTimeMillis();
        int customTimeoutInSeconds = 180;
        if (generatedPass != null && (currentTime - timestamp) > (customTimeoutInSeconds * 1000)) {
            // The object has expired, take appropriate action
            session.removeAttribute("generatedPass");
            session.removeAttribute("generatedPassTimestamp");
        }
    }
    
    public void checkExistedGeneratedValue(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String generatedValue = null;
        long timestamp = 0;
        if(session.getAttribute("generatedValue") != null){
            generatedValue = (String) session.getAttribute("generatedValue");
            timestamp = (Long) session.getAttribute("generatedValueTimestamp");
        }
        
        long currentTime = System.currentTimeMillis();
        int customTimeoutInSeconds = 180;
        if (generatedValue != null && (currentTime - timestamp) > (customTimeoutInSeconds * 1000)) {
            // The object has expired, take appropriate action
            session.removeAttribute("generatedValue");
            session.removeAttribute("generatedValueTimestamp");
        }
    }
    
    private boolean validateFullname(String fullname) {
        String pattern = "^[a-zA-Z].*[\\s\\.]*$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(fullname);
        return matcher.find();
    }

    public String validateCreateLecturerAccountInput(String username, String fullname, String email) {
        String errorMsg = "";
        UserDAO user = new UserDAO();
        // Check if username,email existed in the database or violating rules and password violating rules or not match
        if (user.checkExistedUserWithUsername(username)) {
            errorMsg = "Username is already existed, please use another username.";
        } else if (!validateUsername(username)) {
            errorMsg = "Username must contains at least 6 non-special characters, please use another username.";
        } else if (!validateFullname(fullname)) {
            errorMsg = "Fullname must contains at least 6 alphabetical characters, please re-enter fullname.";
        } else if (user.checkExistedEmail(email)) {
            errorMsg = "Email is already used, please use another email.";
        } else if (!validateEmail(email)) {
            errorMsg = "Email is not valid, please a valid email.";
        }
        return errorMsg;
    }
}
