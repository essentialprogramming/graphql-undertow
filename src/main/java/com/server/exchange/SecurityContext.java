package com.server.exchange;

import java.security.Principal;

/**
 * The security context defining the security related information within the current thread
 * of execution.
 *
 * The context is attached to the current exchange.
 *
 * @author Razvan Prichici
 */
public class SecurityContext {

	private boolean authenticationRequired;
	private Principal principal;

	/**
	 * Obtains the currently authenticated principal.
	 *
	 */
	public Principal getAuthentication() {
		return principal;
	}

	/**
	 * Changes the currently authenticated principal or removes the authentication
	 * information.
	 */
	public void setAuthentication(Principal principal) {
		this.principal = principal;
	}

	public void setAuthenticationRequired() {
		authenticationRequired = true;
	}

	public boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public boolean isAuthenticated() {
		return principal != null;
	}
}
