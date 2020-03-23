package com.entities;

public class CommentEntity {

    private String id;
    private String text;
    private String commentAuthor;

    public CommentEntity(String id, String text, String commentAuthor) {
        this.id = id;
        this.text = text;
        this.commentAuthor = commentAuthor;
    }

    public CommentEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentAuthor() {
        return commentAuthor;
    }

    public void setCommentAuthor(String commentAuthor) {
        this.commentAuthor = commentAuthor;
    }
}
