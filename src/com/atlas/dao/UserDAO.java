package com.atlas.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.atlas.entity.User;


@Repository
public interface UserDAO {
	public void addUser(User u);

	public void updateUser(User u);

	public void removeUser(int id);
	
	public List<User> listUser();
	
	public User getUserById(int id);
	
}
