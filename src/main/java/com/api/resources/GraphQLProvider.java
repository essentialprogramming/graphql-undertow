package com.api.resources;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.dataloader.DataLoaderRegistry;

import javax.inject.Inject;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;


public class GraphQLProvider {

    @Inject
    private DataLoaderRegistry dataLoaderRegistry;
    @Inject
    private StudentWiring studentWiring;

    private GraphQL graphQL;

    public GraphQLProvider(DataLoaderRegistry dataLoaderRegistry, StudentWiring studentWiring, GraphQL graphQL) {
        this.dataLoaderRegistry = dataLoaderRegistry;
        this.studentWiring = studentWiring;
        this.graphQL = graphQL;
    }

    public GraphQLProvider() {
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("student", studentWiring.studentDataFetcher)
                )
                .type(newTypeWiring("School")
                        .dataFetcher("student", studentWiring.studentDataFetcher)
                )
                .type(newTypeWiring("Student")
                        .dataFetcher("student", studentWiring.studentDataFetcher)
                )
                .build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }
}
