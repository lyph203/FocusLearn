package model;
import java.time.LocalDateTime;
import java.util.List;

public class Question {
    private int questionId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private User user;
    List<Answer> listAnswer;
    List<Tag> listTag;
    private String status;
    public Question() {
    }

    public Question(int questionId, String title, String content, LocalDateTime createdDate, LocalDateTime updateDate, User user) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.user = user;
    }

    public Question(String title, String content, LocalDateTime createdDate, User user) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
    }

    public Question(int questionId, String title, String content, LocalDateTime createdDate, LocalDateTime updateDate, User user, List<Answer> listAnswer, List<Tag> listTag, String status) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.user = user;
        this.listAnswer = listAnswer;
        this.listTag = listTag;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate.toString().replace("T00:00", "");
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdateDate() {
        if(updateDate != null){
            return updateDate.toString().replace("T00:00", "");
        }
        return null;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getListAnswer() {
        return listAnswer;
    }

    public void setListAnswer(List<Answer> listAnswer) {
        this.listAnswer = listAnswer;
    }

    public List<Tag> getListTag() {
        return listTag;
    }

    public void setListTag(List<Tag> listTag) {
        this.listTag = listTag;
    }
    
    
}
