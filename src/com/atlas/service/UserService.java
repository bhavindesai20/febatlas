package com.atlas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.atlas.entity.User;

@Service
public interface UserService {
	public void addUser(User u);

	public User updateUser(User u);

	public User removeUser(int id);

	public List<User> listUser();

	public User getUserById(int id);
}
