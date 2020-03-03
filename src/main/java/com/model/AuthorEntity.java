package com.model;

import java.util.List;

public class AuthorEntity {

    private String id;
    private String firstName;
    private String lastName;
    private List<ArticleEntity> articles;
    private List<String> contactLinks;

    public AuthorEntity(String id, String firstName, String lastName, List<ArticleEntity> articles, List<String> contactLinks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.articles = articles;
        this.contactLinks = contactLinks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleEntity> articles) {
        this.articles = articles;
    }

    public List<String> getContactLinks() {
        return contactLinks;
    }

    public void setContactLinks(List<String> contactLinks) {
        this.contactLinks = contactLinks;
    }
}
