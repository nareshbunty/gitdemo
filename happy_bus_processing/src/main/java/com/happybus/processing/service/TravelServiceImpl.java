package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.happybus.beans.Response;
import com.happybus.beans.Travel;
import com.happybus.integration.dao.TravelsDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.security.PasswordGenerator;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;



@Service
public class TravelServiceImpl implements TravelService{
	@Autowired
	private TravelsDAO travelsDAO;
	@Autowired
	private UserSmsService userSmsService;
	@Autowired
	private UserEmailService userEmailService;
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	private static Logger logger=Logger.getLogger(TravelServiceImpl.class);
	@Override
	public String registerTravel(String jsonTravel, Long userId, String token) {
	Response response=new Response();
	response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
	response.setMessage("Travel Registration is Failure!Please Try Again.");
		logger.info("Entered into registerTravel() "+jsonTravel+" "+userId+" "+token);
   try{	
	if(userAuthenticationService.isAuthenticated(userId,token)){
		Travel travel=JsonUtil.convertJsonToJava(jsonTravel,Travel.class);
	  if(travel!=null && travel.getBankDetails()!=null && travel.getUser()!=null){
	    travel.getUser().setUserRole(RolesConstants.ROLE_TRAVEL_ADMIN);
	    travel.getUser().setEmail(travel.getTravelEmail());
	    
		  //generate the Password
		  String password=PasswordGenerator.generatePassword();
		  travel.getUser().setPassword(BCrypt.hashpw(password,BCrypt.gensalt()));
		  Integer travelId=(Integer)travelsDAO.registerTravel(travel);
	   if(travelId!=null && travelId>0){  
		   response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		   response.setMessage("Travel Registered Successfully");
		 //send sms & email to travel_admin (username,password for login)
			String msg = "Dear user use this  UserName : "+travel.getUser().getUserName()+"\n and Password : "+password+" for login."
					+ ". Thank you for registration.";  
		   userSmsService.sendSms(travel.getUser().getMobile(),msg);
   String subject="Welcome to HappyBus";
   String body="Dear user use this  UserName : "+travel.getUser().getUserName()+"\n and Password : "+password+" for login."
		+ ". Thank you for registration.";
		   userEmailService.sendEmail(travel.getTravelEmail(), subject, body);
	   }
	  }
	}
 }catch(UserAuthenticationException e){
	   response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
	   response.setMessage(e.getMessage());
	   logger.error("Exception Occured while Registering the Travel :: "+e.getMessage());
 e.printStackTrace();  
 }
   catch(DataAccessException e){
	   response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
	   response.setMessage("Unable to process your request!Please Try Again.");
	   logger.error("Exception Occured while Registering the Travel :: "+e.getMessage());
	   e.printStackTrace(); 
   }
   catch(Exception e){
	   response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
	   response.setMessage("Unable to process your request!Please Try Again.");
	   logger.error("Exception Occured while Registering the Travel :: "+e.getMessage());
	   e.printStackTrace(); 
   }
   String jsonRespone=JsonUtil.convertJavaToJson(response);
   logger.info("Response from registerTravel() : "+jsonRespone);
		return jsonRespone;
	}
	/**
	 *  This method contains travel register number alredy register or not operation performed
	 * @param travelRegisterNumber
	 * @return jsonResponse 
	 */
	
	@Override
	public String checkTravelRegisterNumber(String travelRegisterNumber) {
		Response response = new Response();
		response.setMessage("");
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		try {
			Integer count = travelsDAO.checkRegisterNumber(travelRegisterNumber);
			if (count != null && count > 0) {
				response.setMessage("Travle Number Already Registered !");
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
			}				
		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while  checkTravelRegisterNumber :" + de.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while  checkTravelRegisterNumber :" + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}
	@Override
	public String searchTravels(String jsonSearchTravels) {
		String jsonSearchedTravels="";
		List<Travel> list=null;
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Searching Booking History is Failure!Please Try Again.");
		logger.info("Entered into searchBooking :: " + jsonSearchTravels);

		
		Travel travel=JsonUtil.convertJsonToJava(jsonSearchTravels, Travel.class);
		list=travelsDAO.searchTravel(travel);
		
		jsonSearchedTravels=JsonUtil.convertJavaToJson(list);
		return jsonSearchedTravels;
	}

}
