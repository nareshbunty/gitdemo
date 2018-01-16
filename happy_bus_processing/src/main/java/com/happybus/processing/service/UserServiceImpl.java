
package com.happybus.processing.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.happybus.beans.ForgotPassword;
import com.happybus.beans.Response;
import com.happybus.beans.User;
import com.happybus.integration.dao.CouponsDAO;
import com.happybus.integration.dao.DiscountsDAO;
import com.happybus.integration.dao.OtpDAO;
import com.happybus.integration.dao.UserAuthenticationDAO;
import com.happybus.integration.dao.UserDAO;
import com.happybus.integration.sql.DBConstants;
import com.happybus.security.OTPGenerator;
import com.happybus.security.TokenGenerator;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;
import com.happybus.util.Validator;

@Service
public class UserServiceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserAuthenticationDAO userAuthenticationDAO;
	@Autowired
	private OtpDAO otpDAO;
	@Autowired
	private UserSmsService userSmsService;

	@Autowired
	private UserEmailService userEmailService;
	@Autowired
	private CouponsDAO couponDAO;
	@Autowired
	private DiscountsDAO discountsDAO;

	@Override
	public String loginUser(String jsonUser) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Login Failure!Please Try Again.");
		logger.info("Entered into loginUser :: " + jsonUser);
		User user = JsonUtil.convertJsonToJava(jsonUser, User.class);
		if (user != null) {
			try {// handle exceptions
					// get UserPassword based on username
					// call check checkpw()
				String hasPwd = userDAO.getHashPassword(user.getUserName());
				if (BCrypt.checkpw(user.getPassword(), hasPwd)) {
					user = userDAO.loginUser(user);
					if (user.getUserId() != null && user.getUserId() > 0 && user.getUserRole() != null) {
						System.out.println(user.getStatus() + " " + user.getUserRole());

						if (user.getStatus().equals(DBConstants.USER_STATUS_BLOCKED)) {
							response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
							response.setMessage("Your Account is Blocked.Please Contact customersupport@happybus.com");
						} else if (user.getStatus().equals(DBConstants.USER_STATUS_MOBILE_NOT_VERIFIED)
								&& user.getUserRole().equals(RolesConstants.ROLE_PASSENGER)) {
							// if user mobile not verified generate OTP
							Integer otp = OTPGenerator.generateOTP();
							// save in db
							if (otpDAO.saveOtp(otp, user.getUserId(), new Date()) > 0) {
								// send otp sms && mail
								String msg = "Dear user OTP(One Time Password) for registration is " + otp
										+ ". Please use this OTP to complete the registration. - MythriBus";
								String otpStatus = userSmsService.sendSms(user.getMobile(), msg);
								logger.info("OTP Status : " + otpStatus);
								jsonUser = JsonUtil.convertJavaToJson(user);
								response.setData(jsonUser);
								response.setStatus(StatusUtil.HAPPY_STATUS_MOBILE_NOT_VERIFIED);
								response.setMessage("OTP Sent to Registered Mobile Xxx"
										+ user.getMobile().substring(user.getMobile().length() - 3));

							}

						} else if (user.getStatus().equals(DBConstants.USER_STATUS_ACTIVE)) {
							System.out.println(user.getStatus() + " " + user.getUserRole());

							// generate token

							String token = BCrypt.hashpw(TokenGenerator.generateToken(), BCrypt.gensalt());
							// first save the token in db and send token as a
							// response data to client
							int[] count = userAuthenticationDAO.saveToken(user.getUserId(), token, user.getLoginLog());
							if (count != null && count.length > 0 && count[0] > 0) {
								user.setToken(token);
								jsonUser = JsonUtil.convertJavaToJson(user);
								response.setMessage("Login Success");
								response.setData(jsonUser);
								response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
							}
						}
					}
				} else {
					response.setMessage("Invalid Password");
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				}
			} catch (IncorrectResultSizeDataAccessException ie) {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Invalid UserName/Password");
				logger.info("Invalid UserName (OR) Password");
			} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while User Login ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while User Login ::" + e.getMessage());
			}
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response from loginUser :: " + jsonResponse);

		return jsonResponse;
	}

	public String registerUser(String jsonUser) {
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Your Registration is Failure!Please Try Again.");
		logger.info("Entered into registerUser :: " + jsonUser);
		User user = JsonUtil.convertJsonToJava(jsonUser, User.class);
		if (user != null) {
			if (!Validator.isEmailValid(user.getEmail())) {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Invalid Email");
				logger.info("Invalid Email :: ");
			} else if (!Validator.isPhoneNumerValid(user.getMobile())) {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Invalid Phone Number");
				logger.info("Invalid Phone Number :: ");
			} else {
				try {
					// encrypt the password
					// generate token
					String salt = BCrypt.gensalt();
					System.out.println(salt);
					user.setPassword(BCrypt.hashpw(user.getPassword(), salt));
					user.setStatus((byte) 0);
					user.setUserRole(RolesConstants.ROLE_PASSENGER);
					user.setUserName(user.getEmail());
					Long userId = (Long) userDAO.registerUser(user);
					if (userId != null && userId > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage(
								"Your Registration is almost done !Please Enter OTP Sent To Your Registered Mobile.");
						user.setUserId(userId);
						// convert into user obj into json
						jsonUser = JsonUtil.convertJavaToJson(user);
						response.setData(jsonUser);
						try {
							// generate OTP and send sms
							Integer otp = OTPGenerator.generateOTP();
							int count = otpDAO.saveOtp(otp, userId, new Date());
							if (count > 0) {
								// send otp sms && mail
								String msg = "Dear user OTP(One Time Password) for registration is " + otp
										+ ". Please use this OTP to complete the registration. - MythriBus";
								String otpStatus = userSmsService.sendSms(user.getMobile(), msg);
								logger.info("OTP Status : " + otpStatus);
							}
						} catch (DataAccessException de) {
							de.printStackTrace();
							logger.error("Exception occured while saving the OTP :: " + de.getMessage());
						} catch (Exception de) {
							logger.error("Exception occured while saving the OTP :: " + de.getMessage());
						}
					}

				} catch (DataAccessException de) {
					de.printStackTrace();
					logger.error("Exception occured while registering the User");
					response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
					response.setMessage("unable to process your request!please try again");
				}
			}
		}
		// convert response object into json
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of registerUser :: " + jsonResponse);
		return jsonResponse;
	}

	/**
	 * This method contain logic for Getting Old data To User To edit profile *
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author nagaraj.e
	 */
	@Override
	public String getProfile(Long userId) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Your Registration is Failure!Please Try Again.");
		logger.info("Entered into EditProfileMethod :: " + userId);
		if (userId != null) {
			try {// Handle Exceptions
					// getting user Old Data For Editing
				User user = userDAO.getProfile(userId);
				logger.info(user);
				logger.info(user.getEmail());
				logger.info(user.getMobile());
				if (user != null) {
					logger.info(user.getEmail());
					jsonResponse = JsonUtil.convertJavaToJson(user);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Plesase Update your Profile");
					response.setData(jsonResponse);
				}
			} catch (DataAccessException de) {
				logger.error("Exception occured while retriving the  Data");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again");
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Updating The Profile ::" + e.getMessage());
			}

		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response From edit Profile:" + jsonResponse);
		return jsonResponse;
	}

	/**
	 * This method contain logic for update profile*
	 * 
	 * @param userId
	 * @return jsonResponse
	 * @author nagaraj.e
	 */
	@Override
	public String updateProfile(String jsonUser) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Updating Profile is Failure!Please Try Again.");
		logger.info("Entered into UpdateProfileMethod :: " + jsonUser);
		User user = JsonUtil.convertJsonToJava(jsonUser, User.class);
		if (user != null) {
			try {
				// Logic for coverting Java.util.Date to Java.sql.Date
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date parsed;
				parsed = format.parse(user.getDob());
				java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
				Integer count = userDAO.updateProfile(user, sqlDate);
				if (count > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Your Profile Updates Successfully");
				}

			} catch (IncorrectResultSizeDataAccessException ie) {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Invalid Details");
				logger.info("Invalid Data Entered by user");
			} catch (DataAccessException de) {
				de.printStackTrace();
				logger.error("Exception occured while Updating Profile");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again");
				logger.error("Exception Occured while Update The Data ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Update The Data ::" + e.getMessage());
			}

		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response From Update()" + jsonResponse);
		return jsonResponse;
	}

	/**
	 * the method contain logic for Change Password
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author nagaraj.e
	 */
	@Override
	public String changePassword(String jsonUser) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Change Password Failure!Please Try Again.");
		logger.info("Entered into Change Password Method  :: " + jsonUser);
		User user = JsonUtil.convertJsonToJava(jsonUser, User.class);
		if (user != null) {
			try {// handle exceptions
					// get UserPassword based on username
					// call check checkpw() method to compare new Password and
					// old Password
				String hasPwd = userDAO.getHashPassword(user.getUserName());
				if (BCrypt.checkpw(user.getPassword(), hasPwd)) {
					logger.info("newPassword" + user.getNewPassword());
					user.setNewPassword(BCrypt.hashpw(user.getNewPassword(), BCrypt.gensalt()));
					Integer count = userDAO.changePassword(user);
					if (count > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Your Password is Updated Successfully!!!");
					} else {
						response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
						response.setMessage("Problem occured While Accessing Please Try Again");
					}
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Your CurrentPassword is Wrong Please Try again!!");
				}
			} catch (IncorrectResultSizeDataAccessException ie) {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Invalid Password Details");
				logger.info("Invalid Password Details");
			} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Changing The Password ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Changing The Password ::" + e.getMessage());
			}
		}
		// convert response object into json
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of Change Password :: " + jsonResponse);
		return jsonResponse;
	}// Change Password Method

	/**
	 * the method contain logic for forgotPassword
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author srikanth.g
	 */

	@Override
	public String forgotPassword(String jsonUser) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Forgot Password Failure!Please Try Again.");
		logger.info("Entered into Forgot Password Method  :: " + jsonUser);
		User user = JsonUtil.convertJsonToJava(jsonUser, User.class);
		try {
			if (user != null) {
				ForgotPassword result = userDAO.forgotPassword(user);
				// if User Enters Email
				if (user.getEmail() != null && result.getStatus().equals(DBConstants.USER_STATUS_ACTIVE)
						&& user.getEmail().equals(result.getEmail())) {
					logger.info(result.getEmail());
					String subject = "Welcome to HappyBus";
					String body = "Dear user use this link To Reset Your Password :"
							+ "http://localhost:8081/HappyBus/resetForm?userId=" + result.getUserId();
					userEmailService.sendEmail(user.getEmail(), subject, body);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Generated Link Sent to Your Mail please reset Password using your Mail");
				}
				// if user Enters Mobile
				else if(user.getMobile() != null && result.getStatus().equals(DBConstants.USER_STATUS_ACTIVE)
						&& user.getMobile().equals(result.getMobile())) {
					// Send Otp To Mobile
					try {
						logger.info(result.getMobile());
						logger.info(result.getEmail());
						// generate OTP and send sms
						Integer otp = OTPGenerator.generateOTP();
						logger.info(otp);
						logger.info(new Date());
						logger.info("UserId" + result.getUserId());
						logger.info("status" + result.getStatus());
						int forgototp = otpDAO.updateOtp(otp, new Date(), result.getUserId());
						logger.info(forgototp);

						if (forgototp > 0) { // send otp sms && mail String
							/*
							 * String msg =
							 * "Dear user OTP(One Time Password) for ResetPassword is "
							 * + otp +
							 * ". Please use this OTP to Reset your Password. - MythriBus"
							 * ;
							 */
							String msg = "Dear user OTP(One Time Password) for registration is " + otp
									+ ". Please use this OTP to complete the registration. - MythriBus";
							String otpStatus = userSmsService.sendSms(user.getMobile(), msg);
							logger.info("OTP Status : " + otpStatus);
							user.setUserId(result.getUserId());
							user.setMobile(result.getMobile());
							jsonResponse = JsonUtil.convertJavaToJson(user);
						}
						response.setData(jsonResponse);
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Otp Send To your Mobile please enter Otp To Reset Your Password ");

					} catch (DataAccessException de) {
						logger.error("Exception occured while saving the OTP :: " + de.getMessage());
					} catch (Exception de) {
						logger.error("Exception occured while saving the OTP :: " + de.getMessage());
					}
				}

			}

		} catch (IncorrectResultSizeDataAccessException ie) {
			response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
			response.setMessage("Invalid Mobile/Email Details");
			logger.info("Invalid Mobile/Email Details");
		} catch (DataAccessException de) {
			de.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Updating  Interacting::" + de.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Updating The Password ::" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response From Forgot Password" + jsonResponse);
		return jsonResponse;
	}

	/**
	 * the method contain logic for ForgotPassword Otp Verification
	 * 
	 * @param userId,otp
	 * @return jsonResponse
	 * @author srikanth.g
	 */
	@Override
	public String otpCheck(Long userId, Integer otp) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Invalid OTP.");
		logger.info("Entered into OtpCheck Method :: " + userId + "," + otp);
		try {
			if (userId != null && otp != null) {
				Object[] otpCheck = userDAO.otpCheck(userId, otp);
				logger.info("Otp =" + otpCheck[1] + "time difference=" + otpCheck[0]);
				if (((Integer) otpCheck[1]).equals(otp) && (Integer) otpCheck[0] <= 20) {
					Integer count = userDAO.updateUserStatus(userId, 1);
					if (count != null && count > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Your Otp Verified Successfully");
					} else {
						response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
						response.setMessage("Unable to process Your Request");

					}
				}

			}
		} catch (DataAccessException de) {
			de.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Verifying otp ::" + de.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Verifying Otp ::" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("otpChek jsonResponse is" + jsonResponse);
		return jsonResponse;
	}

	/**
	 * the method contain logic for resetPassword
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author srikanth.g
	 */

	@Override
	public String resetPassword(String jsonUser) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Reset Password Failure!Please Try Again.");
		logger.info("Entered into Reset Password Method  :: " + jsonUser);
		User user = JsonUtil.convertJsonToJava(jsonUser, User.class);
		if (user != null) {
			try {// handle exceptions
				logger.info("newPassword" + user.getNewPassword());
				user.setNewPassword(BCrypt.hashpw(user.getNewPassword(), BCrypt.gensalt()));
				Integer count = userDAO.resetPassword(user);
				if (count > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Your Password is Updated Successfully!!!");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Problem occured While Accessing Please Try Again");
				}

			} catch (IncorrectResultSizeDataAccessException ie) {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Invalid Password Details");
				logger.info("Invalid Password Details");
			} catch (DataAccessException de) {
				de.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Reset The Password ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Reset The Password ::" + e.getMessage());
			}
		}
		// convert response object into json
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of Reset Password :: " + jsonResponse);
		return jsonResponse;
	}

	@Override
	public String checkUserName(String userName, String mobile) {
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Your Are Not A registed User!Please Signup");
		String jsonResponse = "";
logger.info("Entered into checkUser() "+userName+" "+mobile);		
		try {
			User user = new User();
			user= userDAO.checkUserName(userName);
			if (user.getUserId() != null && 
					user.getUserId()>0 && user.getCount()>0) {
				response.setMessage("Email Already Registered !");
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				user.setUserId(user.getUserId());
				String jsonUser = JsonUtil.convertJavaToJson(user);
				response.setData(jsonUser);
			} else {
				user = userDAO.checkMobileNumber(mobile);
				if (user.getUserId() != null &&user.getUserId()>0&&user.getCount()>0) {
					response.setMessage("Mobile Already Registered !");
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					user.setUserId(user.getUserId()); 
					String jsonUser = JsonUtil.convertJavaToJson(user);
					response.setData(jsonUser);
				}
			}

		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while checking the UserName :" + de.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while checking the UserName :" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
	logger.info("Response of checkUser() "+jsonResponse);	
		return jsonResponse;
	}

	@Override
	public String getUserRole(Long userId) {

		return userDAO.getUserRole(userId);
	}

	@Override
	public String logoutUser(String userId, String token) {
		logger.info("Entered into Logout User : " + userId + " " + token);

		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		try {
			Integer count = userAuthenticationDAO.logoutUser(userId, token);
			if (count != null && count > 0) {
				response.setMessage("You Are Logged out Successfully");
				response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
			}

		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while logout the user :" + de.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while logout the user :" + e.getMessage());
		}
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response of Logout User : " + jsonResponse);

		return jsonResponse;
	}

	@Override
	public String reGenerateOtp(Long userId, String mobile) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("otp Failure!Please Try Again.");
		logger.info("get the UserID  :: " + userId);
		try {
			if (userId != null && mobile != null) {
				Integer otp = OTPGenerator.generateOTP();
				logger.info(otp);
				logger.info(new Date());

				int forgototp = otpDAO.updateOtp(otp, new Date(), userId);
				logger.info(forgototp);

				if (forgototp > 0) { // send otp sms && mail String
					/*
					 * String msg =
					 * "Dear user OTP(One Time Password) for ResetPassword is "
					 * + otp +
					 * ". Please use this OTP to Reset your Password. - MythriBus"
					 * ;
					 */
					try {
						String msg = "Dear user OTP(One Time Password) for registration is " + otp
								+ ". Please use this OTP to complete the registration. - MythriBus";
						String otpStatus = userSmsService.sendSms(mobile, msg);
						logger.info("OTP Status : " + otpStatus);

						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Otp ReSended To your Mobile please enter NewOtp  ");

					} catch (DataAccessException de) {
						logger.error("Exception occured while saving the OTP :: " + de.getMessage());
					} catch (Exception de) {
						logger.error("Exception occured while saving the OTP :: " + de.getMessage());
					}
				}
			}

		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while checking the UserName :" + de.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while checking the UserName :" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;

	}
	@Override
	public String getUserIdByUserName(String userName) {
		Response response = new Response();
		if(!Validator.isEmailValid(userName))
		{
			response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
			response.setMessage("In  Email Id");
		}else{
		try {
			User user=new User();
			user.setUserName(userName);
			user = userDAO.checkUserName(user.getUserName()); 
			if (user.getCount() != null && user.getCount() > 0) {
				response.setMessage("Email Already Registered !Please Sign-In");
				response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
				String jsonData=JsonUtil.convertJavaToJson(user.getCount());
				response.setData(jsonData);
			} 
			else
			{
				response.setMessage("You email not Register !Please Sign-Up");
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
			}

		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("You email not Register !Please Sign-Up");
			logger.error("Exception Occured while  getUserIdByUserName :" + de.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("unable to process your request");
			logger.error("Exception Occured while  getUserIdByUserName :" + e.getMessage());
		}
	  }
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;
	}

	

}// UserServiceImpl Class
