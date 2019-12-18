package com.server.bean.producer;


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



}
