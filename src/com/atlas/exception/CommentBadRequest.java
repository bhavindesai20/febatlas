package com.atlas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "incorrect Comment data found in request")
public class CommentBadRequest extends Exception {
	private static final long serialVersionUID = -7311953203815441796L;
}
