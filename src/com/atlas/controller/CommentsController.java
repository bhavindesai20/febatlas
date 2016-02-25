package com.atlas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.entity.Comments;
import com.atlas.exception.CommentBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;
import com.atlas.service.CommentsService;

@RestController
@RequestMapping("/comments")
public class CommentsController {

	@Autowired
	@Qualifier("commentsServiceImpl")
	private CommentsService commentsService;

	@RequestMapping(value = "{titleId}/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Comments addComments(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId, @RequestBody Comments c)
			throws UserNotFound, MovieNotFound, CommentBadRequest {
		return this.commentsService.addComments(titleId, userId, c);
	}

	@RequestMapping(value = "{titleId}/{userId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Comments updateComments(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId, @RequestBody Comments c)
			throws UserUnAuthorized, CommentBadRequest, MovieNotFound {
		return this.commentsService.updateComment(titleId, userId, c);
	}

	@RequestMapping(value = "{titleId}/{userId}/{commentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Comments deleteComments(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId,
			@PathVariable("commentId") int commentId) throws UserUnAuthorized {
		return this.commentsService.removeComments(titleId, userId, commentId);
	}

	@RequestMapping(value = "/title/{titleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Comments> getCommentsForTitle(
			@PathVariable("titleId") int titleId) throws MovieNotFound {
		return this.commentsService.getCommentsForTitle(titleId);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Comments> getCommentsForUser(@PathVariable("userId") int userId)
			throws UserNotFound, UserUnAuthorized {
		return this.commentsService.getCommentsForUser(userId);
	}
	
}
