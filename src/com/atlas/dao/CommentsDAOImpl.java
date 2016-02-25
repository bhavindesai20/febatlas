package com.atlas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.atlas.entity.Comments;

@Repository
public class CommentsDAOImpl implements CommentsDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public CommentsDAOImpl() {
	}
	
	@Override
	@Transactional
	public Comments addComments(Comments c) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(c);
		return c;
	}

	@Override
	@Transactional
	public Comments removeComments(int commentId) {
		Session session = sessionFactory.getCurrentSession();
		Comments comment = (Comments)session.load(Comments.class, new Integer(commentId));
		session.delete(comment);
		return comment;
	}

	@Override
	@Transactional
	public Comments updateComment(Comments c) {
		Session session = sessionFactory.getCurrentSession();
		session.update(c);
		return c;
	}

	@Override
	@Transactional
	public List<Comments> getCommentsForTitle(int titleId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Comments c where c.title_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Comments> commentsListByTitle = query.setParameter("searchTerm",titleId).list();
		return commentsListByTitle;
	}

	@Override
	@Transactional
	public List<Comments> getCommentsForUser(int userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Comments c where c.user_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Comments> commentsListByUser = query.setParameter("searchTerm",userId).list();
		return commentsListByUser;
	}

}
