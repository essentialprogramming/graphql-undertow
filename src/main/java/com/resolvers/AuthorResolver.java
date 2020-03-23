package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.model.Author;
import com.repository.ArticleRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorResolver implements GraphQLResolver<Author> {

    private final ArticleRepository articleRepository;

    public AuthorResolver(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> articles(Author author, int count) {

        return articleRepository.allArticles()
                .stream()
                .map(ArticleMapper::entityToGraphQL)
                .filter(article ->
                        article.getAuthor()
                                .getFirstName()
                                .equals(author.getFirstName()) &&
                                article.getAuthor()
                                        .getLastName()
                                        .equals(author.getLastName()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
