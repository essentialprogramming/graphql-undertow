package com.api.resources;

import com.context.Context;
import com.model.ArticleData;
import com.model.Author;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLObjectType;
import graphql.schema.TypeResolver;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ArticleWiring {

    private DataLoaderRegistry dataLoaderRegistry;

    public ArticleWiring(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
        dataLoaderRegistry.register("articles", newArticleDataLoader());
    }

    private List<Object> getArticleDataViaBatchHTTPApi(List<String> keys) {
        return keys.stream().map(ArticleData::getArticleData).collect(Collectors.toList());
    }

    private BatchLoader<String, Object> articleBatchLoader = keys -> {
        return CompletableFuture.supplyAsync(() -> getArticleDataViaBatchHTTPApi(keys));
    };

    private DataLoader<String, Object> newArticleDataLoader() {
        return new DataLoader<>(articleBatchLoader);
    }

    DataFetcher articleDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context context = environment.getContext();
        return context.getArticleDataLoader().load(id);
    };

    DataFetcher authorDataFetcher = environment -> {
        String id = environment.getArgument("id");
        Context context = environment.getContext();
        return context.getArticleDataLoader().load(id);
    };

    TypeResolver articleTypeResolver = environment -> {
        return (GraphQLObjectType) environment.getSchema().getType("Article");
    };

    TypeResolver authorTypeResolver = environment -> {
        return (GraphQLObjectType) environment.getSchema().getType("Author");
    };
}
