package com.atlas.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atlas.dao.UserDAO;
import com.atlas.entity.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	public UserServiceImpl() {}

	@Override
	public void addUser(User u) {
		String password=u.getPassword();
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		u.setPassword(hashed);
		userDAO.addUser(u);
	}

	@Override
	public User updateUser(User u) {
		return userDAO.updateUser(u);

	}

	@Override
	public User removeUser(int id) {
		return userDAO.removeUser(id);

	}

	@Override
	public List<User> listUser() {
		return userDAO.listUser();
	}

	@Override
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}

	@Override
	public boolean authenticate(int id, String password) {
		return userDAO.authenticate(id, password);
	}

}
