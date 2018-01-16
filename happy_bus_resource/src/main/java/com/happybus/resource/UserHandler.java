package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.beans.User;
import com.happybus.processing.service.UserService;

/**
 * This class is acting as Resource class . It accessed over the N.w
 * 
 * @author Sathish
 */
@RestController
@RequestMapping(value = "user")
public class UserHandler {
	@Autowired
	private UserService userService;

	/**
	 * the method is a resource method for loginUser
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 */
	@RequestMapping(value = "loginUser", method = RequestMethod.POST)
	@ResponseBody
	public String loginUser(@RequestBody String jsonUser) {
		return userService.loginUser(jsonUser);
	}

	/**
	 * the method is a resource method for registerUser
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 */
	@RequestMapping(value = "registerUser", method = RequestMethod.POST)
	@ResponseBody
	public String registerUser(@RequestBody String jsonUser) {
		return userService.registerUser(jsonUser);
	}

	/**
	 * This method contain logic for Getting Old data To User To edit profile *
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	@RequestMapping(value = "getProfile", method = RequestMethod.GET)
	@ResponseBody
	public String editProfile(@RequestParam("userId") Long userId) {
		return userService.getProfile(userId);

	}

	/**
	 * This method contain logic for update profile*
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	@RequestMapping(value = "updateProfile", method = RequestMethod.POST)
	@ResponseBody
	public String updateProfile(@RequestBody String jsonUser) {
		return userService.updateProfile(jsonUser);

	}

	/**
	 * the method is a resource method for ChangePassword
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	@RequestMapping(value = "changePassword", method = RequestMethod.POST)
	@ResponseBody
	public String changePassword(@RequestBody String jsonUser) {

		return userService.changePassword(jsonUser);

	}

	/**
	 * the method is a resource method for ForgotPassword
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	@RequestMapping(value = "forgotPassword", method = RequestMethod.POST)
	@ResponseBody
	public String forgotPassword(@RequestBody String jsonUser) {
		return userService.forgotPassword(jsonUser);

	}

	/**
	 * the method is a resource method for ForgotPassword
	 * 
	 * @param userId,otp
	 * @return jsonResponse
	 * @author nagaraj.e and srikanth.g
	 */
	@RequestMapping(value = "checkOTP", method = RequestMethod.GET)
	@ResponseBody
	public String OtpCheck(@RequestParam("userId") Long userId, @RequestParam("otp") Integer otp) {

		return userService.otpCheck(userId, otp);

	}
	
	
	@RequestMapping(value = "regenerateOTP", method = RequestMethod.GET)
	@ResponseBody
	public String otpGenerate(@RequestParam("userId") Long userId, @RequestParam("mobile") String mobile) {

		return userService.reGenerateOtp(userId, mobile);

	}

	@RequestMapping(value = "resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String resetPassword(@RequestBody String jsonUser) {
		return userService.resetPassword(jsonUser);

	}

	@RequestMapping(value = "checkUser/{userName}/{mobile}")
	@ResponseBody
	public String checkUserName(@PathVariable("userName") String userName, @PathVariable("mobile") String mobile) {
		return userService.checkUserName(userName, mobile);
	}
	@RequestMapping(value = "logoutUser/{userId}/{token}",method=RequestMethod.GET)
	@ResponseBody
	public String logoutUser(@PathVariable("userId") String userId,
			@PathVariable("token") String token){
      return userService.logoutUser(userId,token);		
	}
	/**
	 * The method is a resource method for  get user id
	 * @param userEmail
	 * @return
	 */
	@RequestMapping(value = "getUserIdByUserName",method=RequestMethod.GET)
	@ResponseBody
	public String getUserIdByUserName(@RequestParam("userEmail") String userEmail)
	{
      return userService.getUserIdByUserName(userEmail);		
	}
	
}
