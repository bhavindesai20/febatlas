package com.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "incorrect rating data found in request")
public class RatingBadRequest extends Exception {
	private static final long serialVersionUID = 1L;

}
