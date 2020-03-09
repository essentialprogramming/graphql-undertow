package com.async.support;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorsProvider class provides static access to application shared ExecutorServices to be used by asynchronous
 * methods (tasks implemented using CompletableFutures that run asynchronously).
 */
@Component
public class ExecutorsProvider {

	private static final int APP_EXECUTOR_POOL_SIZE = 5;
	private ExecutorService executorService;

	@PostConstruct 
	private void init() {
		executorService = Executors.newFixedThreadPool(APP_EXECUTOR_POOL_SIZE);
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}
}
