package com.server.bean.producer;


import org.dataloader.DataLoaderRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



@Component
public class BeanProducer {

    @Bean
    public DataLoaderRegistry produceDataLoaderRegistry() {
        return new DataLoaderRegistry();
    }



}
