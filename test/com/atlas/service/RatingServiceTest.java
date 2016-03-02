package com.atlas.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atlas.dao.CommentsDAO;
import com.atlas.dao.RatingDAO;
import com.atlas.dao.TitleDAO;
import com.atlas.dao.UserDAO;
import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.CommentBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RatingServiceTest {
	
	@Mock
	private TitleDAO titleDAO;
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private RatingDAO dao;
	
	@InjectMocks
	private RatingService service = new RatingServiceImpl();
	
	private Rating rating;
	
	private Title title;

	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		rating = new Rating();
		rating.setId(1);
		rating.setRating(5);
		rating.setTitle(setTitle());
		rating.setUser(setUser());
	}
	
	@Test
	public void testaddRating() throws UserUnAuthorized, MovieNotFound{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		service.addRating(title.getId(), user.getId(), rating);
		Mockito.verify(dao).addRating(rating);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testaddRatingWithNoTitle() throws UserUnAuthorized, MovieNotFound{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(null);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		service.addRating(title.getId(), user.getId(), rating);
		Mockito.verify(dao).addRating(rating);
	}
	
	@Test(expected=UserUnAuthorized.class)
	public void testaddCommentWithNoUser() throws UserUnAuthorized, MovieNotFound{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
		service.addRating(title.getId(), user.getId(), rating);
		Mockito.verify(dao).addRating(rating);
	}
	
	@Test
	public void testlistratingByTitle() throws MovieNotFound {
		List<Rating> expected = Arrays.asList(rating);
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(dao.getRatingByTitle(title.getId())).thenReturn(expected);
		List<Rating> actual = service.getRatingByTitle(title.getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testlistratingByNoTitle() throws MovieNotFound {
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(null);
	    service.getRatingByTitle(title.getId());
	}
	
	@Test
	public void testlistratingByUser() throws UserUnAuthorized,	UserNotFound {
		List<Rating> expected = Arrays.asList(rating);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		Mockito.when(dao.getRatingByUser(user.getId())).thenReturn(expected);
		List<Rating> actual = service.getRatingByUser(user.getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected=UserUnAuthorized.class)
	public void testlistratingByNo() throws UserUnAuthorized,UserNotFound {
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
	    service.getRatingByUser(user.getId());
	}
	
	public User setUser() {
		user = new User();
		user.setEmail("user@user.com");
		user.setFirstName("FirstName");
		user.setLastName("LastName");
		user.setPassword("Password");
		user.setId(1);
		return user;
	}

	public Title setTitle() {
		title = new Title();
		title.setId(1);
		title.setActors("Actor-1,Actor-2");
		title.setAwards("Award-1,Award-2");
		title.setCountry("India");
		title.setDirector("Director-1");
		title.setGenre("Action");
		title.setImdbId("tt884455rt");
		title.setImdbRating(4.2);
		title.setImdbVotes(11223344);
		title.setLanguage("Hindi");
		title.setMetaScore(6);
		title.setPlot("This is plot");
		title.setPoster("This is poster");
		title.setRated("R");
		title.setReleased(new Date());
		title.setRuntime(22);
		title.setTitle("Movie title");
		title.setType("Movie");
		title.setWriter("Writer-1");
		title.setYear(2002);
		return title;
	}
	
	@Configuration
	public static class TestConfig {
		
	}
}
