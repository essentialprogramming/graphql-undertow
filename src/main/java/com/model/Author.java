package com.model;

import java.util.List;

public class Author {

    private String id;
    private String firstName;
    private String lastName;
    private List<String> articles;
    private List<String> contactLinks;

    public Author(String id, String firstName, String lastName, List<String> articles, List<String> contactLinks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.articles = articles;
        this.contactLinks = contactLinks;
    }
}
