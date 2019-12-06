package com.api.resources;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.dataloader.DataLoaderRegistry;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;

import static graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions.newOptions;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.util.Arrays.asList;


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
                        .dataFetcher("student", studentWiring.schoolDataFetcher)
                )
                .type(newTypeWiring("School")
                        .dataFetcher("student", studentWiring.schoolDataFetcher)
                )
                .type(newTypeWiring("Student")
                        .dataFetcher("student", studentWiring.schoolDataFetcher)
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
