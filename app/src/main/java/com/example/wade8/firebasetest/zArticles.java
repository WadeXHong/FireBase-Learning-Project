package com.example.wade8.firebasetest;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by wade8 on 2018/4/13.
 */
@IgnoreExtraProperties
public class zArticles {

    public zArticles(){
    }

    public String authorName;
    public String authorEmail;
    public String authorId;
    public String authorImage;
    public String content;
    public long createdTime;
    public long interests;
    public String picture;
    public String tag;
    public String title;

}
