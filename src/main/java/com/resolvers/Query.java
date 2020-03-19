package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.model.Filter;
import com.repository.ArticleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class Query implements GraphQLQueryResolver {

    private final ArticleRepository articleRepository;

    public Query(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public String hello(String message) {
        return message;
    }

    public Article articleById(String id) {
        return ArticleMapper.entityToGraphQL(articleRepository.getById(id));
    }

    public List<Article> getArticles(Filter filter) {

        List<Article> result;

        List<Article> articles = articleRepository.allArticles()
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .collect(Collectors.toList());

        if (filter == null) return articles;

        else {
            result = articles.stream()
                    .filter(article -> filter.getTitle() != null &&
                            article.getTitle()
                                    .toLowerCase()
                                    .contains(filter.getTitle().toLowerCase()))

                    .filter(article -> filter.getTags() != null &&
                            article.getTags()
                                    .stream()
                                    .map(String::toLowerCase)
                                    .anyMatch(tag -> filter.getTags()
                                            .stream()
                                            .map(String::toLowerCase)
                                            .collect(Collectors.toList())
                                            .contains(tag)))

                    .filter(article -> filter.getFirstName() != null && filter.getLastName() != null &&
                            article.getAuthor()
                                    .getFirstName()
                                    .equals(filter.getFirstName()) &&
                            article.getAuthor()
                                    .getLastName()
                                    .equals(filter.getLastName()))

                    .filter(article -> filter.getStartDate() != null && filter.getEndDate() == null &&
                            article.getCreationDate()
                                    .isAfter(LocalDate.parse(filter.getStartDate())) ||

                            filter.getStartDate() != null && filter.getEndDate() != null &&
                                    article.getCreationDate()
                                            .isAfter(LocalDate.parse(filter.getStartDate())) &&
                                    article.getCreationDate()
                                            .isBefore(LocalDate.parse(filter.getEndDate())))
                    .collect(Collectors.toList());
        }

        return result;
    }
}
