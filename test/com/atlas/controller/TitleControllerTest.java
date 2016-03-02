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

import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.MovieBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;
import com.atlas.service.TitleService;
import com.atlas.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TitleControllerTest {
	
	@Mock
	private TitleService service;
	
	@InjectMocks
	private TitleController controller;

	private MockMvc mockMvc;

	private Title title;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
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
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testlistTitle() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/titles")).andExpect(
				MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).listTitles();
	}
	
	@Test
	public void testgetTitleById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/titles/" + title.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getTitleById(title.getId());
	}
	
	@Test
	public void testGetTitleByIdNotFound() throws Exception {
		Mockito.when(service.getTitleById(1)).thenThrow(new MovieNotFound());
		mockMvc.perform(MockMvcRequestBuilders.get("/titles/1"))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testaddTitle() throws Exception {
		String requestBody = new ObjectMapper().writeValueAsString(title);
		mockMvc.perform(MockMvcRequestBuilders.post("/titles")
			   .contentType(MediaType.APPLICATION_JSON_VALUE)
			   .content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).addTitle(Mockito.any(Title.class));
	}
	
	@Test
	public void testaddTitleException() throws Exception {
		Mockito.when(service.addTitle(Mockito.any(Title.class))).thenThrow(	new MovieBadRequest());
		String requestBody = new ObjectMapper().writeValueAsString(title);
		mockMvc.perform(
				MockMvcRequestBuilders.post("/titles")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody)).andExpect(
				MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void testUpdateTitle() throws Exception {
		String requestBody = new ObjectMapper().writeValueAsString(title);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/titles")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody)).andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).updateTitle(Mockito.any(Title.class));
	}
	
	@Test
	public void testupdateTitleException() throws Exception {
		Mockito.when(service.updateTitle(Mockito.any(Title.class))).thenThrow(new MovieNotFound());
		String requestBody = new ObjectMapper().writeValueAsString(title);
		mockMvc.perform(
				MockMvcRequestBuilders.put("/titles")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(requestBody)).andExpect(
				MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testRemoveTitle() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/titles/" + title.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).removeTitle(Mockito.eq(title.getId()));
	}
	
	@Test
	public void testRemoveUserException() throws Exception {
		Mockito.when(service.removeTitle(Mockito.eq(title.getId()))).thenThrow(new MovieNotFound());
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/titles/" + title.getId())
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(	MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void testlistTitleBySearch() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/titles/titlefilter?title="+title.getTitle())).andExpect(
				MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getTitleBySearchTerm(title.getTitle());
	}
	
	@Test
	public void testlistTitleByYear() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/titles/yearfilter?year="+title.getYear())).andExpect(
				MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getTitleByYear(title.getYear());
	}
	
	@Test
	public void testlistTitleByGenre() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/titles/genrefilter?genre="+title.getGenre())).andExpect(
				MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getTitleByGenre(title.getGenre());
	}
	
	@Test
	public void testlistTitleByType() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/titles/typefilter?type="+title.getType())).andExpect(
				MockMvcResultMatchers.status().isOk());
		Mockito.verify(service).getTitleByType(title.getType());
	}
	
	@Configuration
	public static class TestConfig {

	}
}
