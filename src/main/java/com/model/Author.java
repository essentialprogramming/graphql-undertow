package com.model;

import java.util.List;

public class Author {

    private String id;
    private String firstName;
    private String lastName;
    private List<Article> articles;
    private List<String> contactLinks;

    public Author(String id, String firstName, String lastName, List<Article> articles, List<String> contactLinks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.articles = articles;
        this.contactLinks = contactLinks;
    }

    public Author() {
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

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<String> getContactLinks() {
        return contactLinks;
    }

    public void setContactLinks(List<String> contactLinks) {
        this.contactLinks = contactLinks;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", articles=" + articles +
                ", contactLinks=" + contactLinks +
                '}';
    }
}
