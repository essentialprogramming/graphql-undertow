package com.api.resources;


import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.model.Article;
import com.model.SearchCriteria;
import com.repository.ArticleRepository;

import java.util.List;


public class Query implements GraphQLRootResolver {

    private final ArticleRepository articleRepository;

    public Query(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> allByTitle(SearchCriteria filter, String articleTitle) {
        return articleRepository.getAllByTitle(filter, articleTitle);
    }

    public List<Article> allByTag(SearchCriteria filter) {
        return articleRepository.getAllByTag(filter);
    }

    public List<Article> allByAuthor(SearchCriteria filter) {
        return articleRepository.getAllByAuthor(filter);
    }

    public List<Article> allByDate(SearchCriteria filter, String compareValue) {
        return articleRepository.getAllByDate(filter, compareValue);
    }

    public List<Article> allArticles() {
        return articleRepository.getAllArticles();
    }

    public Article articleById(SearchCriteria filter) {
        return articleRepository.getArticleById(filter);
    }
}
