package com.example.adity.scholarquiz.Model;

public class User {
    private String slackId;
    private String password;
    private String email;

    public User() {

    }

    public User(String slackId, String password, String email) {
        this.slackId = slackId;
        this.password = password;
        this.email = email;
    }

    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
