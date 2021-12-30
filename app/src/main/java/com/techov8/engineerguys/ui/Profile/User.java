package com.techov8.engineerguys.ui.Profile;

public class User {

    private String name;
    private String email;

    private String username;
    private String mobile;
    private String imageurl;
    private String id;

    public User() {
    }

    public User(String name, String email, String username, String mobile, String imageurl, String id) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.mobile = mobile;
        this.imageurl = imageurl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
