/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Course;

/**
 *
 * @author nhatm
 */
public class EmailUtil {

    //Email: focuslearn927@gmail.com
    final String from = "focuslearn927@gmail.com";
    //Pass: sedwroyckpunaubh
    final String password = "sedwroyckpunaubh";

    //Send email from ... to ..., if type is signup then send sign up mail, type is forgotpass then send password reset mail
    public void sendEmail(String to, String type, String generatedValue) {
        String emailContent = "";
        PasswordUtil passUtil = new PasswordUtil();
        //Properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        };
        //Session
        Session session = Session.getInstance(props, auth);

        //Send email
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            //Sender
            msg.setFrom(from);

            //Receiver
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            if (type.equals("forgotpass")) {
                //Genpass
                //generatedValue = passUtil.generatePassword(); 
                //Subject
                msg.setSubject("Request to reset password ");

                //Content
                emailContent = "Hello user,\n\n"
                        + "** This is an automated message -- please do not reply as you will not receive a response. **\n\n"
                        + "This message is in response to your request to reset your account password. Please click the link below and follow the instructions to change your password.\n\n"
                        + "Your password is: " + generatedValue + "\n\n"
                        + "Note: Your temporary password is only available in 3 mins from the momment this email is sent!!\n\n"
                        + "http://localhost:9999/FocusLearn/login.jsp \n\n"
                        + "Thank you.\n\n"
                        + "FocusLearn.";
            } else if (type.equals("signup")) {
                //GenOTP
                //generatedValue = passUtil.generateOTP();
                //Subject
                msg.setSubject("Request signup ");
                //Content
                emailContent = "Hello user,\n\n"
                        + "** This is an automated message -- please do not reply as you will not receive a response. **\n\n"
                        + "This message is in response to your request to signup. Please enter the following OTP, remember to not share this with anyone.\n\n"
                        + "Your OTP is: " + generatedValue + "\n\n"
                        + "Note: YThis OTP Code is only available in 3 mins from the momment this email is sent!!\n\n"
                        + "http://localhost:9999/FocusLearn/login.jsp \n\n"
                        + "Thank you.\n\n"
                        + "FocusLearn.";
            } else if (type.equals("lecturerpass")) {
                //Subject
                msg.setSubject("Change Your Password");

                //Content
                emailContent = "Hello user,\n\n"
                        + "** This is an automated message -- please do not reply as you will not receive a response. **\n\n"
                        + "This message notice about your new creator account in our website. Please click the link below, use your username and follow the instructions to change your password.\n\n"
                        + "Your password is: " + generatedValue + "\n\n"
                        + "http://localhost:9999/FocusLearn/login.jsp \n\n"
                        + "Thank you.\n\n"
                        + "FocusLearn.";
            } else if (type.equals("Banned account")) {
                //Subject
                msg.setSubject("Deative account");
                //Content
                emailContent = "Hello user,\n\n"
                        + "** This is an automated message -- please do not reply as you will not receive a response. **\n\n"
                        + "This message notice about your account in our website has no longer active.\n\n"
                        + "Thank you.\n\n"
                        + "FocusLearn.";
            } else if (type.equals("UnBanned account")) {
                //Subject
                msg.setSubject("Avtive account");
                //Content
                emailContent = "Hello user,\n\n"
                        + "** This is an automated message -- please do not reply as you will not receive a response. **\n\n"
                        + "This message notice about your account in our website is active.\n\n"
                        + "Thank you.\n\n"
                        + "FocusLearn.";
            }
            msg.setText(emailContent, "UTF-8");
            //Send Email
            Transport.send(msg);
        } catch (Exception e) {
            System.out.println("sendEmail(): " + e.getMessage());
        }
    }

    //Send warning email from ... to ..., type is bancourse,unbancourse: notify course status
    public void sendWarningEmail(String to, String type, Course course, String reason, String avgRate, String numberOfRate) {
        String emailContent = "";
        //Properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        //Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }

        };
        //Session
        Session session = Session.getInstance(props, auth);

        //Send email
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");

            //Sender
            msg.setFrom(from);

            //Receiver
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            if (type.equals("unbancourse")) {
                //Genpass
                //generatedValue = passUtil.generatePassword(); 
                //Subject
                msg.setSubject("Course Unbanned on FocusLearn");

                //Content
                emailContent = "Dear Lecturer,\n\n"
                        + "I hope this message finds you well. We are writing to inform you that the course you were previously instructing  has been unbanned and is now available for students on our website.\n"
                        + "\n"
                        + "Course Name: " + course.getCourseName() + "\n"
                        + "Course ID: " + course.getCourseId() + "\n"
                        + "Average Rating: " + avgRate + "\n"
                        + "Number Of Rates: " + numberOfRate + "\n"
                        + "\n"
                        + "We understand that there may have been certain issues or concerns that led to the course's suspension. After a thorough review, we have resolved the matter, and your course is once again accessible to students.\n"
                        + "\n"
                        + "If you have any questions or require further assistance regarding your course, please do not hesitate to reach out to our support team.\n"
                        + "\n"
                        + "Thank you for being a part of our educational platform. We look forward to the continued success of your course and the positive learning experiences it offers to students.\n"
                        + "\n"
                        + "Best regards,\n"
                        + "FocusLearn";
            } else if (type.equals("bancourse")) {
                //Subject
                msg.setSubject("Course Suspension in FocusLearn");
                //Content
                emailContent = "Dear Lecturer,\n\n"
                        + "I hope this message finds you well. We regret to inform you that one of your course on our online platform has been banned due to a violation of our content guidelines and policies.\n"
                        + "\n"
                        + "Course Details:\n"
                        + "\n"
                        + "Course Name: " + course.getCourseName() + "\n"
                        + "Course ID: " + course.getCourseId() + "\n"
                        + "Reason for Banning:" + reason + "\n"
                        + "We understand that this may be disappointing news, and we would like to work with you to address the issue and resolve it as soon as possible. To ensure a swift resolution, please take the following steps:\n"
                        + "\n"
                        + "1. Review the course content to identify any elements that may have violated our guidelines.\n"
                        + "\n"
                        + "2. Make the necessary changes to the course content, removing any prohibited materials or addressing any policy violations.\n"
                        + "\n"
                        + "3. Once the required changes are made, please inform us by replying to this email.\n"
                        + "\n"
                        + "Our content review team will then reevaluate the course. If the issues have been addressed and the course aligns with our guidelines, we will lift the ban and reinstate the course.\n"
                        + "\n"
                        + "We value your contributions to our online platform and aim to maintain a high standard of quality and compliance. We appreciate your prompt attention to this matter and cooperation in resolving the issue.\n"
                        + "\n"
                        + "Thank you for your understanding.\n"
                        + "\n"
                        + "Best regards,\n"
                        + "FocusLearn";
            } else if (type.equals("reject")) {
                //Subject
                msg.setSubject("Pending Course Verified");
                //Content
                emailContent = "Dear Lecturer,\n"
                        + "\n"
                        + "We regret to inform you that your course, " + course.getCourseName()
                        + "has been reviewed and unfortunately did not meet our content guidelines. As a result, your course has been rejected.\n"
                        + "\n"
                        + "Here are the details of your course:\n"
                        + "- Course Name: " + course.getCourseName() + "\n"
                        + "- Verification Status: Rejected\n"
                        + "Reason for Rejection: " + reason + "\n"
                        + "\n"
                        + "We understand that this may be disappointing, but we encourage you to review our content guidelines and make the necessary adjustments to meet our quality standards. You may resubmit your course for verification after making the required improvements.\n"
                        + "\n"
                        + "If you have any questions or need further clarification regarding the rejection, please don't hesitate to contact our support team. We're here to assist you in improving your course.\n"
                        + "\n"
                        + "Thank you for considering our platform for sharing your knowledge, and we look forward to seeing your course successfully verified in the future.\n"
                        + "\n"
                        + "Best regards,\n"
                        + "FocusLearn";
            } else if (type.equals("verify")) {
                //Subject
                msg.setSubject("Pending Course Verified");
                //Content
                emailContent = "Dear Lecturer,\n"
                        + "\n"
                        + "We are pleased to inform you that your course, "+ course.getCourseName()
                        +" has been successfully verified and is now live on our platform. Congratulations!\n"
                        + "\n"
                        + "Here are the details of your course:\n"
                        + "- Course Name: " + course.getCourseName() + "\n"
                        + "- Verification Status: Verified\n"
                        + "\n"
                        + "Your course is now accessible to our users, and we encourage you to share it with your intended audience. We appreciate your contribution to our e-learning platform and look forward to more successful courses from you.\n"
                        + "\n"
                        + "If you have any questions or need further assistance, please don't hesitate to contact our support team.\n"
                        + "\n"
                        + "Thank you for choosing our platform to share your knowledge.\n"
                        + "\n"
                        + "Best regards,\n"
                        + "FocusLearn";
            }
            msg.setText(emailContent, "UTF-8");

            //Send Email
            Transport.send(msg);

        } catch (MessagingException e) {
            System.out.println("sendWarningEmail(): " + e.getMessage());
        }
    }
}
