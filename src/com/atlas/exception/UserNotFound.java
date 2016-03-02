package com.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Invalid User.")
public class UserNotFound extends Exception {
	private static final long serialVersionUID = 1L;
}
