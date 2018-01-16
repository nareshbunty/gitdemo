
/*
 * Copyright (c) 2016, 2017, HappyBus and/or its affiliates. All rights reserved.
 * HppayBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.happybus.processing.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happybus.beans.FeedBack;
import com.happybus.beans.FeedbackType;
import com.happybus.beans.Response;
import com.happybus.beans.Review;
import com.happybus.integration.dao.FeedbackDAO;
import com.happybus.integration.dao.FeedbackTypeDAO;
import com.happybus.integration.dao.UserDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;

/**
 * This class contain feedback to user.
 * 
 * @author Harisha
 * @version 1.0
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{
	
	private static Logger logger=Logger.getLogger(FeedbackServiceImpl.class);
	@Autowired
	FeedbackTypeDAO feedbackTypeDAO;
	@Autowired
	FeedbackDAO feedbackDAO;
	@Autowired
	UserAuthenticationService userAuthenticationService;
	@Autowired
	UserDAO userDAO;
	
	/**
	 * this method contain save FeedbackType
	 * 
	 * @param jsonFeedbackType
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String saveFeedbackType(String jsonFeedbackType, Long userId,String role ,String token) {
		logger.info("::registerFeedbackType method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("FeedbackType registre is Failure!Please Try Again.");
		try {
			if (userAuthenticationService.isAuthenticated((Long) userId, (String) token)) {
				logger.info(":::is Authenticated user");
				FeedbackType feedbackType = JsonUtil.convertJsonToJava(jsonFeedbackType, FeedbackType.class);
				if (role != null && role.equals(RolesConstants.ROLE_SUPER_ADMIN)) {
					logger.info("::: is user role is super admin");
					if (feedbackTypeDAO.saveFeedbackType(feedbackType) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("FeedbackType save Successfully");
					}
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("You don't have permission!");
				}
			}
		} catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage(e.getMessage());
			logger.error("Exception Occured while save FeedbackType :: " + e.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while save FeedbackType:: " + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}

	/**
	 * this method contain get all FeedbackTypes
	 * 
	 * @param jsonString
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String getAllFeedbackTypes(Long userId,String role,String token) {
		logger.info("::getAllFeedbackTypes method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("get all feedback types Failure!Please Try Again.");
		try {
		//	 if (userAuthenticationService.isAuthenticated((Long) userId,(String) token)) {
			logger.info(":::Is isAuthenticated user::: " + userId);
			if (role != null && role.equals(RolesConstants.ROLE_SUPER_ADMIN)) {
				logger.info(":::user rolle is Super admin:::");
				// get all feed back types from DB
				List<FeedbackType> listFeedbackTypes = feedbackTypeDAO.getAllFeedbackTypes();
				if (!listFeedbackTypes.isEmpty()) {
					// list feed back types convert to json array
					String	jsonList = JsonUtil.convertJavaToJson(listFeedbackTypes);
					response.setData(jsonList);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Get all feedback types Successfully");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Data is not available");
				}
			} else {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("You don't have permission!");
			}
	//		  }
		} catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage(e.getMessage());
			logger.error("Exception Occured while getAllFeedbackTypes :: " + e.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured whilegetAllFeedbackTypes:: " + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}

	/**
	 * this method contain delete FeedbackType in db
	 * 
	 * @param jsonString
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String deleteFeedbackType(Integer feedbackTypeId ,Long userId, String role,String token) {
		logger.info("::deleteFeedbackType method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("FeedbackType delete Fail!Please Try Again.");
		try {
			if (userAuthenticationService.isAuthenticated((Long) userId, (String) token)) {
				logger.info(":::is authenticated user:::");
				if (role != null && role.equals(RolesConstants.ROLE_SUPER_ADMIN)) {
					logger.info("::user role is super admin:::");
					if (feedbackTypeDAO.deleteFeedbackType(Integer.valueOf(feedbackTypeId.toString())) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("FeedbackType delete Successfully");
					} else {
						response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
						response.setMessage("FeedbackType is not available! delete Fail ");
					}
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("You don't have permission!");
				}
			}
		} catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while deleteFeedbackType :: " + e.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while deleteFeedbackType:: " + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}
	/**
	 * this method contain update FeedbackType in db
	 * 
	 * @param jsonfeedbackType
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String updateFeedbackType(String jsonFeedbackType, Long userId,String role, String token) {
		logger.info("::updateFeedbackType method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("FeedbackType update Fail!Please Try Again.");
		try {
			if (userAuthenticationService.isAuthenticated((Long) userId, (String) token)) {
				logger.info(":::is authenticated user:::");
				FeedbackType feedbackType=JsonUtil.convertJsonToJava(jsonFeedbackType,FeedbackType.class);
				if (role != null && role.equals(RolesConstants.ROLE_SUPER_ADMIN)) {
					logger.info("::user role is super admin:::");
					if (feedbackTypeDAO.updateFeedbackType(feedbackType) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("FeedbackType update Successfully");
					} 
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("You don't have permission!");
				}
			}
		} catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while updateFeedbackTypes :: " + e.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while updateFeedbackTypes:: " + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}
	/**
	 * this method contain get FeedbackType desc in db
	 *
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String getAllFeedbackTypeDesc() {
		logger.info("::getAllFeedbackTypeDesc method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("get all feedback types desc Failure!Please Try Again.");
		try {
				// get all feed back type desc from DB
				List<Map<String, Object>> listFeedbackTypeDesc = feedbackTypeDAO.getAllFeedbackTypesDesc();
				if (!listFeedbackTypeDesc.isEmpty()) {
					// list feed back types convert to json array
					String	jsonList = JsonUtil.convertJavaToJson(listFeedbackTypeDesc);
					response.setData(jsonList);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Get all feedback type descs Successfully");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Data is not available");
				}
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again."+e);
			logger.error("Exception Occured whilegetAllFeedbackTypes:: " + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}
	/**
	 * this method contain save feed back
	 * 
	 * @param jsonFeedback
	 * @return Response obj
	 * 
	 */
	@Override
	public String saveFeedback(String jsonFeedback) {
		logger.info("::registerFeedback method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("unable to  process!Please Try Again.");
		try {
				FeedBack feedback = JsonUtil.convertJsonToJava(jsonFeedback, FeedBack.class);
			
			 	feedback.setStatus((byte)0);
					if (feedbackDAO.saveFeedback(feedback) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Thank You for Your Feedback");
		             }
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured while save FeedbackType:: " + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}
	@Override
	public String getAllReviews() {
		logger.info("::getAllReviews method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("getAllReviews Failure");
		try {
				// get all feed back type desc from DB
				List<Review> reviews = feedbackDAO.getAllReviews();
				if (!reviews.isEmpty()) {
					// list feed back types convert to json array
					String	jsonList = JsonUtil.convertJavaToJson(reviews);
					response.setData(jsonList);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Get all revies Successfully");
					logger.info("::json list:::"+jsonList);
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Data is not available");
				}
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured getAllReviews:: " + e.getMessage());
		}
		logger.info("::getAllReviews method finished:::");
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}
	@Override
	public String getAllReviewsByTravelId(Integer travelId) {
		logger.info("::getAllReviewsByTravelId method started:::");
		Response response = new Response();
		response.setMessage(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("getAllReviewsByTravelId Failure");
		try {
				// get all feed back type desc from DB
				List<Review> reviews = feedbackDAO.getAllReviewsByTravelId(travelId);
				if (!reviews.isEmpty()) {
					// list feed back types convert to json array
					String	jsonList = JsonUtil.convertJavaToJson(reviews);
					response.setData(jsonList);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Get all revies Successfully");
					logger.info("::json list:::"+jsonList);
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Data is not available");
				}
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process your request!Please Try Again.");
			logger.error("Exception Occured getAllReviews:: " + e.getMessage());
		}
		logger.info("::getAllReviewsByTravelId method finished:::");
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}


}