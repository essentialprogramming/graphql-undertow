package com.model;

import java.util.List;

public class Article {

    private String id;
    private String title;
    private List<String> tags;
    private String paragraph;
    private Author author;

    public Article(String id, String title, List<String> tags, String paragraph, Author author) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.paragraph = paragraph;
        this.author = author;
    }
}
