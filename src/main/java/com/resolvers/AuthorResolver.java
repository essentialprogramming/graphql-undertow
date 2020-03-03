package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.model.Author;
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
        List<Article> articles = articleRepository.allByAuthor(author.getFirstName(), author.getLastName()).stream().map(ArticleMapper::entityToGraphQL).collect(Collectors.toList());
        List<Article> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            result.add(articles.get(i));
        }

        return result;
    }

}
