package com.api.resources;

import com.coxautodev.graphql.tools.SchemaParser;
import com.repository.ArticleRepository;
import com.repository.AuthorRepository;
import com.repository.CommentRepository;
import com.resolvers.ArticleResolver;
import com.resolvers.AuthorResolver;
import com.resolvers.Mutation;
import com.resolvers.Query;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class GraphInit {

    private GraphQLSchema buildSchema(ArticleRepository articleRepository) throws IOException {

        return SchemaParser.newParser()
                .file("article.graphql")
                .resolvers(
                        new Query(articleRepository),
                        new Mutation(articleRepository),
                        new ArticleResolver(new AuthorRepository(), new CommentRepository()),
                        new AuthorResolver(articleRepository)
                        )
                .build()
                .makeExecutableSchema();
    }

    @Bean
    public GraphQL graphQL(ArticleRepository articleRepository) throws IOException {

        GraphQLSchema graphQLSchema = buildSchema(articleRepository);

        return GraphQL.newGraphQL(graphQLSchema)
                .build();
    }

}
