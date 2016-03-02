package com.atlas.controller;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;
import com.atlas.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RatingControllerTest {
	
	@Mock
	private RatingService service;
	
	@InjectMocks
	private RatingController controller;

	private MockMvc mockMvc;

	private Rating rating;
	
	private User user;
	
	private Title title;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		rating = new Rating();
		rating.setId(1);
		rating.setRating(5);
		rating.setTitle(setTitle());
		rating.setUser(setUser());
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testaddRating() throws Exception {
		String requestBody = new ObjectMapper().writeValueAsString(rating);
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/"+title.getId()+"/"+user.getId())
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).addRating(Mockito.eq(user.getId()),Mockito.eq(title.getId()),Mockito.any(Rating.class));
	}
	
	@Test
	public void testaddRatingException() throws Exception {
		Mockito.when(service.addRating(Mockito.eq(user.getId()),Mockito.eq(title.getId()),Mockito.any(Rating.class))).thenThrow(
				new UserUnAuthorized());
		String requestBody = new ObjectMapper().writeValueAsString(rating);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/rating/1/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody)).andExpect(
				MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void testgetRatingForUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/user/" + user.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getRatingByUser(user.getId());
	}
	
	@Test
	public void testgetRatingForUserNotFound() throws Exception {
		Mockito.when(service.getRatingByUser(Mockito.eq(user.getId()))).thenThrow(
				new UserNotFound());
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/user/" + user.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testgetRatingForTitle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/title/" + title.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getRatingByTitle(title.getId());
	}
	
	@Test
	public void testgetRatingForTitleNotFound() throws Exception {
		Mockito.when(service.getRatingByTitle(Mockito.eq(title.getId()))).thenThrow(
				new MovieNotFound());
		mockMvc.perform(MockMvcRequestBuilders.get("/rating/title/" + title.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
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
