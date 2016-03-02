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
import com.atlas.entity.Title;
import com.atlas.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CommentsDAOTest {

	@Mock
	private SessionFactory sessionFactory;

	@Mock
	private Session session;

	@Mock
	private Query query;

	@InjectMocks
	private CommentsDAO dao = new CommentsDAOImpl();

	private Comments comment;

	private Title title;

	private User user;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
		comment = new Comments();
		comment.setId(1);
		comment.setComments("I am comment for user 1 and title 1");
		comment.setTitle(setTitle());
		comment.setUser(setUser());
	}
	
	@Test
	public void testAddComment () {
		dao.addComments(title.getId(), user.getId(), comment);
		Mockito.verify(session).save(comment);
	}
	
	@Test
	public void testremoveComment () {
		Mockito.when(session.get(Comments.class, new Integer(comment.getId()))).thenReturn(comment);
		dao.removeComments(comment.getId());
		Mockito.verify(session).delete(comment);
	}
	
	@Test
	public void testremoveCommentForUser () {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(session.createQuery("from Comments where user_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",comment.getUser().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		dao.removeCommentsForUser(user.getId());
		for(Comments c : expected){
			Mockito.verify(session).delete(c);
		}
	}
	
	@Test
	public void testremoveCommentForTitle () {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(session.createQuery("from Comments where title_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",comment.getTitle().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		dao.removeCommentsForTitle(title.getId());
		for(Comments c : expected){
			Mockito.verify(session).delete(c);
		}
	}
	
	@Test
	public void testupdateComment () {
		dao.updateComment(comment);
		Mockito.verify(session).update(comment);
	}
	
	@Test
	public void testgetCommentById () {
		Mockito.when(session.get(Comments.class, new Integer(comment.getId()))).thenReturn(comment);
		Comments actual = dao.getCommentByID(comment.getId());
		Assert.assertEquals(comment, actual);
	}
	
	@Test
	public void testCommentForUser () {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(session.createQuery("from Comments where user_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",comment.getUser().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Comments> actual = dao.getCommentsForUser(comment.getUser().getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testCommentForTitle () {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(session.createQuery("from Comments where title_Id = :searchTerm")).thenReturn(query);
		Mockito.when(query.setParameter("searchTerm",comment.getTitle().getId())).thenReturn(query);
		Mockito.when(query.list()).thenReturn(expected);
		List<Comments> actual = dao.getCommentsForTitle(comment.getTitle().getId());
		Assert.assertEquals(expected, actual);
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
