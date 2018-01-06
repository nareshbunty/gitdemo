package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.happybus.beans.LoginLog;
import com.happybus.beans.Response;
import com.happybus.beans.User;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;
import com.happybus.web.client.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	private static final String WEB_PASSENGER_REGISTRATION="happy_home";
	private static final String WEB_PASSENGER_USER_FORGOT_PASSWORD="happy_forgot_password";
	@RequestMapping(value="registerUser",method=RequestMethod.GET)
public String showPassengerRegistrationPage(){
	return WEB_PASSENGER_REGISTRATION;
}	
@RequestMapping(value="userRegistration",method=RequestMethod.POST)
@ResponseBody
	public String passengerRegistration(@ModelAttribute User user){
	String jsonResponse=userService.registerUser(user);
return jsonResponse;	
}

@RequestMapping(value="checkOTP",method=RequestMethod.POST)
@ResponseBody
	public String checkOTP(@RequestParam("userId") Long userId,
			@RequestParam("otp") Integer otp){
	String jsonResponse="";
	 jsonResponse=userService.checkOTP(userId,otp);
return jsonResponse;	
}

@RequestMapping(value="otpForm",method=RequestMethod.GET)

	public ModelAndView checkOTP(@RequestParam("userId") Long userId,
			@RequestParam("mobile") String mobile){
  System.out.println(userId+" "+mobile);
	ModelAndView modelAndView=new ModelAndView("otpForm");
  modelAndView.addObject("userId",userId);
  modelAndView.addObject("mobile",mobile);
  return modelAndView;
}

@RequestMapping(value="checkUser",method=RequestMethod.GET)
@ResponseBody
public String checkUser(@RequestParam("email") String email,
		@RequestParam("mobile") String mobile){

 return userService.checkUser(email, mobile);
}
/**
 * the method contain logic to Change Password
 * 
 * @param 
 * @return happy_forgot_password
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="changePassword",method=RequestMethod.POST)
@ResponseBody
public String changePassword( HttpServletRequest request,
		@RequestParam("currentpassword") String currentPassword,
		@RequestParam("newpassword") String newPassword,
		@RequestParam("confirmpassword") String confirmPassword){
	String userName=(String)request.getSession(false).getAttribute("userName");
	String jsonResponse=""; 
	jsonResponse=userService.changePassword(userName,currentPassword,newPassword,confirmPassword); 
	return jsonResponse;

}
/**
 * the method used to show getProfileForm
 * 
 * @param 
 * @return happy_forgot_password
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="getProfile",method=RequestMethod.GET)
public ModelAndView getProfile(@RequestParam("userId") Long userId,HttpServletRequest request){
System.out.println(userId);
ModelAndView modelAndView=new ModelAndView("happy_editprofileForm");
Response response =userService.getProfile(userId);
if(response!=null && response.getStatus().equals(StatusUtil.HAPPY_STATUS_SUCCESS)){
	User user=JsonUtil.convertJsonToJava(response.getData(),User.class);
		request.setAttribute("userId",user.getUserId());
		request.setAttribute("email",user.getEmail());
		request.setAttribute("mobile",user.getMobile());
		request.setAttribute("dob",user.getDob());
		request.setAttribute("gender",user.getGender());
		request.setAttribute("address",user.getAddress());
		request.setAttribute("pincode",user.getPincode());		
	 }
return modelAndView;
}
/**
 * the method contain Logic for update profile
 * 
 * @param 
 * @return happy_forgot_password
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="updateProfile",method=RequestMethod.POST)
@ResponseBody
	public String updateProfile(@ModelAttribute User user){
	String jsonResponse=userService.updateProfile(user);
return jsonResponse;	
}
/**
 * the method used to show forgotpasswordForm
 * 
 * @param 
 * @return happy_forgot_password
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="forgotForm",method=RequestMethod.GET)
public String showForgotPasswordForm(){
return WEB_PASSENGER_USER_FORGOT_PASSWORD;
}
/**
 * the method contain logic for call service to send email or otp to mobile
 * 
 * @param 
 * @return jsonResponse
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="forgotPassword",method=RequestMethod.POST)
@ResponseBody
	public String forgotPassword(@RequestParam("email") String forgotinput ){
	String jsonResponse=""; 
	jsonResponse=userService.forgotPassword(forgotinput ); 
	 return jsonResponse;	 
}
/**
 * the method used to show resetpasswordForm
 * 
 * @param 
 * @return resetPasswordForm
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="resetForm",method=RequestMethod.GET)
public ModelAndView resetPasswordPage(@RequestParam("userId") Long userId){
System.out.println(userId);
ModelAndView modelAndView=new ModelAndView("resetPasswordForm");
modelAndView.addObject("userId",userId);
return modelAndView;
}
@RequestMapping(value="regenerateOTP",method=RequestMethod.GET)
@ResponseBody
	public String regenerateOTP(@RequestParam("userId") Long userId,
			@RequestParam("mobile") String mobile){
	String jsonResponse="";
	 jsonResponse=userService.regenerateOTP(userId, mobile);
return jsonResponse;
}
/**
 * the method used to show resetpasswordForm
 * 
 * @param 
 * @return resetPasswordForm
 * @author nagaraj.e and srikanth.g
 */

@RequestMapping(value="forgototpform",method=RequestMethod.GET)

public ModelAndView PasswordPage(@RequestParam("userId") Long userId,@RequestParam("mobile") String mobile){
	System.out.println(userId+" "+mobile);
	ModelAndView modelAndView=new ModelAndView("happy_forgot_otp_form");
  modelAndView.addObject("userId",userId);
  modelAndView.addObject("mobile",mobile);
modelAndView.addObject("userId",userId);
return modelAndView;
}
/**
 * the method contain logic for resetPassword
 * 
 * @param 
 * @return jsonResponse
 * @author nagaraj.e and srikanth.g
 */
@RequestMapping(value="resetPassword",method=RequestMethod.POST)
@ResponseBody
	public String resetPassword(@RequestParam("userId") Long userId,
			@RequestParam("newpassword") String newpassword,@RequestParam("confirmpassword") String confirmpassword){
	String jsonResponse="";
	 jsonResponse=userService.resetPassword(userId,newpassword,confirmpassword); 
return jsonResponse;	
}

@RequestMapping(value="loginUser",method=RequestMethod.POST)
public ModelAndView  loginUser(@ModelAttribute("user")  User user,
		HttpServletRequest request){
	String status="Login Failure!Please Try Again";
	String targetViewName="happy_home";
	ModelAndView modelAndView=new ModelAndView();
	LoginLog loginLog=new LoginLog();
	loginLog.setUserAgent(request.getHeader("user-agent"));
	loginLog.setUserIp(request.getRemoteAddr());
	loginLog.setSessionId(request.getRequestedSessionId());
	 user.setLoginLog(loginLog);
	   Response response =userService.loginUser(user);
   if(response!=null && response.getStatus().equals(StatusUtil.HAPPY_STATUS_SUCCESS) || response.getStatus().equals(StatusUtil.HAPPY_STATUS_MOBILE_NOT_VERIFIED)){
		user=JsonUtil.convertJsonToJava(response.getData(),User.class);
		if(user!=null && user.getUserId()!=null
		&& user.getUserId()>0 &&
		user.getUserRole()!=null){
	//keep the data into session
		HttpSession session=request.getSession(false);
		 if(session==null){
			 session=request.getSession();
		 }
		session.setAttribute("userId",user.getUserId());
		session.setAttribute("userName",user.getUserName());
		session.setAttribute("userRole",user.getUserRole());
		session.setAttribute("token",user.getToken());
		session.setAttribute("status","welcome ,"+user.getUserName());
	if(user.getUserRole().equals(RolesConstants.ROLE_PASSENGER) && response.getStatus().equals(StatusUtil.HAPPY_STATUS_MOBILE_NOT_VERIFIED)){	
		targetViewName="otpForm";
			
		modelAndView.setViewName(targetViewName);
	}
	else if(user.getUserRole().equals(RolesConstants.ROLE_PASSENGER) && response.getStatus().equals(StatusUtil.HAPPY_STATUS_SUCCESS)){
		
		modelAndView.setView(new RedirectView("passengerDashboard"));
	}else if(user.getUserRole().equals(RolesConstants.ROLE_TRAVEL_ADMIN) && response.getStatus().equals(StatusUtil.HAPPY_STATUS_SUCCESS)){
		modelAndView.setView(new RedirectView("traveladminDashboard"));
	}
	else {
		modelAndView.setView(new RedirectView("adminDashboard"));
	
	}
		}else{
			status="Login Failure!Please Try Again";
			targetViewName="happy_home";
			modelAndView.setViewName(targetViewName);
			modelAndView.addObject("status",status);
		}
	}else{
		status=response.getMessage();
		targetViewName="happy_home";
		modelAndView.setViewName(targetViewName);
		
	}
   
   return modelAndView;
   }

@RequestMapping(value="logoutUser",method=RequestMethod.GET)
public ModelAndView  logoutUser(HttpServletRequest req){
   String status="Your Are Loggedout Successfully";
	HttpSession session=req.getSession(false);
   if(session!=null && session.getAttribute("userId")!=null){
	   
	Response response=userService.logoutUser(session.getAttribute("userId"),session.getAttribute("token")); 
	   session.invalidate();
	   status="Your Are Loggedout Successfully";
   }
   return new ModelAndView("happy_home","logoutStatus",status);
}
@RequestMapping(value = "getUserId", method = RequestMethod.GET)
@ResponseBody
public String getUserId(@RequestParam("email") String email) {

	return userService.getUserIdByUserName(email);
}
}


