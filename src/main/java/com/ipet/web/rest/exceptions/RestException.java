package com.ipet.web.rest.exceptions;

/**
 * Service层公用的Exception.
 * 
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
public class RestException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 2535093799629938840L;

	public RestException() {
		super();
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestException(Throwable cause) {
		super(cause);
	}

}
