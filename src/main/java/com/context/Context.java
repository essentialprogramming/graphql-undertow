package com.context;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;

public class Context {

    private final DataLoaderRegistry dataLoaderRegistry;

    Context(DataLoaderRegistry dataLoaderRegistry) {
        this.dataLoaderRegistry = dataLoaderRegistry;
    }

    public DataLoader<String, Object> getStudentDataLoader() {
        return dataLoaderRegistry.getDataLoader("students");
    }
}
