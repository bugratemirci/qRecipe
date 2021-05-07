package com.example.q_recipe.Business;

import java.io.Serializable;

public class LoggedInUser implements Serializable {

    private String name;
    private String email;
    private String access_token;
    private String id;
    private String phone;
    private String about;
    private String role;
    private String profile_image;



    public LoggedInUser(String name, String email, String access_token, String id, String phone, String about, String role, String profile_image) {
        this.name = name;
        this.email = email;
        this.access_token = access_token;
        this.id = id;
        this.phone = phone;
        this.about = about;
        this.role = role;
        this.profile_image = profile_image;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoggedInUser() {
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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
