package com.atlas.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atlas.entity.Rating;

@Repository
public interface RatingDAO {

	public Rating addRating(Rating r);

	public List<Rating> getRatingByUser(int userId);

	public List<Rating> getRatingByTitle(int titleId);

	public Double getAverageRatingForTitle(int titleId);

	public void removeRatingForUser(int userId);

	public void removeRatingForTitle(int titleId);
	
	public List<Object> getTopRatedTitle();
}
