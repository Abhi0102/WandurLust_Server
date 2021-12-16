package com.wanderlust.dao;

import com.wanderlust.model.Users;

public interface UserDAO {
	public Users getUserByContactNumber(String contactNumber);
	public Boolean searchByEmailId(String emailId);
	public Boolean searchByContactNumber(String contactNumber);
	public String registerUser(Users user);
	public Users getUserByUserId(Integer userId);
}
