package com.resolvers;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.model.Article;
import com.repository.ArticleRepository;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;


public class Query implements GraphQLQueryResolver {

    private final ArticleRepository articleRepository;

    public Query(ArticleRepository articleRepository) {

        this.articleRepository = articleRepository;
    }

    public List<Article> allArticlesByTitle(String filter, DataFetchingEnvironment env) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>"+env.getFields());
        System.out.println("::::::::::::::::"+env.getSelectionSet().get().keySet());
        return articleRepository.getAllArticlesByTitle(filter);
    }

    public List<Article> allByTag(List<String> tags, DataFetchingEnvironment env) {
        System.out.println("-----------------"+env.getSelectionSet().get().keySet());

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
