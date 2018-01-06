package com.cba.processing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import com.cba.dao.UserAuthenticationDAO;

@Service
public class UserServiceImpl 
implements UserService{
	@Autowired
private UserAuthenticationDAO userAuthenticationDAO;
	@Override
	public Integer getEmployeeId(String userName) {
	Integer employeeId=userAuthenticationDAO.getEmployeeId(userName);
		return employeeId;
	}

}
