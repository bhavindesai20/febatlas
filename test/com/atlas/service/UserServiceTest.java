package com.atlas.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.atlas.dao.UserDAO;
import com.atlas.dao.UserDAOImpl;
import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.User;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserServiceTest {
	
	@Mock
	private UserDAO dao;
	
	@Mock
	private CommentsDAO commentsDAO;
	
	@Mock
	private RatingDAO ratingDAO;
	
	@InjectMocks
	private UserService service = new UserServiceImpl();
	
	private User user;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setEmail("user@user.com");
		user.setFirstName("FirstName");
		user.setLastName("LastName");
		user.setPassword("Password");
		user.setId(1);
	}
	
	@Test
	public void addUser() throws UserBadRequest{
		Mockito.when(dao.getUserByEmail(user.getEmail())).thenReturn(null);
		user.setId(0);
		service.addUser(user);
		Mockito.verify(dao).addUser(user);
	}
	
	@Test(expected=UserBadRequest.class)
	public void addUserException() throws UserBadRequest{
		Mockito.when(dao.getUserByEmail(user.getEmail())).thenReturn(user);
		service.addUser(user);
		Mockito.verify(dao).addUser(user);
	}
	
	@Test(expected=UserBadRequest.class)
	public void addUserExceptionNullEmail() throws UserBadRequest{
		Mockito.when(dao.getUserByEmail(user.getEmail())).thenReturn(null);
		user.setEmail(null);
		service.addUser(user);
		Mockito.verify(dao).addUser(user);
	}
	
	@Test
	public void updateUser() throws UserNotFound{
		Mockito.when(dao.getUserById(user.getId())).thenReturn(user);
		user.setFirstName("UpdatedFirstName");
		service.updateUser(user);
		Mockito.verify(dao).updateUser(user);
	}
	
	@Test(expected=UserNotFound.class)
	public void updateUserNotExist() throws UserNotFound{
		Mockito.when(dao.getUserById(user.getId())).thenReturn(null);
		service.updateUser(user);
		Mockito.verify(dao).updateUser(user);
	}
	
	@Test(expected=UserNotFound.class)
	public void removeUserNotExist() throws UserNotFound{
		Mockito.when(dao.getUserById(user.getId())).thenReturn(null);
		service.removeUser(user.getId());
		Mockito.verify(dao).removeUser(user.getId());
	}
	
	@Test
	public void removeUserNoCommentAndRating() throws UserNotFound{
		Mockito.when(dao.getUserById(user.getId())).thenReturn(user);
		Mockito.when(commentsDAO.getCommentsForUser(user.getId())).thenReturn(null);
		Mockito.when(ratingDAO.getRatingByUser(user.getId())).thenReturn(null);
		service.removeUser(user.getId());
		Mockito.verify(dao).removeUser(user.getId());
	}
	
	@Test
	public void removeUserWithCommentAndRating() throws UserNotFound{
		List<Comments> cm = getCommentList();
		List<Rating> r = getRatingList();
		Mockito.when(dao.getUserById(user.getId())).thenReturn(user);
		Mockito.when(commentsDAO.getCommentsForUser(user.getId())).thenReturn(cm);
		Mockito.when(ratingDAO.getRatingByUser(user.getId())).thenReturn(r);
		service.removeUser(user.getId());
		Mockito.verify(dao).removeUser(user.getId());
	}
	
	@Test
	public void listuser(){
		List<User> expected = Arrays.asList(user);
		Mockito.when(dao.listUser()).thenReturn(expected);
		List<User> actual = service.listUser();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testgetUserById() throws UserNotFound{
		Mockito.when(dao.getUserById(user.getId())).thenReturn(user);
		User actual = service.getUserById(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	@Test(expected=UserNotFound.class)
	public void testgetUserByIdNotFound() throws UserNotFound{
		Mockito.when(dao.getUserById(user.getId())).thenReturn(null);
		User actual = service.getUserById(user.getId());
		Assert.assertEquals(user, actual);
	}
	
	public List<Comments> getCommentList(){
		List<Comments> cm = new ArrayList<Comments>();
		Comments c1= new Comments();
		c1.setId(1);
		c1.setTitle(null);
		c1.setComments("Hello comment 1");
		c1.setUser(user);
		cm.add(c1);
		return cm;
	} 
	
	public List<Rating> getRatingList(){
		List<Rating> rlist = new ArrayList<Rating>();
		Rating r= new Rating();
		r.setId(1);
		r.setTitle(null);
		r.setRating(5);
		r.setUser(user);
		return rlist;
	} 
	
	@Configuration
	public static class TestConfig {
		
	}
}
