package com.model;

public class Comment {

    private String id;
    private String text;
    private String commentAuthor;

    public Comment(String id, String text, String commentAuthor) {
        this.id = id;
        this.text = text;
        this.commentAuthor = commentAuthor;
    }
}
