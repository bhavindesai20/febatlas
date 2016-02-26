package com.atlas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.entity.Rating;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;
import com.atlas.service.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingController {

	@Autowired
	@Qualifier("ratingServiceImpl")
	private RatingService ratingServiceImpl;

	@RequestMapping(value = "{titleId}/{userId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Rating addRating(@PathVariable("titleId") int titleId,
			@PathVariable("userId") int userId, @RequestBody Rating r)
			throws UserUnAuthorized, MovieNotFound {
		return this.ratingServiceImpl.addRating(userId, titleId, r);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Rating> getRatingForUser(@PathVariable("userId") int userId)
			throws UserUnAuthorized, UserNotFound {
		return this.ratingServiceImpl.getRatingByUser(userId);
	}

	@RequestMapping(value = "/title/{titleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Rating> getRatingForTitle(@PathVariable("titleId") int titleId)
			throws MovieNotFound {
		return this.ratingServiceImpl.getRatingByTitle(titleId);
	}

	@RequestMapping(value = "/title/{titleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Double getAvgRatingForTitle(@PathVariable("titleId") int titleId)
			throws MovieNotFound {
		return this.ratingServiceImpl.getAverageRatingForTitle(titleId);
	}

}
