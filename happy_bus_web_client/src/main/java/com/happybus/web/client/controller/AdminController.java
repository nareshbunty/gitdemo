


package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.beans.Bank_Details;
import com.happybus.beans.SearchBooking;
import com.happybus.beans.Travel;
import com.happybus.beans.User;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.web.client.service.BookingService;
import com.happybus.web.client.service.CustomerService;
import com.happybus.web.client.service.RouteService;
import com.happybus.web.client.service.TravelService;

@Controller
public class AdminController {
	@Autowired
	private TravelService travelService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private RouteService routeService;
	@Autowired
	private BookingService bookingService;
	private Object routeId; 
	
	private static Logger logger = Logger.getLogger(AdminController.class);
	private static final String WEB_CUSTOMER_SUPORT_REGISTRATION = "customer_support_frame_register";

	@RequestMapping(value = "adminDashboard", method = RequestMethod.GET)
	public String showAdminPage() {
		return "adminDashboard";
	}

	@RequestMapping(value = "addTravel", method = RequestMethod.GET)
	public String showTravelRegistrationPage() {
		return "travelRegistration";
	}

	@ResponseBody

	@RequestMapping(value = "addTravel1", method = RequestMethod.POST)
	public String travelRegistration(HttpServletRequest req, @ModelAttribute Travel travel,
			@ModelAttribute Bank_Details bank_Details, @ModelAttribute User user) {
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			travel.setBankDetails(bank_Details);
			travel.setCreatedBy((Long) req.getSession(false).getAttribute("userId"));

			user.setAddress(travel.getTravelAddress());
			user.setEmail(travel.getTravelEmail());
			user.setUserName(user.getEmail());
			user.setUserRole(RolesConstants.ROLE_TRAVEL_ADMIN);
			user.setCreatedBy((Long) req.getSession(false).getAttribute("userId"));
			user.setStatus((byte) 1);
			travel.setStatus((byte) 1);
			travel.setUser(user);
			jsonResponse = travelService.registerTravel(travel, req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}

		return jsonResponse;
	}

	@RequestMapping(value = "addCustomerSupport", method = RequestMethod.GET)
	public String showCustomerSupportRegistrationPage() {

		logger.info("entered into showCustomerSupportRegistrationPage()GET");

		return WEB_CUSTOMER_SUPORT_REGISTRATION;
	}

	@ResponseBody
	@RequestMapping(value = "addCustomerSupport", method = RequestMethod.POST)
	public String customerSupportRegistration(HttpServletRequest req, @RequestParam("mobile") String mobile,
			@RequestParam("email") String email) {

		logger.info("entered into showCustomerSupportRegistrationPage()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {

			User user = new User();
			user.setEmail(email);
			user.setUserName(email);
			user.setMobile(mobile);

			user.setStatus((byte) 1);
			user.setCreatedBy((Long) req.getSession(false).getAttribute("userId"));
			user.setUserRole(RolesConstants.ROLE_CUSTOMER_SUPPORT);

			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse = customerService.registerCustomerSupport(user, req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
	}

	@RequestMapping(value = "checkTravelRegisterNumber", method = RequestMethod.GET)
	@ResponseBody
	
	public String checkTravelRegisterNumber(@RequestParam("registerNumber") String registerNumber) {

		return travelService.checkTravelRegisterNumber(registerNumber);
	}
	
	
	
	@RequestMapping(value = "searchRoutes", method = RequestMethod.GET)
	public String showSearchRoutes() {
		logger.info("entered into searchRoutes()GET");
		return "search_routes";
	}
	@ResponseBody
	@RequestMapping(value = "searchRoutes1", method = RequestMethod.POST)
	public String searchRoutes(HttpServletRequest req) {

		logger.info("entered into searchRoutes()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null  && req.getSession(false).getAttribute("userId") != null)  {

			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse =routeService.searchRoutes( req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
		
	}
	


	@RequestMapping(value = "editRoutes", method = RequestMethod.GET)
	public String editRoutes() {
		logger.info("entered into editRoutes()GET");
		return "edit_routes";
	}
	
	@ResponseBody
	@RequestMapping(value = "getRoutes", method = RequestMethod.POST)
	public String getRoutes(HttpServletRequest req,@RequestParam("routeId") Integer routeId,@RequestParam("travelId") Integer travelId) {

		logger.info("entered into editRoutes()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null )  {

			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse =routeService.getRoutes( req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"),travelId,routeId);
		} else {

		}
		return jsonResponse;
	}
	
	@ResponseBody
	@RequestMapping(value = "editRoutes1", method = RequestMethod.POST)
	public String editRoutes(HttpServletRequest req,@RequestParam("source") String source,@RequestParam("destination") String destination,@RequestParam("routeId") Integer routeId,@RequestParam("price") Double price,@RequestParam("travelId") Integer travelId) {

		logger.info("entered into editRoutes()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null )  {

			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));


			jsonResponse =routeService.editRoutes( req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"),source,destination,routeId,price,travelId);


//			jsonResponse =routeService.editRoutes( req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("userId"),
	//				req.getSession(false).getAttribute("token"),source,destination,serviceNo,routeId,price);

		} else {

		}
		return jsonResponse;
	}
	
	/**
	 * This method contain logic for searchBooking
	 * 
	 * @param searchBooking
	 * @return jsonResponse
	 * @author Satyabrata
	 */
	
	@RequestMapping(value="searchBookingForSuperAdmin",method=RequestMethod.GET)	
	  public String showbookingSearchPage(){
		logger.info("entered into showbookingSearchPage()GET");
		  return "happy_search_booking_by_super_admin";
	  }
	
	
	@RequestMapping(value="searchBooking1",method=RequestMethod.POST)
	@ResponseBody	
	public String searchBooking(@ModelAttribute SearchBooking searchBooking,HttpServletRequest request){
		String targetViewName="happy_search_booking_by_super_admin";
		
		SearchBooking booking=new SearchBooking();		
		booking.setBookingId(searchBooking.getBookingId());		
		booking.setBusRegNo(searchBooking.getBusRegNo());
		booking.setStartBookingDate(searchBooking.getStartBookingDate());
		booking.setEndBookingDate(searchBooking.getEndBookingDate());	
		System.out.println("Entered in to controoler "+booking);
		String jsonSearchBookingResponse=JsonUtil.convertJavaToJson(booking);
		String searchBookingHistory= bookingService.searchBooking(jsonSearchBookingResponse,request.getSession(false).getAttribute("userId"),request.getSession(false).getAttribute("token"),request.getSession(false).getAttribute("userRole"));
		System.out.println(searchBookingHistory);
		return searchBookingHistory;
	
	}

	
	
	
		
}
