package model;

public class CourseSection {

    private int sectionId;
    private String title;
    private String video;
    private String description;
    private Course course;
    private int status;

    public CourseSection() {
    }

    public CourseSection(int sectionId, String title, String video, String description, Course course) {
        this.sectionId = sectionId;
        this.title = title;
        this.video = video;
        this.description = description;
        this.course = course;
    }

    public CourseSection(String title, String video, String description, Course course) {
        this.title = title;
        this.video = video;
        this.description = description;
        this.course = course;
    }

    public CourseSection(String title, String video, String description) {
        this.title = title;
        this.video = video;
        this.description = description;
    }

    public CourseSection(int sectionId, String title, String video, String description, Course course, int status) {
        this.sectionId = sectionId;
        this.title = title;
        this.video = video;
        this.description = description;
        this.course = course;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    
    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    

}
