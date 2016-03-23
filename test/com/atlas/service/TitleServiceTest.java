package com.atlas.service;

import java.util.ArrayList;
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

import com.atlas.dao.CommentsDAO;
import com.atlas.dao.RatingDAO;
import com.atlas.dao.TitleDAO;
import com.atlas.dao.UserDAO;
import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.MovieBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TitleServiceTest {
	
	@Mock
	private TitleDAO dao;
	
	@Mock
	private CommentsDAO commentsDAO;
	
	@Mock
	private RatingDAO ratingDAO;
	
	@InjectMocks
	private TitleService service = new TitleServiceImpl();
	
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
	}
	
	@Test
	public void testaddTitle() throws MovieBadRequest{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(null);
		service.addTitle(title);
		Mockito.verify(dao).addTitle(title);
	}
	
	@Test(expected=MovieBadRequest.class)
	public void testaddTitleWithExistingTitle() throws MovieBadRequest{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(title);
		service.addTitle(title);
		Mockito.verify(dao).addTitle(title);
	}
	
	@Test(expected=MovieBadRequest.class)
	public void testaddTitleWithNoTitle() throws MovieBadRequest{
		title.setTitle(null);
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(title);
		service.addTitle(title);
		Mockito.verify(dao).addTitle(title);
	}
	
	@Test
	public void testupdateTitleWithExistingTitle() throws MovieNotFound{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(title);
		service.updateTitle(title);
		Mockito.verify(dao).updateTitle(title);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testupdateTitleWithNoTitle() throws MovieNotFound{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(null);
		service.updateTitle(title);
		Mockito.verify(dao).updateTitle(title);
	}
	
	@Test
	public void  testRemoveTitle() throws MovieNotFound{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(commentsDAO.getCommentsForTitle(title.getId())).thenReturn(null);
		Mockito.when(ratingDAO.getRatingByTitle(title.getId())).thenReturn(null);
		service.removeTitle(title.getId());
		Mockito.verify(dao).removeTitle(title.getId());
	}
	
	@Test(expected=MovieNotFound.class)
	public void  testRemoveTitleNotAvailable() throws MovieNotFound{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(null);
		Mockito.when(commentsDAO.getCommentsForTitle(title.getId())).thenReturn(null);
		Mockito.when(ratingDAO.getRatingByTitle(title.getId())).thenReturn(null);
		service.removeTitle(title.getId());
		Mockito.verify(dao).removeTitle(title.getId());
	}
	
	@Test
	public void removeTitleWithCommentAndRating() throws MovieNotFound{
		List<Comments> cm = getCommentList();
		List<Rating> r = getRatingList();
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(commentsDAO.getCommentsForTitle(title.getId())).thenReturn(cm);
		Mockito.when(ratingDAO.getRatingByTitle(title.getId())).thenReturn(r);
		service.removeTitle(title.getId());
		Mockito.verify(dao).removeTitle(title.getId());
	}
	
	@Test
	public void listtitle(){
		List<Title> expected = Arrays.asList(title);
		Mockito.when(dao.listTitles()).thenReturn(expected);
		List<Title> actual = service.listTitles();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testgetTitlesById() throws MovieNotFound{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(title);
		Title actual = service.getTitleById(title.getId());
		Assert.assertEquals(title, actual);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testgetTitlesByIdNotFound() throws MovieNotFound{
		Mockito.when(dao.getTitleById(title.getId())).thenReturn(null);
		Title actual = service.getTitleById(title.getId());
		Assert.assertEquals(title, actual);
	}
	
	@Test
	public void testlistTitlesBySearch() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(dao.getTitleBySearchTerm(title.getTitle())).thenReturn(expected);
		List<Title> actual = service.getTitleBySearchTerm(title.getTitle());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistTitlesByYear() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(dao.getTitleByYear(title.getYear())).thenReturn(expected);
		List<Title> actual = service.getTitleByYear(title.getYear());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistTitlesByType() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(dao.getTitleByType(title.getType())).thenReturn(expected);
		List<Title> actual = service.getTitleByType(title.getType());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistTitlesByGenre() {
		List<Title> expected = Arrays.asList(title);
		Mockito.when(dao.getTitleByGenre(title.getGenre())).thenReturn(expected);
		List<Title> actual = service.getTitleByGenre(title.getGenre());
		Assert.assertEquals(expected, actual);
	}
	
	public List<Comments> getCommentList(){
		List<Comments> cm = new ArrayList<Comments>();
		Comments c1= new Comments();
		c1.setId(1);
		c1.setTitle(null);
		c1.setComments("Hello comment 1");
		c1.setUser(null);
		cm.add(c1);
		return cm;
	} 
	
	public List<Rating> getRatingList(){
		List<Rating> rlist = new ArrayList<Rating>();
		Rating r= new Rating();
		r.setId(1);
		r.setTitle(null);
		r.setRating(5);
		r.setUser(null);
		return rlist;
	} 
	
	@Configuration
	public static class TestConfig {
		
	}
}
