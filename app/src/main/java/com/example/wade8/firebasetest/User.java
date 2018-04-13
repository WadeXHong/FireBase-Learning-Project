package com.example.wade8.firebasetest;

/**
 * Created by wade8 on 2018/4/10.
 */

public class User {
    String email;
    private String UID;


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String userEmail) {
        this.email = userEmail;
    }
}
