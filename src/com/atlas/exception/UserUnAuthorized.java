package com.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You are not authorized to proceed on this operation")
public class UserUnAuthorized extends Exception {
	private static final long serialVersionUID = 3395643850556625864L;

}
