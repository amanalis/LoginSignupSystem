package com.example.loginsystem.model;

public class Notes {
    private int id;
    private String email;
    private String title;
    private String content;
    private String timestamp;

    public Notes(int id, String email, String title, String content, String timestamp) {
        this.id = id;
        this.email = email;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Notes(String email, String title, String content, String timestamp) {
        this.email = email;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Notes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
