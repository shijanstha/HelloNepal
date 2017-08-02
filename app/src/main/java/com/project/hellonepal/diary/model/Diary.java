package com.project.hellonepal.diary.model;

import java.util.Date;

import io.realm.RealmObject;

public class Diary extends RealmObject {


    private String title;
    private String content;
    private Date date;
    private byte[] photo;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
