package com.example.wade8.firebasetest;

/**
 * Created by wade8 on 2018/4/11.
 */

public class Article {

    public String author;
    public String content;
    public long createdTime;
    public int interests;
    public boolean interestedIn;
    public String place;
    public String picture;
    public String tag;
    public String title;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getInterests() {
        return interests;
    }

    public void setInterests(int interests) {
        this.interests = interests;
    }

    public boolean isInterestedIn() {
        return interestedIn;
    }

    public void setInterestedIn(boolean interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
