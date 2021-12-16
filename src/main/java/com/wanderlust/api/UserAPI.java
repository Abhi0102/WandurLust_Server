package com.wanderlust.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wanderlust.model.Users;
import com.wanderlust.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("UserAPI") //Check the value
public class UserAPI {
	
	@Autowired
	private UserService userService;
	@Autowired
	private Environment environment;
	
	@PostMapping(value = "login")
	public ResponseEntity<Users> autheticateUser(@RequestBody Users user)throws Exception{
		
		try {
			Users userFromService= userService.authenticateUser(user.getContactNumber(), user.getPassword());
			return new ResponseEntity<Users>(userFromService,HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,environment.getProperty(e.getMessage()));
		}
		
	}
	
	@PostMapping(value = "register")
	public ResponseEntity<String> registerUser(@RequestBody Users user)throws Exception{
		
		try {
			String userName= "Welcome "+userService.authenticateRegistration(user)+environment.getProperty("UserAPI.Welcome");
			return new ResponseEntity<String>(userName,HttpStatus.OK);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, environment.getProperty(e.getMessage()));
		}
	}
	
	@GetMapping(value = "/user/{userId}")
	public ResponseEntity<Users> userProfile(@PathVariable("userId") Integer userId)throws Exception{
		System.out.println("In Get Mapping");
		try {
			
			Users userFromService=userService.getUserByUserId(userId);
			return new ResponseEntity<Users>(userFromService,HttpStatus.OK);
			
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
	}
	

}
