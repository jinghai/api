package com.ipet.web.rest.exceptions;

/**
 * Simulated business-logic exception indicating a desired business entity or
 * record cannot be found.
 */
public class UnknownResourceException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 3218630511016760807L;

	public UnknownResourceException() {
		super();
	}

	public UnknownResourceException(String msg) {
		super(msg);
	}

	public UnknownResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownResourceException(Throwable cause) {
		super(cause);
	}

}
