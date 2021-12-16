package com.wanderlust.validator;

import com.wanderlust.model.Users;

public class UserValidator {
	
	public static void validateForRegistration(Users user)throws Exception {
		
		if(!validateContactNumber(user.getContactNumber()))
			throw new Exception("UserValidator.Invalid_Contact_Number");
//		else if(!validateEmailId(user.getEmailId()))
//			throw new Exception("UserValidator.Invalid_Email_Id");
		else if(!validatePassword(user.getPassword()))
			throw new Exception("UserValidator.Invalid_Password");
		
	}
	
	public static Boolean validateEmailId(String emailId) {
		if(emailId.matches("[a-zA-Z0-9._]+@[a-zA-Z]{2,}\\\\.[a-zA-Z][a-zA-Z.]+")) {
			return true;
		}
		return false;
	}
	
	public static Boolean validateContactNumber(String contactNumber) {
		if(contactNumber.matches("[6-9][0-9]{9}")) {
			return true;
		}
		return false;
	}
	
	public static Boolean validatePassword(String password) {
		if(password.length()>=7 && password.length()<=20) {
			return true;
		}
		return false;
	}

}
