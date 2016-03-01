package com.atlas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.atlas.dao.CommentsDAO;
import com.atlas.dao.RatingDAO;
import com.atlas.dao.UserDAO;
import com.atlas.entity.Comments;
import com.atlas.entity.Rating;
import com.atlas.entity.User;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDAOImpl")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("commentsDAOImpl")
	private CommentsDAO commentsDAO;

	@Autowired
	@Qualifier("ratingDAOImpl")
	private RatingDAO ratingDAO;

	public UserServiceImpl() {
	}

	@Override
	public User addUser(User u) throws UserBadRequest {
		if (u.getEmail() == null || u.getFirstName() == null) {
			throw new UserBadRequest();
		}
		return userDAO.addUser(u);
	}

	@Override
	public User updateUser(User u) throws UserNotFound {
		User existing = userDAO.getUserById(u.getId());
		if (existing == null) {
			throw new UserNotFound();
		} else {
			return userDAO.updateUser(u);
		}
	}

	@Override
	public User removeUser(int id) throws UserNotFound {
		User existing = userDAO.getUserById(id);
		if (existing == null) {
			throw new UserNotFound();
		} else {
			List<Comments> commentlist= commentsDAO.getCommentsForUser(id);
			List<Rating> ratinglist= ratingDAO.getRatingByUser(id);
			if(commentlist!=null){
				commentsDAO.removeCommentsForUser(id);
			}
			if(ratinglist!=null){
				ratingDAO.removeRatingForUser(id);
			}
			return userDAO.removeUser(id);
		}
	}

	@Override
	public List<User> listUser() {
		return userDAO.listUser();
	}

	@Override
	public User getUserById(int id) throws UserNotFound {
		User existing = userDAO.getUserById(id);
		if (existing == null) {
			throw new UserNotFound();
		} else {
			return userDAO.getUserById(id);
		}
	}

	@Override
	public boolean authenticate(int id, String password) {
		return userDAO.authenticate(id, password);
	}
}
