package com.aydogan.deugundem;

public class Comment {

    private String Id;
    private String userId;
    private String articleId;
    private String text;
    private String commentUserName;
    private String createdDate;
    private String updatedDate;

    public Comment(String id, String userId, String articleId, String commentUserName,String text, String createdDate, String updatedDate) {
        Id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.text = text;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.commentUserName = commentUserName;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
}
