package com.atlas.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RatingDAOTest {
	
	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private Query query;

	@InjectMocks
	private RatingDAO dao = new RatingDAOImpl();

	private Rating rating;
	
	private Rating ratingone;

	private Title title;

	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		rating = new Rating();
		rating.setId(1);
		rating.setRating(5);
		rating.setTitle(setTitle());
		rating.setUser(setUser());
	}
	
	@Test
	public void testAddRating() {
		dao.addRating(rating);
		Mockito.verify(session).persist(rating);
	}
	
	@Test
	public void testRatingForUser () {
		List<Rating> expected = Arrays.asList(rating);
		Mockito.when(session.createQuery("from Rating where user_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",rating.getUser().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Rating> actual = dao.getRatingByUser(rating.getUser().getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testRatingForTitle () {
		List<Rating> expected = Arrays.asList(rating);
		Mockito.when(session.createQuery("from Rating where title_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",rating.getTitle().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Rating> actual = dao.getRatingByTitle(rating.getTitle().getId());
		Assert.assertEquals(expected, actual);
	}
	
	/*@Test
	public void testAVGRatingForTitle () {
		ratingone = new Rating();
		ratingone.setId(2);
		ratingone.setRating(3);
		ratingone.setTitle(setTitle());
		ratingone.setUser(setUser());
		
		Double expectedAvg = 4.0;
		
		List<Rating> expected = Arrays.asList(rating,ratingone);
		
		Mockito.when(session.createQuery("select AVG(rating) from Rating where title_Id = :searchTerm group by title_Id")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",rating.getTitle().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		Double actual = dao.getAverageRatingForTitle(rating.getTitle().getId());
		Assert.assertEquals(expectedAvg, actual);
	}*/
	
	@Test
	public void testremoveRatingForUser () {
		List<Rating> expected = Arrays.asList(rating);
		Mockito.when(session.createQuery("from Rating where user_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",rating.getUser().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		dao.removeRatingForUser(user.getId());
		for(Rating r : expected){
			Mockito.verify(session).delete(r);
		}
	}
	
	@Test
	public void testremoveRatingForTitle () {
		List<Rating> expected = Arrays.asList(rating);
		Mockito.when(session.createQuery("from Rating where title_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",rating.getTitle().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		dao.removeRatingForTitle(title.getId());
		for(Rating r : expected){
			Mockito.verify(session).delete(r);
		}
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
