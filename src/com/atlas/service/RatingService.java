package com.atlas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atlas.entity.Rating;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@Service
public interface RatingService {
	
	public Rating addRating(int userId,int titleId,Rating r) throws UserUnAuthorized, MovieNotFound;

	public List<Rating> getRatingByUser(int userId) throws UserUnAuthorized, UserNotFound;

	public List<Rating> getRatingByTitle(int titleId)throws MovieNotFound;

	public Double getAverageRatingForTitle(int titleId) throws MovieNotFound;
	
	public List<Object> getTopRatedTitle();
	
	public Rating getRatingByUserForTitle(int userId, int titleId) throws UserNotFound, MovieNotFound;
}
