package com.atlas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atlas.entity.Comments;
import com.atlas.exception.CommentBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@Service
public interface CommentsService {

	public Comments addComments(int titleId, int userId, Comments c)throws UserNotFound, MovieNotFound, CommentBadRequest;

	public Comments removeComments(int titleId, int userId,int commentId)throws UserUnAuthorized;

	public Comments updateComment(int titleId, int userId,Comments c)throws UserUnAuthorized,CommentBadRequest,MovieNotFound;

	public List<Comments> getCommentsForTitle(int titleId)throws MovieNotFound;

	public List<Comments> getCommentsForUser(int userId) throws UserNotFound, UserUnAuthorized;

}
