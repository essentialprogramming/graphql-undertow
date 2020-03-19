package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.model.Author;
import com.model.Filter;
import com.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorResolver implements GraphQLResolver<Author> {

    private final ArticleRepository articleRepository;

    public AuthorResolver(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> articles(Author author, int count) {
        Filter filter = new Filter();
        filter.setFirstName(author.getFirstName());
        filter.setLastName(author.getLastName());

        List<Article> articles = articleRepository.allArticles()
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .filter(article ->
                        article.getAuthor()
                                .getFirstName()
                                .equals(filter.getFirstName()) &&
                        article.getAuthor()
                                .getLastName()
                                .equals(filter.getLastName()))
                .collect(Collectors.toList());

        List<Article> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            result.add(articles.get(i));
        }

        return result;
    }

}
