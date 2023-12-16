package model;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String img;
    private String dob;
    private int gender;
    private String description;
    private int role;
    List<Course> listCourse;
    private int status;
    private double wallet;

    public User() {
    }

    public User(String username, String password, String fullName, String email, String img, String dob, int gender, String description, int role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.img = img;
        this.dob = dob;
        this.gender = gender;
        this.description = description;
        this.role = role;
    }
    
    public User(String fullName, String email, String dob, int gender, String description, int status, String img) {
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.description = description;
        this.status = status;
        this.img = img;
    }

    public User(String username, String fullName, String email, String dob, int gender, String description, int status, String img) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.description = description;
        this.status = status;
        this.img = img;
    }

    public User(String username, String password, String fullName, String email, String img, String dob, int gender, String description, int role, int status, double wallet) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.img = img;
        this.dob = dob;
        this.gender = gender;
        this.description = description;
        this.role = role;
        this.status = status;
        this.wallet = wallet;
    }

    
    
    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public List<Course> getListCourse() {
        return listCourse;
    }

    public void setListCourse(List<Course> listCourse) {
        this.listCourse = listCourse;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", fullName=" + fullName + ", email=" + email + ", img=" + img + ", dob=" + dob + ", gender=" + gender + ", description=" + description + ", role=" + role + ", listCourse=" + listCourse + ", status=" + status + '}';
    }
}
