package com.server.bean.producer;

import com.api.resources.FileInputResource;
import com.api.resources.GraphQLProvider;
import com.api.resources.StudentWiring;
import com.context.ContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import static graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions.newOptions;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.util.Arrays.asList;

@ApplicationScoped
public class BeanProducerCtx {

    @Inject
    private DataLoaderRegistry dataLoaderRegistry;
    @Inject
    private StudentWiring studentWiring;

    @Produces
    @ApplicationScoped
    public GraphQLProvider produceGraphQLProvider() throws IOException {
        return new GraphQLProvider(dataLoaderRegistry, studentWiring, graphQL());
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
                        .dataFetcher("student", studentWiring.studentDataFetcher)

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
