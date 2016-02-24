package com.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND , reason="Requested Movie Not Found")
public class MovieNotFound  extends Exception {
	private static final long serialVersionUID = 1L;
}
