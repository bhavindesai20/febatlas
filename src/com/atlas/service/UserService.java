package com.atlas.service;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Service;

import com.atlas.entity.User;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;

@Service
public interface UserService {
	public User addUser(User u) throws UserBadRequest;

	public User updateUser(User u) throws UserNotFound;

	public User removeUser(int id) throws UserNotFound;

	public List<User> listUser();

	public User getUserById(int id) throws UserNotFound;
	
	public User login(String email, String password) throws UserUnAuthorized;
	
	public User getUserByEmail(String email) throws UserNotFound; 
	
}
