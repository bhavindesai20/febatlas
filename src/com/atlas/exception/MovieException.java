package com.atlas.exception;

public class MovieException extends Exception {

	private static final long serialVersionUID = 1L;

	public MovieException() {

	}

	public MovieException(String msg) {
		super(msg);
	}

	public MovieException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
