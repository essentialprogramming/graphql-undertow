package com.api.resources;

import com.coxautodev.graphql.tools.SchemaParser;
import com.model.Query;
import com.repository.ArticleRepository;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;


@ApplicationScoped
public class GraphInit {

    private GraphQLSchema buildSchema(ArticleRepository articleRepository) throws IOException {

        return SchemaParser.newParser()
                .file("article.graphql")
                .resolvers(
                        new Query(articleRepository))
                .build()
                .makeExecutableSchema();
    }

    @javax.enterprise.inject.Produces
    public GraphQL graphQL(ArticleRepository articleRepository) throws IOException {

        GraphQLSchema graphQLSchema = buildSchema(articleRepository);

        return GraphQL.newGraphQL(graphQLSchema)
                .build();
    }

}
