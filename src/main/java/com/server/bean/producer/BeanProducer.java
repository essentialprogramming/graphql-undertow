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
import java.io.*;

import static graphql.execution.instrumentation.dataloader.DataLoaderDispatcherInstrumentationOptions.newOptions;
import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.util.Arrays.asList;

@ApplicationScoped
public class BeanProducer {

    @Produces
    @ApplicationScoped
    public DataLoaderRegistry produceDataLoaderRegistry() {
        return new DataLoaderRegistry();
    }

    @Produces
 /*   @ApplicationScoped*/
    public StudentWiring produceWiring(DataLoaderRegistry dataLoaderRegistry) {
        System.out.println("Create wiring");
        return new StudentWiring(dataLoaderRegistry);
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

}
