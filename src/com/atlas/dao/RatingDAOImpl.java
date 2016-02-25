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
		Query query = sessionFactory.getCurrentSession().createQuery("from Rating r where r.user_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Rating> ratingsListByUser = query.setParameter("searchTerm",userId).list();
		return ratingsListByUser;
	}

	@Override
	@Transactional
	public List<Rating> getRatingByMovie(int titleId) {
		Query query = sessionFactory.getCurrentSession().createQuery("from Rating r where r.title_Id = :searchTerm");
		@SuppressWarnings("unchecked")
		List<Rating> ratingListByTitle = query.setParameter("searchTerm",titleId).list();
		return ratingListByTitle;
	}

	@Override
	@Transactional
	public Double getAverageRatingForTitle(int titleId) {
		Double average=0.0;
		Query query = sessionFactory.getCurrentSession().createQuery("select AVG(rating) from Rating r where r.title_Id = :searchTerm group by r.title_Id");
		@SuppressWarnings("unchecked")
		List<Object[]> avgRating = query.setParameter("searchTerm",titleId).list();
		for (Object[] aRow : avgRating) {
			average = (Double) aRow[0];
		}
		return average;
	}

}
