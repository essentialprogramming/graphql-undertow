package com.api.resources.handler;

public class Response<T> {

	private int status;
	private T body;

	public int getStatus() {
		return status;
	}

	public Response status(int status) {
		this.status = status;
		return this;
	}

	public T getBody() {
		return body;
	}

	public Response body(T body) {
		this.body = body;
		return this;
	}

	public Response() {
	}
}
