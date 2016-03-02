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
import com.atlas.dao.TitleDAO;
import com.atlas.dao.UserDAO;
import com.atlas.entity.Comments;
import com.atlas.entity.Title;
import com.atlas.entity.User;
import com.atlas.exception.CommentBadRequest;
import com.atlas.exception.MovieBadRequest;
import com.atlas.exception.MovieNotFound;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CommentsServiceTest {
	
	@Mock
	private TitleDAO titleDAO;
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private CommentsDAO dao;
	
	@InjectMocks
	private CommentsService service = new CommentsServiceImpl();
	
	private Comments comment;
	
	private Title title;

	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		comment = new Comments();
		comment.setId(1);
		comment.setComments("I am comment for user 1 and title 1");
		comment.setTitle(setTitle());
		comment.setUser(setUser());
	}
	
	@Test
	public void testaddComment() throws UserNotFound, MovieNotFound, CommentBadRequest{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		service.addComments(title.getId(), user.getId(), comment);
		Mockito.verify(dao).addComments(title.getId(), user.getId(), comment);
	}
	
	@Test(expected=CommentBadRequest.class)
	public void testaddCommentWithNoComment() throws UserNotFound, MovieNotFound, CommentBadRequest{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		comment.setComments("");
		service.addComments(title.getId(), user.getId(), comment);
		Mockito.verify(dao).addComments(title.getId(), user.getId(), comment);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testaddCommentWithNoTitle() throws UserNotFound, MovieNotFound, CommentBadRequest{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(null);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		service.addComments(title.getId(), user.getId(), comment);
		Mockito.verify(dao).addComments(title.getId(), user.getId(), comment);
	}
	
	@Test(expected=UserNotFound.class)
	public void testaddCommentWithNoUser() throws UserNotFound, MovieNotFound, CommentBadRequest{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
		service.addComments(title.getId(), user.getId(), comment);
		Mockito.verify(dao).addComments(title.getId(), user.getId(), comment);
	}
	
	@Test
	public void testremoveCommentWithUser() throws UserUnAuthorized{
		service.removeComments(title.getId(), user.getId(), comment.getId());
		Mockito.verify(dao).removeComments(comment.getId());
	}
	
	@Test(expected=UserUnAuthorized.class)
	public void testremoveCommentWithNoUser() throws UserUnAuthorized{
		service.removeComments(title.getId(), 0, comment.getId());
		Mockito.verify(dao).removeComments(comment.getId());
	}
	
	@Test
	public void testupdateComment() throws MovieNotFound, CommentBadRequest, UserUnAuthorized{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		service.updateComment(title.getId(), user.getId(), comment);
		Mockito.verify(dao).updateComment(comment);
	}
	
	@Test(expected=CommentBadRequest.class)
	public void testupdateCommentwithNoComment() throws MovieNotFound, CommentBadRequest, UserUnAuthorized{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		comment.setComments(null);
		service.updateComment(title.getId(), user.getId(), comment);
		Mockito.verify(dao).updateComment(comment);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testupdateCommentWithNoTitle() throws UserUnAuthorized, MovieNotFound, CommentBadRequest{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(null);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(user);
		service.updateComment(title.getId(), user.getId(), comment);
		Mockito.verify(dao).updateComment(comment);
	}
	
	@Test(expected=UserUnAuthorized.class)
	public void testupdateCommentWithNoUser() throws UserUnAuthorized, MovieNotFound, CommentBadRequest{
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(userDAO.getUserById(user.getId())).thenReturn(null);
		service.updateComment(title.getId(), user.getId(), comment);
		Mockito.verify(dao).updateComment(comment);
	}
	
	@Test
	public void testlistcommetnByTitle() throws MovieNotFound {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(title);
		Mockito.when(dao.getCommentsForTitle(title.getId())).thenReturn(expected);
		List<Comments> actual = service.getCommentsForTitle(title.getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected=MovieNotFound.class)
	public void testlistcommetnByUnknownTitle() throws MovieNotFound {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(titleDAO.getTitleById(title.getId())).thenReturn(null);
		Mockito.when(dao.getCommentsForTitle(title.getId())).thenReturn(expected);
		List<Comments> actual = service.getCommentsForTitle(title.getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testlistcommetnByUser() throws UserUnAuthorized, UserNotFound {
		List<Comments> expected = Arrays.asList(comment);
		Mockito.when(dao.getCommentsForUser(user.getId())).thenReturn(expected);
		List<Comments> actual = service.getCommentsForUser(user.getId());
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected=UserUnAuthorized.class)
	public void testlistcommetnByUnknownUser() throws UserNotFound, UserUnAuthorized {
		Mockito.when(dao.getCommentsForUser(0)).thenReturn(null);
		service.getCommentsForUser(0);
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
