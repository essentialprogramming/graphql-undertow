package com.api.resources;


import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.model.Article;
import com.repository.ArticleRepository;

import java.util.List;


public class Query implements GraphQLRootResolver {

    private final ArticleRepository articleRepository;

    public Query(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> allArticlesByTitle(String filter) {
        return articleRepository.getAllArticlesByTitle(filter);
    }

    public List<Article> allByTag(List<String> tags) {

        return articleRepository.getAllByTag(tags);
    }

    public List<Article> allByAuthor(String firstName, String lastName) {

        return articleRepository.getAllByAuthor(firstName, lastName);
    }

    public List<Article> allByDate(String date, String compareValue) {
        return articleRepository.getAllByDate(date, compareValue);
    }

    public List<Article> allArticles() {
        return articleRepository.getAllArticles();
    }

    public Article articleById(String id) {
        return articleRepository.getArticleById(id);
    }
}
