package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private int courseId;
    private String courseName;
    private String description;
    private double price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private int discount;
    private String level;
    private String image;
    private String longDescription;
    private int status;
    private User author;
    private Certificate certificate;
    private String authorName;
    private List<User> listUser;
    private List<Tag> listTag = new ArrayList<>();
    private List<CourseSection> listSection;

    public Course() {
    }

    public Course(int courseId, String courseName, String description, double price, LocalDateTime createdDate, LocalDateTime updateDate, int discount, String level, int status, User author) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.discount = discount;
        this.level = level;
        this.status = status;
        this.author = author;
    }

    public Course(int courseId, String courseName, String description, double price, int discount, String image, String level, String authorName) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.level = level;
        this.authorName = authorName;
    }

    public Course(int courseId, String courseName, String description, double price, LocalDateTime createdDate, LocalDateTime updateDate, int discount, String level, String image, String longDescription, int status, User author) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.discount = discount;
        this.level = level;
        this.image = image;
        this.longDescription = longDescription;
        this.status = status;
        this.author = author;
    }

    public Course(String courseName, String description, double price, LocalDateTime createdDate, LocalDateTime updateDate, int discount, String level, String image, String longDescription, int status, User author) {
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.discount = discount;
        this.level = level;
        this.image = image;
        this.longDescription = longDescription;
        this.status = status;
        this.author = author;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public List<Tag> getListTag() {
        return listTag;
    }

    public void setListTag(List<Tag> listTag) {
        this.listTag = listTag;
    }

    public List<CourseSection> getListSection() {
        return listSection;
    }

    public void setListSection(List<CourseSection> listSection) {
        this.listSection = listSection;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

}
