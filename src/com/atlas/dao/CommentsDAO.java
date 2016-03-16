package com.atlas.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atlas.entity.Comments;

@Repository
public interface CommentsDAO {

	public Comments addComments(int titleId, int userId,Comments c);

	public Comments removeComments(int commentId);
	
	public void removeCommentsForUser(int userId);
	
	public void removeCommentsForTitle(int titleId);
	
	public Comments getCommentByID(int commentId);

	public Comments updateComment(Comments c);

	public List<Comments> getCommentsForTitle(int titleId);

	public List<Comments> getCommentsForUser(int userId);

}
