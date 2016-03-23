package com.atlas.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.atlas.dao.CommentsDAO;
import com.atlas.dao.TitleDAO;
import com.atlas.dao.UserDAO;
import com.atlas.entity.Comments;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.CommentBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@Service
public class CommentsServiceImpl implements CommentsService {

	@Autowired
	@Qualifier("titleDAOImpl")
	private TitleDAO titleDAO;

	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("commentsDAOImpl")
	private CommentsDAO commentsDAO;

	public CommentsServiceImpl() {
	}

	@Override
	public Comments addComments(int titleId, int userId, Comments c)
			throws UserNotFound, MovieNotFound, CommentBadRequest {
		if (c.getComments().isEmpty()) {
			throw new CommentBadRequest();
		}
		Title t = titleDAO.getTitleById(titleId);
		if (t != null) {
			c.setTitle(t);
		} else {
			throw new MovieNotFound();
		}
		User u = userDAO.getUserById(userId);
		if (u != null) {
			c.setUser(u);
		} else {
			throw new UserNotFound();
		}
		return commentsDAO.addComments(titleId,userId,c);
	}

	@Override
	public Comments removeComments(int titleId, int userId, int commentId)
			throws UserUnAuthorized {
		if (userId == 0) {
			throw new UserUnAuthorized();
		}
		return commentsDAO.removeComments(commentId);
	}

	@Override
	public Comments updateComment(int titleId, int userId, Comments c)
			throws UserUnAuthorized, CommentBadRequest, MovieNotFound {
		if (c.getComments() == null) {
			throw new CommentBadRequest();
		}
		Title t = titleDAO.getTitleById(titleId);
		if (t == null) {
			throw new MovieNotFound();
		}
		c.setTitle(t);
		User u = userDAO.getUserById(userId);
		if (u == null) {
			throw new UserUnAuthorized();
		}
		c.setUser(u);
		return commentsDAO.updateComment(c);
	}

	@Override
	public List<Comments> getCommentsForTitle(int titleId) throws MovieNotFound {
		Title t = titleDAO.getTitleById(titleId);
		if(t == null){
			throw new MovieNotFound();
		}
		List<Comments> commentsList = commentsDAO.getCommentsForTitle(titleId);
		Iterator<Comments> cm= commentsList.iterator();
		while(cm.hasNext()){
			Comments c = (Comments)cm.next();
			User u = userDAO.getUserById(c.getUser().getId());
			c.setUser(u);
		}
		return commentsList;
	}

	@Override
	public List<Comments> getCommentsForUser(int userId) throws UserNotFound,
			UserUnAuthorized {
		if (userId == 0) {
			throw new UserUnAuthorized();
		}
		List<Comments> commentsList = commentsDAO.getCommentsForUser(userId);
		Iterator<Comments> cm= commentsList.iterator();
		while(cm.hasNext()){
			Comments c = (Comments)cm.next();
			User u = userDAO.getUserById(c.getUser().getId());
			c.setUser(u);
		}
		return commentsList;
	}

}
