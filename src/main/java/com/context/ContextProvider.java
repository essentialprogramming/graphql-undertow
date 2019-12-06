package com.context;

import org.dataloader.DataLoaderRegistry;

import javax.inject.Inject;

public class ContextProvider {

    @Inject
    DataLoaderRegistry dataLoaderRegistry;

    public ContextProvider() {
    }

    public ContextProvider(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    public Context newContext() {
        return new Context(dataLoaderRegistry);
    }

}
