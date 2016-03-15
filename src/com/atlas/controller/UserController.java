package com.atlas.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.entity.User;
import com.atlas.entity.UserLogin;
import com.atlas.exception.UserBadRequest;
import com.atlas.exception.UserNotFound;
import com.atlas.exception.UserUnAuthorized;
import com.atlas.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/users",method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public User addUser(@RequestBody User u) throws UserBadRequest {
		return this.userService.addUser(u);
	}

	@RequestMapping(value="/api/users",method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@RequestBody User u) throws UserNotFound {
		return this.userService.updateUser(u);
	}

	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User removeUser(@PathVariable("id") int id) throws UserNotFound {
		return this.userService.removeUser(id);
	}

	@RequestMapping(value = "/api/users",method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> listUser() {
		return this.userService.listUser();
	}

	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserById(@PathVariable("id") int id) throws UserNotFound {
		return this.userService.getUserById(id);
	}
	
	@RequestMapping(value = "/api/users/email/{email}/", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public User getUserByEmail(@PathVariable("email") String email) throws UserNotFound {
		return this.userService.getUserByEmail(email);
	}
	
	@RequestMapping(value = "/users/login", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE, 
			consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoginResponse userlogin(@RequestBody final UserLogin login)
        throws UserUnAuthorized {
    	User u = userService.login(login.getEmail(),login.getPassword());
        if (u == null) {
            throw new UserUnAuthorized();
        }
        return new LoginResponse(Jwts.builder().setSubject(login.getEmail())
            .claim("roles", "user").setIssuedAt(new Date())
            .signWith(SignatureAlgorithm.HS256, "febatlas").compact());
    }
	
	@SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;
        public LoginResponse(final String token) {
            this.token = token;
        }
    }
    
	
}
