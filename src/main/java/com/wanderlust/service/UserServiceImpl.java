package com.wanderlust.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanderlust.dao.UserDAO;
import com.wanderlust.model.Users;
import com.wanderlust.validator.UserValidator;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	public Users authenticateUser(String contactNumber, String password) throws Exception {
		// Make a validator
		Users userFromDAO = userDAO.getUserByContactNumber(contactNumber);
		if (userFromDAO != null) {
			if (userFromDAO.getPassword().equals(password)) {
				userFromDAO.setPassword(null);
				return userFromDAO;
			}
			throw new Exception("UserService.Invalid_Password"); // Password Doesn't Matches
		}
		throw new Exception("UserService.Invalid_Contact_Number"); // Invalid Contact Number
	}

	@Override
	public String authenticateRegistration(Users user) throws Exception {
		// Create Validator
		UserValidator.validateForRegistration(user);
		Boolean checkContactNumberAvailability = userDAO.searchByContactNumber(user.getContactNumber());
		System.out.println(checkContactNumberAvailability);
		if (!checkContactNumberAvailability) {
			Boolean checkEmailIdAvailability = userDAO.searchByEmailId(user.getEmailId());
			if (!checkEmailIdAvailability) {
				String userName = userDAO.registerUser(user);
				return userName;
				// Update User In DB
			} else {
				throw new Exception("UserService.Email_Available"); // Email ID Available in DB
			}
		} else {
			throw new Exception("UserService.ContactNumber_Available"); // Contact Number Available in DB
		}

	}
	
	@Override
	public Users getUserByUserId(Integer userId)throws Exception {
		Users userFromDAO=userDAO.getUserByUserId(userId);
		if(userFromDAO!=null) {
			return userFromDAO;
		}
		throw new Exception("UserService.User_Id_Unavailable");
	}

}
