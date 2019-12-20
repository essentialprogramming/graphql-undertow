package com.api.resources;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.model.Article;
import com.model.ArticleDTO;
import com.repository.ArticleRepository;

import java.io.IOException;


public class Mutation implements GraphQLRootResolver {

    private final ArticleRepository articleRepository;

    Mutation(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(ArticleDTO article, String firstName) throws IOException {
        return articleRepository.saveArticle(article, firstName);
    }
}
