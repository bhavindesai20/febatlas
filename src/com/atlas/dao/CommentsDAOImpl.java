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
public class CommentsDAOImpl implements CommentsDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public CommentsDAOImpl() {
	}

	@Override
	@Transactional
	public Comments addComments(int titleId, int userId, Comments c) {
		Session session = sessionFactory.getCurrentSession();
		session.save(c);
		return c;
	}

	@Override
	@Transactional
	public Comments removeComments(int commentId) {
		Session session = sessionFactory.getCurrentSession();
		Comments comment = (Comments) session.get(Comments.class, new Integer(commentId));
		comment.setTitle(null);
		comment.setUser(null);
		session.delete(comment);
		return comment;
	}

	@Override
	@Transactional
	public Comments getCommentByID(int commentId) {
		Session session = sessionFactory.getCurrentSession();
		Comments comment = (Comments) session.get(Comments.class, new Integer(commentId));
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
		Query query = sessionFactory.getCurrentSession().createQuery("from Comments where title_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Comments> commentsListByTitle = query.setParameter("searchTerm",titleId).list();
		return commentsListByTitle;
	}

	@Override
	@Transactional
	public List<Comments> getCommentsForUser(int userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Comments where user_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Comments> commentsListByUser = query.setParameter("searchTerm",userId).list();
		return commentsListByUser;
	}

	@Override
	@Transactional
	public void removeCommentsForUser(int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = sessionFactory.getCurrentSession().createQuery("from Comments where user_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Comments> commentsListByUser = query.setParameter("searchTerm",userId).list();
		for (Comments cm : commentsListByUser) {
			cm.setTitle(null);
			cm.setUser(null);
			session.delete(cm);
		}
	}

	@Override
	@Transactional
	public void removeCommentsForTitle(int titleId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = sessionFactory.getCurrentSession().createQuery("from Comments where title_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Comments> commentsListByTitle = query.setParameter("searchTerm",titleId).list();
		for (Comments cm : commentsListByTitle) {
			cm.setTitle(null);
			cm.setUser(null);
			session.delete(cm);
		}
	}

}
