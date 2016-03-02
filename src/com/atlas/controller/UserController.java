package com.atlas.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.entity.User;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;
import com.atlas.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public User addUser(@RequestBody User u) throws UserBadRequest {
		return this.userService.addUser(u);
	}

	@RequestMapping(method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@RequestBody User u) throws UserNotFound {
		return this.userService.updateUser(u);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User removeUser(@PathVariable("id") int id) throws UserNotFound {
		return this.userService.removeUser(id);
	}

	@RequestMapping(method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> listUser() {
		return this.userService.listUser();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserById(@PathVariable("id") int id) throws UserNotFound {
		return this.userService.getUserById(id);
	}
	
	@RequestMapping(value = "{email}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserByEmail(@PathVariable("email") String email) throws UserNotFound {
		return this.userService.getUserByEmail(email);
	}
	
	@RequestMapping(value="/email/{password}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String login(@RequestParam(required=true,value="email") String email,
			@PathVariable("password") String password) throws ServletException, UserNotFound{
		return this.userService.login(email, password);
		}

}
