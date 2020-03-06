package com.resolvers;


import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.repository.ArticleRepository;
import graphql.language.Field;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.stream.Collectors;


public class Query implements GraphQLQueryResolver {

    private final ArticleRepository articleRepository;

    public Query(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> allByTitle(String filter, DataFetchingEnvironment env) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>" + env.getFields());
        System.out.println("::::::::::::::::" + env.getSelectionSet().get().keySet());

        return articleRepository.allByTitle(filter)
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
    }

    public List<Article> allByTag(List<String> tags, DataFetchingEnvironment env) {
        System.out.println("-----------------" + env.getSelectionSet().get().keySet());

        return articleRepository.allByTag(tags)
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
    }

    public List<Article> allByAuthor(String firstName, String lastName, DataFetchingEnvironment env) {
        env.getFields().forEach(selection -> {
            if (selection instanceof Field) {
                Field selectedField = (Field) selection;

                System.out.println("Selected field: " + selectedField);
            }
        });

        return articleRepository.allByAuthor(firstName, lastName)
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
    }

    public List<Article> allBetweenDates(String startDate, String endDate) {
        return articleRepository.allBetweenDates(startDate, endDate)
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
    }

    public List<Article> allArticles() {
        return articleRepository.allArticles()
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());
    }

    public Article articleById(String id) {
        return ArticleMapper.entityToGraphQL(articleRepository.getById(id));
    }

    public String hello(String message){
        return message;
    }
}
