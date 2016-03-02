package com.atlas.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atlas.entity.User;


@Repository
public interface UserDAO {
	public User addUser(User u);

	public User updateUser(User u);

	public User removeUser(int id);
	
	public List<User> listUser();
	
	public User getUserById(int id);
	
	public User getUserByEmail(String email);

	public boolean login(String email, String password);
	
}
