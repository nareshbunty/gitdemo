/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.happybus.beans.Agent;
import com.happybus.beans.Response;
import com.happybus.integration.dao.AgentDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.security.PasswordGenerator;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;

/**
 * AgentSrviceImpl is used perform the business operations on RegisterAgent
 * @author srinath
 * @since 1.0
 *
 */
@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentDAO agentDAO;
	@Autowired
	private UserSmsService userSmsService;
	@Autowired
	private UserEmailService userEmailService;

	@Autowired
	private UserAuthenticationService userAuthenticationService;
	private static Logger logger = Logger.getLogger(AgentServiceImpl.class);

	/**
	 * registerAgent() method is used to perform bussiness operations on Agent Table
	 * @param String
	 * @param Object
	 * @return String
	 * @author srinath
	 * @since 1.0
	 */
	@Override
	public String registerAgent(String jsonAgent, Long userId, Object token) {
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("agent Registration is Failure!Please Try Again.");
		logger.info("Entered into registeragent() " + jsonAgent + " " + userId + " " + token);
		try {
			// if(userAuthenticationService.isAuthenticated((Long)userId,(String)token)){
			Agent agent = JsonUtil.convertJsonToJava(jsonAgent, Agent.class);
			if (agent != null && agent.getBankDetails() != null && agent.getUser() != null) {
				agent.setStatus((byte)1);
				agent.getUser().setStatus((byte)1);
				agent.getUser().setUserRole(RolesConstants.ROLE_AGENT);
				//agent.getUser().setEmail(agent.getUser().getEmail());

				// generate the Password
				String password = PasswordGenerator.generatePassword();
				agent.getUser().setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
				Long agentId = (Long) agentDAO.registerAgent(agent);
				if (agentId != null && agentId > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("agent Registered Successfully");
					jsonAgent=JsonUtil.convertJavaToJson(agentId);
					response.setData(jsonAgent);
					// send sms & email to agent_admin (username,password for
					// login)
					String msg = "Dear user use this  UserName : " + agent.getUser().getUserName()
							+ "\n and Password : " + password + " for login." + ". Thank you for registration.";
					userSmsService.sendSms(agent.getUser().getMobile(), msg);
					String subject = "Welcome to HappyBus";
					String body = "Dear user use this  UserName : " + agent.getUser().getUserName()
							+ "\n and Password : " + password + " for login." + ". Thank you for registration.";
					userEmailService.sendEmail(agent.getUser().getEmail(), subject, body);
				}
			}
			// }
		} catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage(e.getMessage());
			logger.error("Exception Occured while Registering the agent :: " + e.getMessage());
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while Registering the agent :: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while Registering the agent :: " + e.getMessage());
		}
		String jsonRespone = JsonUtil.convertJavaToJson(response);
		logger.info("Response from registeragent() : " + jsonRespone);
		return jsonRespone;
	}
	
	/**
	 * searchAgents() method is used to perform bussiness operations on Agent Table
	 * @param String
	 * @return String
	 * @author Anil ch
	 * @since 1.0
	 */
	@Override
	public String searchAgents(Long agentId,String phoneNumber1,Long userId,String token,String userRole) {
		Response response=new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("agent is not available!Please Try Again.");
		logger.info("Entered into searchAgents() " +agentId+"  "+phoneNumber1);
	 try{
		// if(userAuthenticationService.isAuthenticated(userId,token){ 
		List<Agent> agents=agentDAO.searchAgents(agentId,phoneNumber1,userId,userRole);
		if(agents!=null&&agents.isEmpty()==false){
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("The Agent is");
		String jsonAgent=JsonUtil.convertJavaToJson(agents);
		response.setData(jsonAgent);
		//}
		}
	 }catch(DataAccessException de){
		response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
		response.setMessage("Unable to process your request!Please Try Again.");
		logger.error("Exception Occured while searching the agent :: " + de.toString());
	 }
	 catch(Exception e){
		 response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while searching the agent :: " + e.toString());
	 }
	 String jsonResponse=JsonUtil.convertJavaToJson(response);
	 logger.info("Response from searchAgents() : " + jsonResponse);
		return jsonResponse;
	}

	/**
	 * searchAgents() method is used to perform bussiness operations on Agent Table
	 * @param String
	 * @return String
	 * @author Anil ch
	 * @since 1.0
	 */
	@Override
	public String updateAgents(String jsonAgent) {
		Response response=new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("agent is not available!Please Try Again.");
		logger.info("Entered into searchAgents() " +jsonAgent);
	 try{
		 Agent agent=JsonUtil.convertJsonToJava(jsonAgent, Agent.class);
		if (agent != null){ 
		Long agentId=(Long)agentDAO.updateAgent(agent);
		if (agentId != null && agentId > 0) {
			response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
			response.setMessage("agent updated Successfully");
			jsonAgent=JsonUtil.convertJavaToJson(agentId);
			response.setData(jsonAgent);
		 }
		}
	 }catch(DataAccessException de){
		response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
		response.setMessage("Unable to process your request!Please Try Again.");
		logger.error("Exception Occured while updating the agent :: " + de.toString());
	 }
	 catch(Exception e){
		 response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while updating the agent :: " + e.toString());
	 }
	 String jsonResponse=JsonUtil.convertJavaToJson(response);
	 logger.info("Response from updateAgents() : " + jsonResponse);
		return jsonResponse;
	}

}
