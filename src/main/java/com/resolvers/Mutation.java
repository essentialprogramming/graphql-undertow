package com.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mapper.ArticleMapper;
import com.model.Article;
import com.model.ArticleInput;
import com.repository.ArticleRepository;

import java.io.IOException;


public class Mutation implements GraphQLMutationResolver {

    private final ArticleRepository articleRepository;

    public Mutation(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article createArticle(ArticleInput article) throws IOException {
        return ArticleMapper.entityWithoutAuthorToGraphQL(articleRepository.saveArticle(article));
    }
}
