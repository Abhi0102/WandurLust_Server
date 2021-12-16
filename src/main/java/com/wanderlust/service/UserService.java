package com.wanderlust.service;

import com.wanderlust.model.Users;

public interface UserService {
	public Users authenticateUser(String contactNumber, String password)throws Exception;
	public String authenticateRegistration(Users user)throws Exception;
	public Users getUserByUserId(Integer userId)throws Exception;


}
