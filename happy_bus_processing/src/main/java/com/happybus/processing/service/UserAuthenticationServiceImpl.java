package com.happybus.processing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happybus.integration.dao.UserAuthenticationDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;

@Service
public class UserAuthenticationServiceImpl 
implements UserAuthenticationService{
	@Autowired
private UserAuthenticationDAO userAuthenticationDAO;
	@Override
	public boolean isAuthenticated(Long userId, String token) {
		Integer count=userAuthenticationDAO.checkAuthentication(userId,token,0);
		if(count!=null && count>0){
			return true;
		}
		else{
throw new UserAuthenticationException("Your are not authenticated user to access service ");			
		}
	}

}
