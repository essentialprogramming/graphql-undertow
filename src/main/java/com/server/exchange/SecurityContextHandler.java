package com.server.exchange;

import com.server.handler.Handler;

/**
 * Handler responsible with associating the {@link SecurityContext} instance with the current request.
 *
 * @author Razvan Prichici
 */
public class SecurityContextHandler implements Handler<HttpExchange> {

	@Override
	public void handle(HttpExchange exchange) {
		SecurityContextFactory.setSecurityContext(exchange, createSecurityContext());

	}

	private SecurityContext createSecurityContext( ){
		return new SecurityContext();
	}

}
