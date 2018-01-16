
/*
 * Copyright (c) 2016, 2017, HappyBus and/or its affiliates. All rights reserved.
 * HppayBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.happybus.processing.service;

/**
 * This class contain feedback to user.
 * 
 * @author Harisha
 * @version 1.0
 */

public interface FeedbackService {
	
	/**
	 * this method contain save FeedbackType
	 * 
	 * @param jsonFeedbackType
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String saveFeedbackType(String jsonFeedbackType,Long userId,String role ,String token);
	/**
	 * this method contain get all FeedbackTypes 
	 * 
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String getAllFeedbackTypes(Long userId,String role, String token);
	/**
	 * this method contain delete FeedbackType in db
	 * 
	 * @param jsonString
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String deleteFeedbackType(Integer feedbackTypeId, Long userId, String role,String token);
	/**
	 * this method contain update FeedbackType in db
	 * 
	 * @param jsonfeedbackType
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String updateFeedbackType(String jsonFeedbackType, Long userId, String role,String token) ;
	/**
	 * this method contain get all FeedbackTypeDesc
	 * 
	 * @param userId
	 * @param token
	 * @return Response obj
	 * 
	 */
	public String getAllFeedbackTypeDesc();
	public String saveFeedback(String jsonFeedback);
	public String getAllReviews();
	public String getAllReviewsByTravelId(Integer travelId);

}
