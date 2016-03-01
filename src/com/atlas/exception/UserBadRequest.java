package com.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "incorrect User data found in request")
public class UserBadRequest extends Exception {
	private static final long serialVersionUID = -411122819463579668L;
}
