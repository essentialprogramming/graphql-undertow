package com.server.exchange;

import com.api.resources.handler.Response;
import com.util.map.LinkedMultiValueMap;
import com.util.map.MultiValueMap;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * An HTTP request/response exchange. A new instance is created for each HTTP request that is
 * received.
 *
 * @author Razvan Prichici
 */
public final class HttpExchange {
	private static final RuntimePermission SET_SECURITY_CONTEXT = new RuntimePermission(
			"digital-consulting.SET_SECURITY_CONTEXT");

	private MultiValueMap<String, String> pathParameters;
	private MultiValueMap<String, String> queryParameters;
	private SecurityContext securityContext;
	private Map<String, Object> properties;

	private Response<String> response = new Response();

	/**
	 * Returns the property with the given name registered in the current request/response exchange
	 */
	public Object getProperty(String name) {
		return properties.get(name);
	}

	/**
	 * Binds an object to a given property name in the current request/response exchange.
	 * <p>
	 * A property allows handlers to exchange additional information.
	 * </p>
	 *
	 */
	public void setProperty(String name, Object object) {
		if (properties == null) {
			properties = new LinkedHashMap<>();
		}
		properties.put(name, object);
	}

	/**
	 * Removes a property with the given name from the current request/response exchange
	 */
	public void removeProperty(String name) {
		if (properties == null) {
			return;
		}
		properties.remove(name);
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			sm.checkPermission(SET_SECURITY_CONTEXT);
		}
		this.securityContext = securityContext;
	}

	public MultiValueMap<String, String> getPathParameters() {
		if (pathParameters == null) {
			pathParameters = new LinkedMultiValueMap<>();
		}
		return pathParameters;
	}

	public HttpExchange addPathParam(final String name, final String param) {
		if (pathParameters == null) {
			pathParameters = new LinkedMultiValueMap<>();
		}

		pathParameters.putIfAbsent(name, new LinkedList<>());

		pathParameters.computeIfPresent(name, (key, value) -> {
			value.add(param);
			return value;
		});

		return this;
	}

	public MultiValueMap<String, String> getQueryParameters() {
		if (queryParameters == null) {
			queryParameters = new LinkedMultiValueMap<>();
		}
		return queryParameters;
	}

	public HttpExchange addQueryParam(final String name, final String param) {
		if (queryParameters == null) {
			queryParameters = new LinkedMultiValueMap<>();
		}

		queryParameters.putIfAbsent(name, new LinkedList<>());

		queryParameters.computeIfPresent(name, (key, value) -> {
			value.add(param);
			return value;
		});

		return this;
	}

	public HttpExchange addQueryParam(final String name, final String[] params) {
		if (queryParameters == null) {
			queryParameters = new LinkedMultiValueMap<>();
		}
		queryParameters.add(name, params);
		return this;
	}

	public String getParameter(final String name) {
		return getQueryParameters().getFirst(name);
	}

	public Response<String> getResponse() {
		return response;
	}

	public void setResponse(Response<String> response) {
		this.response = response;
	}
}
