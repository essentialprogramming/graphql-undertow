package com.server.bean.producer;


import com.api.resources.ArticleWiring;
import com.context.ContextProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dataloader.DataLoaderRegistry;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;


@ApplicationScoped
public class BeanProducer {

    @Produces
    @ApplicationScoped
    public DataLoaderRegistry produceDataLoaderRegistry() {
        return new DataLoaderRegistry();
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

    @Produces
    public ArticleWiring produceArticleWiring(DataLoaderRegistry dataLoaderRegistry){
        return new ArticleWiring(dataLoaderRegistry);
    }

}
