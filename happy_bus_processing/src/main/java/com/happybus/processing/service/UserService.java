/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;

/**
 * UserService is used to implement Bussiness Logic for Users
 * 
 * @author Nagaraj.e
 * @since 1.0
 */
public interface UserService {
	/**
	 * the method contain logic for User Login
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author sathish.bandi
	 */
	public String loginUser(String jsonUser);

	/**
	 * the method contain logic for Passenger Registration
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author sathish.bandi
	 */
	public String registerUser(String jsonUser);

	/**
	 * This method contain logic for Getting Old data To User To edit profile *
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	public String getProfile(Long userId);

	/**
	 * This method contain logic for update profile*
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	public String updateProfile(String jsonUser);

	/**
	 * the method contain logic for Change Password
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	public String changePassword(String jsonUser);

	/**
	 * the method contain logic for forgotPassword
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */

	public String forgotPassword(String jsonUser);

	/**
	 * the method contain logic for ForgotPassword Otp Verification
	 * 
	 * @param userId,otp
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	public String otpCheck(Long userId, Integer otp);

	/**
	 * the method contain logic for resetPassword
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	public String resetPassword(String jsonUser);
	/**
	 * The method contain logic to save FeedbackType
	 * 
	 * @param jsonFeedbackType
	 * @param userId
	 * @param token
	 * @return jsonResponse
	 * @author Harisha
	 */
	// public String saveFeedbackType(String jsonFeedbackType, Object
	// userId,Object token);

	public String checkUserName(String userName, String mobile);
	public String getUserRole(Long userId);

	public String logoutUser(String userId,String token);
	
	public String reGenerateOtp(Long userId,String mobile);

	String getUserIdByUserName(String userName);

}
