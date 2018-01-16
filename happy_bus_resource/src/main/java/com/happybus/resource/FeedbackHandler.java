/*
 * Copyright (c) 2016, 2017, HappyBus and/or its affiliates. All rights reserved.
 * HppayBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.FeedbackService;


/**
 * This Handle The Feedback service
 * 
 * @author Srinu R
 * @version 1.0
 *
 */
@RestController
@RequestMapping("feedback")
public class FeedbackHandler {
	
	@Autowired
	private FeedbackService feedbackService;
	/**
	 * the method is a resource method for registerFeedbackType
	 * 
	 * @param jsonFeedbackTypep
	 * @param userId
	 * @param token
	 * @return jsonResponse
	 */
	@RequestMapping(value = "registerFeedbackType", method = RequestMethod.POST)
	@ResponseBody
	public String saveFeedbackType(@RequestBody() String jsonFeedbackType, @RequestParam("userId")Long userId,
			@RequestParam("role") String role,
			@RequestParam("token") String token) {
		return feedbackService.saveFeedbackType(jsonFeedbackType, userId, role,token);
	}
	/**
	 * the method is a resource method for getAllFeedbackTypes
	 * 
	 * @param jsonFeedbackTypep
	 * @param userId
	 * @param token
	 * @return jsonResponse
	 */
	@RequestMapping(value = "getAllFeedbackTypes", method = RequestMethod.GET)
	@ResponseBody
	public String getAllFeedbackTypes(@RequestParam("userId") Long userId,@RequestParam("role") String role,
			@RequestParam("token") String token) {
		
		return feedbackService.getAllFeedbackTypes(userId,role, token);
	}
	/**
	 * the method is a resource method for  delete FeedbackTypes
	 * 
	 * @param jsonFeedbackType
	 * @param userId
	 * @param token
	 * @return jsonResponse
	 */
	@RequestMapping(value = "deleteFeedbackType", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFeedbackType(@RequestParam("feedbackTypeId")Integer feedbackTypeId,@RequestParam("userId") Long userId,
			@RequestParam("role") String role,@RequestParam("token") String token) {
		return feedbackService.deleteFeedbackType(feedbackTypeId,userId,role, token);
	}
	/**
	 * the method is a resource method for  update FeedbackTypes
	 * 
	 * @param jsonFeedbackType
	 * @param userId
	 * @param token
	 * @return jsonResponse
	 */
	@RequestMapping(value = "updateFeedbackType", method = RequestMethod.POST)
	@ResponseBody
	public String updateFeedbackType(@RequestBody String jsonFeedbackType,@RequestParam("userId") Long userId,
			@RequestParam("role") String role,@RequestParam("token") String token) {
		return feedbackService.updateFeedbackType(jsonFeedbackType, userId, role,token);
	}
	/**
	 * the method is a resource method for getAllFeedbackTypeDesc
	 * 
	 * @param userId
	 * @param token
	 * @return jsonResponse
	 */
	@RequestMapping(value = "getAllFeedbackTypeDesc", method = RequestMethod.GET)
	@ResponseBody
	public String getAllFeedbackTypeDesc() {
		return feedbackService.getAllFeedbackTypeDesc();
	}
	/**
	 * the method is a resource method for save Feedback
	 * 
	 * @param jsonFeedback
	 * @return jsonResponse
	 */
	@RequestMapping(value = "saveFeedback", method = RequestMethod.POST)
	@ResponseBody
	public String saveFeedback(@RequestBody() String jsonFeedback) {
		return feedbackService.saveFeedback(jsonFeedback);
	}
	/**
	 * the method is a resource method for getAllReviews
	 * 
	 * @return jsonResponse
	 */
	@RequestMapping(value = "getReviews", method = RequestMethod.GET)
	@ResponseBody
	public String getAllReviews() {
		return feedbackService.getAllReviews();
	}
	@RequestMapping(value = "getReviewsByTravels", method = RequestMethod.GET)
	@ResponseBody
	public String getAllReviewsByTravelId(@RequestParam("travelId") Integer travelId) {
		return feedbackService.getAllReviewsByTravelId(travelId);
	}
}
