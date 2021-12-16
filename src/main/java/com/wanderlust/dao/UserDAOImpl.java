package com.wanderlust.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.wanderlust.entity.UserEntity;
import com.wanderlust.model.Users;

@Repository(value = "userDAO")
public class UserDAOImpl implements UserDAO {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Users getUserByContactNumber(String contactNumber) {
		Query query= entityManager.createQuery("select c from UserEntity c where c.contactNumber = :contactNumber");
		query.setParameter("contactNumber", contactNumber);
		List<UserEntity> userEntity=query.getResultList();
		Users user=null;
		if(!userEntity.isEmpty()) {
			user= new Users();
			user.setContactNumber(userEntity.get(0).getContactNumber());
			user.setEmailId(userEntity.get(0).getEmailId());
			user.setPassword(userEntity.get(0).getPassword());
			user.setUserId(userEntity.get(0).getUserId());
			user.setUserName(userEntity.get(0).getUserName());
			
		}
		
		return user;
	}
	
	@Override
	public Boolean searchByEmailId(String emailId) {
		
		Query query= entityManager.createQuery("select c from UserEntity c where c.emailId = :emailId");
		query.setParameter("emailId", emailId);
		List<UserEntity> userEntity=query.getResultList();
		if(!userEntity.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public Boolean searchByContactNumber(String contactNumber) {
		Query query= entityManager.createQuery("select c from UserEntity c where c.contactNumber = :contactNumber");
		query.setParameter("contactNumber", contactNumber);
		List<UserEntity> userEntity=query.getResultList();
		if(!userEntity.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public String registerUser(Users user) {
		
		String registeredUserName = null;

		UserEntity userEntity = new UserEntity();

		userEntity.setEmailId(user.getEmailId());
		userEntity.setUserName(user.getUserName());
		userEntity.setPassword(user.getPassword());
		userEntity.setContactNumber(user.getContactNumber());
		
		entityManager.persist(userEntity);
		
		registeredUserName = userEntity.getUserName();
		
		return registeredUserName;
		
	}
	
	@Override 
	public Users getUserByUserId(Integer userId) {
		
		UserEntity userEntity=entityManager.find(UserEntity.class, userId);
		Users user=null;
		if(userEntity!=null) {
			user=new Users();
			user.setContactNumber(userEntity.getContactNumber());
			user.setEmailId(userEntity.getEmailId());
			user.setPassword(userEntity.getPassword());
			user.setUserId(userEntity.getUserId());
			user.setUserName(userEntity.getUserName());
			
		}
		return user;
	}
	


}
