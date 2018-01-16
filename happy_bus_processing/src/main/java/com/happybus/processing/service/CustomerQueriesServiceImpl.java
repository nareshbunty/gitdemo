package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.happybus.beans.CustomerSupport;
import com.happybus.beans.IssueType;
import com.happybus.beans.Response;
import com.happybus.beans.User;
import com.happybus.integration.dao.CustomerQueriesDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.security.PasswordGenerator;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;

@Service
public class CustomerQueriesServiceImpl implements CustomerQueriesService {

	

	private static Logger logger = Logger.getLogger(CustomerQueriesServiceImpl.class);

	@Autowired
	private CustomerQueriesDAO customerQueriesDAO;

	@Autowired
	private UserSmsService userSmsService;

	@Autowired
	private UserEmailService userEmailService;
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	
	/**
	 * the method contain register customerSupport
	 * 
	 * @param jsonUser, createdBy
	 * @return jsonResponse
	 * @author naveen
	 */

	public String registerCustomerSupport(String jsonUser,Long userId,String token) {

		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("CustomerSupport Registration is Failure!Please Try Again.");
		logger.info("Entered into registersupport() " +jsonUser);
		

		
		//System.out.println("before if custom supp"+user);
		try {
			User user = JsonUtil.convertJsonToJava(jsonUser,User.class);
		
			if(userAuthenticationService.isAuthenticated(userId,token)){
				logger.info("Entered into authenticcation " +jsonUser);
			if (user!=null) {
				
				logger.info("Entered into user!=null " +jsonUser);
           System.out.println("entered inside register support");
				user.setUserRole(RolesConstants.ROLE_CUSTOMER_SUPPORT);

				// generate the Password
				String password = PasswordGenerator.generatePassword();
				user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
				Long userId1= (Long) customerQueriesDAO.registerCustomerSupport(user,userId);
				System.out.println("after executing  dao"+userId1);
				if (userId!= null && userId > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("customerSupport Registered Successfully");
					// send sms & email to (username,password for login)
					String msg = "Dear user use this  UserName : " + user.getUserName()
							+ "\n and Password : " + password + " for login." + ". Thank you for registration.";
					userSmsService.sendSms(user.getMobile(), msg);
					String subject = "Welcome to HappyBus";
					String body = "Dear user use this  UserName : " + user.getUserName()
							+ "\n and Password : " + password + " for login." + ". Thank you for registration.";
					userEmailService.sendEmail(user.getEmail(), subject, body);
				}
			}
		} 
		}catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			e.printStackTrace();
			response.setMessage(e.getMessage());
			logger.error("Exception Occured while Registering the CustomerSupport :: " + e.getMessage());
		} catch (DataAccessException e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while Registering the CustomerSupport :: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while Registering the Travel :: " + e.getMessage());
		}
		String jsonRespone = JsonUtil.convertJavaToJson(response);
		logger.info("Response from registerTravel() : " + jsonRespone);
		return jsonRespone;

	}

	
	
	
	/**
	 * the method contain logic store customerQuerydetails
	 * 
	 * @param jsonCustomerQueries
	 * @return jsonResponse
	 * @author naveen
	 */
	@Override
	public String saveQueries(String jsonQuery, Object userId) {

		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Sorry your query is not submitted!Please Try Again.");
		logger.info("Entered into saveQueries Method  :: " + jsonQuery);

		CustomerSupport customerSupport = JsonUtil.convertJsonToJava(jsonQuery, CustomerSupport.class);

		if (customerSupport != null) {

			try {
				Long count = (Long) customerQueriesDAO.insertQueries(customerSupport, userId);

				if (count > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Your query is submited & queryid is  "+count);
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Problem occured While Accessing Please Try Again");
				}
			} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while submiting the query ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while submiting the query ::" + e.getMessage());
			}
		}
		// convert response object into json
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of submit query:: " + jsonResponse);
		return jsonResponse;
	}


	/**
	 * the method contain logic store getQuerydetails
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author naveen
	 */






	@Override
	public String getQueries(Long userId,String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("querys can not be retrieved!Please Try Again.");
		logger.info("Entered into getQueries Method  :: " + userId);


		try {
			

			if(userAuthenticationService.isAuthenticated(userId,token)){
				logger.info("Entered into authenticcation " +userId);
		if (userId != null) {
			
			
				Object[] obj =  customerQueriesDAO.getQueries( userId);
			
			
				if (obj!=null) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("the following queries are unresolved");
					
					// convert into user obj into json
				 jsonResponse  = JsonUtil.convertJavaToJson(obj);
				 response.setData(jsonResponse);
				 System.out.println(jsonResponse);
					
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Problem occured While Accessing Please Try Again");
				}
		}
			}} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while retrieving the querys ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while retrieving the query ::" + e.getMessage());
			}
		
		// convert response object into json
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of get query:: " + jsonResponse);
		return jsonResponse;
	}




	/**
	 * the method contain logic showQueries
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author naveen
	 */




	@Override
	public String showQueries(Long userId,String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("querys can not be retrieved!Please Try Again.");
		logger.info("Entered into showQueries Method  :: " + userId);

//		CustomerSupport customerSupport = JsonUtil.convertJsonToJava(jsonSupportQuery, CustomerSupport.class);
		try {
		
			if(userAuthenticationService.isAuthenticated(userId,token)){
				logger.info("Entered into authenticcation " +userId);
			
		if (userId != null) {
			
		
				List< CustomerSupport> listCustomerQueryData =  customerQueriesDAO.showQueries( userId);

				if (listCustomerQueryData.size()>0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("the following are your queries");
					
					// convert into user obj into json
				 jsonResponse  = JsonUtil.convertJavaToJson(listCustomerQueryData);
				 System.out.println(jsonResponse);
				 response.setData(jsonResponse);
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Problem occured While Accessing Please Try Again");
				}
		}
		}
			} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while retrieving the querys ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while retrieving the query ::" + e.getMessage());
			}
		
		// convert response object into json
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of get query:: " + jsonResponse);
		return jsonResponse;
		
		
	}







	/**
	 * the method contain logic showQueries
	 * 
	 * @param jsonCustomerSupport,userId
	 * @return jsonResponse
	 * @author naveen
	 */

	/*,String solutionToQuery,Object queryId*/

	@Override
	public String updateSolutionToQueries(String jsonCustomerSupport,Object customerId,Long userId,String token) {
		
		System.out.println("entered into update solutionTo Queries");
		
	
		
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("querys solution can not be updated!Please Try Again.");
		logger.info("Entered into updateSolutionToQueries  :: " + customerId);
 
CustomerSupport customerSupport = JsonUtil.convertJsonToJava(jsonCustomerSupport, CustomerSupport.class);

		if (customerSupport != null&& customerId!= null ) {
			
			try {
				
				if(userAuthenticationService.isAuthenticated(userId,token)){
					
					//logger.info();
					String subject = "Welcome to HappyBus";
				String emailID	=customerQueriesDAO.getEmail(customerId);
					userEmailService.sendEmail(emailID, subject, customerSupport.getQuerySolution());
					logger.info(customerSupport.getQuerySolution());
					logger.info(emailID);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Generated Link Sent to Your Mail please reset Password using your Mail");
					
				Integer count=  customerQueriesDAO.updateSolutionToQueries(customerSupport);
			
				if (count!=null&&count>0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("solution to the query is updated");
					System.out.println("after Executing dao in if condition");	
					
					
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Problem occured While Accessing Please Try Again");
				}
				}
			} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while updating solution to querys ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while retrieving the query ::" + e.getMessage());
			}
		}
		// convert response object into json
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of get query:: " + jsonResponse);
		return jsonResponse;	
	}




	



	/*,solutionToQuery,queryId*/
	@Override
	public String getIssueType(Long userId, String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("IssueType not retrive! Please try again");
		logger.info("Entered into getIssueType :");

		try {
			if (userAuthenticationService.isAuthenticated(userId, token)) {
				
				List<IssueType> issueTypeList = customerQueriesDAO.getIssueType();
				
				if (issueTypeList.size() > 0) {
					

					response.setData(JsonUtil.convertJavaListToJson(issueTypeList));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("All issueType retrive successfully");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("No issueType found in database");
				}
			}
		} catch (UserAuthenticationException uae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setData("Unable to process");
			logger.error("Exception occure while authentication of user" + uae.getMessage());
			uae.printStackTrace();
		} catch (DataAccessException dae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again.");
			logger.error("Exception occured while geting issueType" + dae.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again.");
			logger.error("Exception occured while get geting issueType" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response from issueType :: " + jsonResponse);
		return jsonResponse;
	}








}
