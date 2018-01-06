package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.beans.FeedBack;
import com.happybus.beans.FeedbackType;
import com.happybus.web.client.service.FeedbackService;

@Controller
public class FeedbackController {
	
	@Autowired
	private FeedbackService feedbackService;
	/**
	 * show feedback form
	 */
	@RequestMapping(value = "addFeedbackType", method = RequestMethod.GET)
	public String showAddFeedbackPage() {
		return "feedbacktype-registration";
	}

	@RequestMapping(value = "addFeedbackType", method = RequestMethod.POST)
	@ResponseBody
	public String addFeedbackType(HttpServletRequest req,@ModelAttribute FeedbackType feedbackType) {
		String jsonResponse = "{}";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			
			jsonResponse = feedbackService.saveFeedbackType(feedbackType,req.getSession(false).getAttribute("userId") ,req.getSession(false).getAttribute("userRole") ,
					req.getSession(false).getAttribute("token"));
		} else {

		}
     
		return jsonResponse;
	}
	/**
	 * all feedbackTypes show
	 * 
	 */
	@RequestMapping(value = "allFeedbackTypes", method = RequestMethod.GET)
	public String showAllFeedbackTypes() {
		return "show-all-feedbacktypes";
	}
	@RequestMapping(value = "getAllFeedbackTypes", method = RequestMethod.GET)
	@ResponseBody
	public String getAllFeedbackTypes(HttpServletRequest req) {
		String jsonResponse = "{}";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			
			jsonResponse = feedbackService.getAllFeedbackTypes(req.getSession(false).getAttribute("userId")  ,
					req.getSession(false).getAttribute("userRole"),req.getSession(false).getAttribute("token"));
		} else {

		}
        
		return jsonResponse;
	}
	@RequestMapping(value = "deleteFeedbackType", method = RequestMethod.GET)
	@ResponseBody
	public String deleteFeedbackType(HttpServletRequest req,@RequestParam("feedbackTypeId")Integer feedbackTypeId) {
		String jsonResponse = "{}";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			
			jsonResponse = feedbackService.deleteFeedbackType(feedbackTypeId,req.getSession(false).getAttribute("userId")  ,
					req.getSession(false).getAttribute("userRole"),req.getSession(false).getAttribute("token"));
		} else {

		}
      
		return jsonResponse;
	}
	@RequestMapping(value = "updateFeedbackType", method = RequestMethod.POST)
	@ResponseBody
	public String updateFeedbackType(HttpServletRequest req,@ModelAttribute FeedbackType feedbackType) {
		String jsonResponse = "{}";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			
			jsonResponse = feedbackService.updateFeedbackType(feedbackType,req.getSession(false).getAttribute("userId") ,req.getSession(false).getAttribute("userRole") ,
					req.getSession(false).getAttribute("token"));
		} else {

		}
      
		return jsonResponse;
	}

	/**
     * Feedback form	
     * @return
     */
	@RequestMapping(value = "feedback", method = RequestMethod.GET)
	public String showTravelRegistrationPage() {
		return "feedback-form";
	}
	@ResponseBody
	@RequestMapping(value ="getAllFeedbackTypeDesc", method = RequestMethod.GET)
	public String getAllFeedbackTypeDesc() {
		System.out.println("getAllFeedbackTypeDesc");
		String jsonResponse = "{}";
			jsonResponse = feedbackService.getAllFeedbackTypeDesc();
		return jsonResponse;
	}
	
	@RequestMapping(value = "saveFeedback", method = RequestMethod.POST)
	@ResponseBody
	public String addFeedbackType(@ModelAttribute FeedBack feedback) {
		String jsonResponse = "{}";
			jsonResponse = feedbackService.saveFeedback(feedback);
			
		return jsonResponse;
	}
	@RequestMapping(value = "reviews", method = RequestMethod.GET)
	public String showReviewPage() {
		return "reviews";
	}
	@ResponseBody
	@RequestMapping(value ="getReviews", method = RequestMethod.GET)
	public String getReviews() {
		String jsonResponse = "{}";
			jsonResponse = feedbackService.getAllReviews();
		return jsonResponse;
	}
	@ResponseBody
	@RequestMapping(value ="getReviewsByTravel", method = RequestMethod.GET)
	public String getReviewsByTravel(@RequestParam("travelId")Integer travelId) {
		String jsonResponse = "{}";
			jsonResponse = feedbackService.getAllReviewsByTravelId(travelId);
		return jsonResponse;
	}
}
