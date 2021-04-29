package com.example.q_recipe.Business;

import java.io.Serializable;

public class LoggedInUser implements Serializable {
    private String name;
    private String email;
    private String access_token;
    private String id;

    public LoggedInUser(String name, String email, String access_token, String id) {
        this.name = name;
        this.email = email;
        this.access_token = access_token;
        this.id = id;

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
