package com.aydogan.deugundem;

public class Article {

    private String Id;
    private String userId;
    private String text;
    private String createdDate;
    private String updatedDate;
    private String commenCount="0";


    public Article (String text,String userId,String createdDate,String updatedDate,String Id){
        this.text=text;
        this.createdDate=createdDate;
        this.updatedDate=updatedDate;
        this.userId=userId;
        this.Id=Id;
    }

    public String getCommenCount() {
        return commenCount;
    }

    public void setCommenCount(String commenCount) {
        this.commenCount = commenCount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
