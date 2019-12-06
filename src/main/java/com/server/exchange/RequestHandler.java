package com.server.exchange;

import com.server.handler.Handler;

import java.util.ArrayDeque;
import java.util.Queue;

public final class RequestHandler implements Handler<HttpExchange> {

	private final Queue<Handler<HttpExchange>> handlers = new ArrayDeque<>();
	
	public RequestHandler(String uri) { //NOSONAR Decorative role to improve readability
	}

	public static RequestHandler route(String uri) { 
		return new RequestHandler(uri);
	}
	
	public RequestHandler handler(Handler<HttpExchange> handler){
		handlers.offer(handler);
		return this;
	}
	
	public void handle(HttpExchange exchange){
		handlers.iterator().forEachRemaining(handler -> handler.handle(exchange));
		handlers.clear();
	}
}
