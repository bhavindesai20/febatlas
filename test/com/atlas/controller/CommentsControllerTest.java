package com.atlas.controller;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;
import com.atlas.service.CommentsService;
import com.atlas.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommentsControllerTest {
	
	@Mock
	private CommentsService service;
	
	@InjectMocks
	private CommentsController controller;

	private MockMvc mockMvc;

	private Comments comment;
	
	private User user;
	
	private Title title;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		comment = new Comments();
		comment.setId(0);
		comment.setComments("I am comment for user 1 and title 1");
		comment.setTitle(setTitle());
		comment.setUser(setUser());
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testaddComment() throws Exception {
		String requestBody = new ObjectMapper().writeValueAsString(comment);
		mockMvc.perform(MockMvcRequestBuilders.post("/comments/"+title.getId()+"/"+user.getId())
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).addComments(Mockito.eq(title.getId()),Mockito.eq(user.getId()),Mockito.any(Comments.class));
	}
	
	@Test
	public void testaddCommentException() throws Exception {
		Mockito.when(service.addComments(Mockito.eq(title.getId()),Mockito.eq(user.getId()),Mockito.any(Comments.class))).thenThrow(
				new UserNotFound());
		String requestBody = new ObjectMapper().writeValueAsString(comment);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/comments/"+title.getId()+"/"+user.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody)).andExpect(
				MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testgetCommentForUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comments/user/" + user.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getCommentsForUser(user.getId());
	}
	
	@Test
	public void testgetCommentForTitle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/comments/title/" + title.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getCommentsForTitle(title.getId());
	}
	
	@Test
	public void testUpdateComment() throws Exception {
		String requestBody = new ObjectMapper().writeValueAsString(comment);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/comments/"+title.getId()+"/"+user.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).updateComment(Mockito.eq(title.getId()),Mockito.eq(user.getId()),Mockito.any(Comments.class));
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
