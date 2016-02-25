package com.atlas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.atlas.dao.RatingDAO;
import com.atlas.dao.TitleDAO;
import com.atlas.dao.UserDAO;
import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@Service
public class RatingServiceImpl implements RatingService {

	@Qualifier("titleDAOImpl")
	private TitleDAO titleDAO;

	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("ratingDAOImpl")
	private RatingDAO ratingDAO;

	public RatingServiceImpl() {
	}

	@Override
	public Rating addRating(int userId, int titleId, Rating r)
			throws UserUnAuthorized , MovieNotFound{
		Title t = titleDAO.getTitleById(titleId);
		if (t != null) {
			r.setTitle(t);
		} else {
			throw new MovieNotFound();
		}
		User u = userDAO.getUserById(userId);
		if (u != null) {
			r.setUser(u);
		} else {
			throw new UserUnAuthorized();
		}
		return ratingDAO.addRating(r);
	}

	@Override
	public List<Rating> getRatingByUser(int userId) throws UserUnAuthorized,
			UserNotFound {
		User u = userDAO.getUserById(userId);
		if (u != null) {
			return ratingDAO.getRatingByUser(userId);
		} else {
			throw new UserUnAuthorized();
		}
	}

	@Override
	public List<Rating> getRatingByTitle(int titleId) throws MovieNotFound {
		Title t = titleDAO.getTitleById(titleId);
		if (t != null) {
			return ratingDAO.getRatingByTitle(titleId);
		} else {
			throw new MovieNotFound();
		}
	}

	@Override
	public Double getAverageRatingForTitle(int titleId) throws MovieNotFound {
		Title t = titleDAO.getTitleById(titleId);
		if (t != null) {
			return ratingDAO.getAverageRatingForTitle(titleId);
		} else {
			throw new MovieNotFound();
		}
	}

}
