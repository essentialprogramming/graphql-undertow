package com.model;

import java.time.LocalDate;
import java.util.List;

public class Article {

    private String id;
    private String title;
    private List<String> tags;
    private String content;
    private Author author;
    private LocalDate creationDate;
    private LocalDate lastModified;
    private int readingTime;
    private String image;
    private List<Comment> comment;

    public Article(String id, String title, List<String> tags, String content, Author author, LocalDate creationDate, LocalDate lastModified, int readingTime, String image, List<Comment> comment) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.author = author;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.readingTime = readingTime;
        this.image = image;
        this.comment = comment;
    }

    public int getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(int readingTime) {
        this.readingTime = readingTime;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
