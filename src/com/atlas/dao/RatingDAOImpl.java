package com.atlas.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.atlas.entity.Rating;

@Repository
public class RatingDAOImpl implements RatingDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public RatingDAOImpl() {
	}

	@Override
	@Transactional
	public Rating addRating(Rating r) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(r);
		return r;
	}

	@Override
	@Transactional
	public List<Rating> getRatingByUser(int userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Rating where user_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Rating> ratingsListByUser = query.setParameter("searchTerm",userId).list();
		return ratingsListByUser;
	}

	@Override
	@Transactional
	public List<Rating> getRatingByTitle(int titleId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Rating where title_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Rating> ratingListByTitle = query.setParameter("searchTerm",titleId).list();
		return ratingListByTitle;
	}

	@Override
	@Transactional
	public Double getAverageRatingForTitle(int titleId) {
		Double average=0.0;
		Query query = sessionFactory.getCurrentSession().createQuery("select AVG(rating) from Rating where title_Id = :searchTerm group by title_Id");
		@SuppressWarnings("rawtypes")
		List avgRating = query.setParameter("searchTerm",titleId).list();
		average = (Double) avgRating.get(0);
		return average;
	}
	
	@Override
	@Transactional
	public void removeRatingForUser(int userId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = sessionFactory.getCurrentSession().createQuery("from Rating where user_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Rating> ratingsListByTitle = query.setParameter("searchTerm",userId).list();
		for(Rating r : ratingsListByTitle)
		{
			r.setTitle(null);
			r.setUser(null);
			session.delete(r);
		}
	}
	
	@Override
	@Transactional
	public void removeRatingForTitle(int titleId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = sessionFactory.getCurrentSession().createQuery("from Rating where title_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Rating> ratingsListByTitle = query.setParameter("searchTerm",titleId).list();
		for(Rating r : ratingsListByTitle)
		{
			r.setTitle(null);
			r.setUser(null);
			session.delete(r);
		}
	}


}
