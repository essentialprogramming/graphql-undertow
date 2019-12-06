package com.server.bean.producer;

import com.api.resources.FileInputResource;
import com.api.resources.GraphQLProvider;
import com.api.resources.StudentWiring;
import com.context.ContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentation;
import graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.dataloader.DataLoaderRegistry;
import sun.misc.IOUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions.newOptions;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.util.Arrays.asList;

@ApplicationScoped
public class BeanProducer {

    @Inject
    private DataLoaderRegistry dataLoaderRegistry;
    @Inject
    private StudentWiring studentWiring;

    @Produces
    @ApplicationScoped
    public DataLoaderRegistry produceDataLoaderRegistry() {
        return new DataLoaderRegistry();
    }

    @Produces
    @ApplicationScoped
    public StudentWiring produceWiring(DataLoaderRegistry dataLoaderRegistry) {
        System.out.println("Create wiring");
        return new StudentWiring(dataLoaderRegistry);
    }

    @Produces
    @ApplicationScoped
    public GraphQLProvider produceGraphQLProvider() throws IOException {
        return new GraphQLProvider(dataLoaderRegistry, studentWiring, graphQL());
    }

    @Produces
    @ApplicationScoped
    public ContextProvider produceContextProvider(DataLoaderRegistry dataLoaderRegistry) {
        return new ContextProvider(dataLoaderRegistry);
    }

    @Produces
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        System.out.println("BUI:D");
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("student", studentWiring.schoolDataFetcher)

                )
                .type(newTypeWiring("Student")
                        .typeResolver(studentWiring.studentTypeResolver)
                )
                .build();

    }

    @Produces
    public GraphQL graphQL() throws IOException {

        FileInputResource fileInputResource = new FileInputResource("classpath:student.graphql");
        InputStream stream = fileInputResource.getInputStream();
        String text = null;

        try (final Reader reader = new InputStreamReader(stream)) {
            text = CharStreams.toString(reader);
        }
        System.out.println(text);

        GraphQLSchema graphQLSchema = buildSchema(text);

        DataLoaderDispatcherInstrumentation dlInstrumentation =
                new DataLoaderDispatcherInstrumentation(dataLoaderRegistry, newOptions().includeStatistics(true));

        Instrumentation instrumentation = new ChainedInstrumentation(
                asList(new TracingInstrumentation(), dlInstrumentation)
        );
        return GraphQL.newGraphQL(graphQLSchema)
                .instrumentation(instrumentation)
                .build();
    }


}
