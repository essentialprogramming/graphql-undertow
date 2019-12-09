package com.api.resources;

import com.context.Context;
import com.model.Student;
import com.model.StudentData;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StudentWiring {

    private DataLoaderRegistry dataLoaderRegistry;

    @Inject
    public StudentWiring(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
        dataLoaderRegistry.register("students", newStudentDataLoader());
    }

    private List<Object> getStudentDataViaBatchHTTPApi(List<String> keys) {
        return keys.stream().map(StudentData::getStudentData).collect(Collectors.toList());
    }

    private BatchLoader<String, Object> studentBatchLoader = keys -> {
        return CompletableFuture.supplyAsync(() -> getStudentDataViaBatchHTTPApi(keys));
    };

    private DataLoader<String, Object> newStudentDataLoader() {
        return new DataLoader<>(studentBatchLoader);
    }

    DataFetcher studentDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context context = environment.getContext();
        return context.getStudentDataLoader().load(id);
    };

    TypeResolver studentTypeResolver = environment -> {
        return (GraphQLObjectType) environment.getSchema().getType("Student");
    };

}
