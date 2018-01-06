package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.beans.SearchBooking;
import com.happybus.util.JsonUtil;
import com.happybus.web.client.service.BookingService;
import com.happybus.web.client.service.RouteService;



@Controller
public class TravelAdminController {
	
	@Autowired
	private BookingService bookingService; 
	
	@Autowired
	private RouteService routeService;
	private static Logger logger = Logger.getLogger(AdminController.class);
	
	@RequestMapping(value = "traveladminDashboard", method = RequestMethod.GET)
	public String showTravelAdminPage() {
		return "travelAdminDashboard";
	}

	@RequestMapping(value = "searchTravelAdminRoutes", method = RequestMethod.GET)
	public String showTravelSearchRoutes() {
		logger.info("entered into searchRoutes()GET");
		return "search_routes";
	}
	@ResponseBody
	@RequestMapping(value = "searchRoutes", method = RequestMethod.POST)
	public String searchRoutes(HttpServletRequest req) {

		logger.info("entered into searchRoutes()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null /*&& req.getSession(false).getAttribute("routeId") != null && req.getSession(false).getAttribute("travelId") != null*/ && req.getSession(false).getAttribute("userId") != null)  {

			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse =routeService.searchRoutes( req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
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
	
	@RequestMapping(value="searchBookingForTravelAdmin",method=RequestMethod.GET)	
	  public String showbookingSearchPage(){
		logger.info("entered into showbookingSearchPage()GET");
		  return "happy_search_booking_by_travel_admin";
	  }
	
	
	@RequestMapping(value="searchBooking2",method=RequestMethod.POST)
	@ResponseBody	
	public String searchBooking(@ModelAttribute SearchBooking searchBooking,HttpServletRequest request){
		String targetViewName="happy_search_booking_by_travel_admin";
		
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


	@RequestMapping(value = " addroutes", method = RequestMethod.GET)
	public String showaddroutes() {
		logger.info("entered into addRoutes()GET");
		return "addroutes";
	}
	@ResponseBody
	@RequestMapping(value = "addRoutes1", method = RequestMethod.POST)
	public String addroutes(HttpServletRequest req) {

		logger.info("entered into addRoutes()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null /*&& req.getSession(false).getAttribute("routeId") != null && req.getSession(false).getAttribute("travelId") != null*/ && req.getSession(false).getAttribute("userId") != null)  {

			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse =routeService.Addroutes( req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
		
	}
	
	@RequestMapping(value = "editRoutesTravelAdmin", method = RequestMethod.GET)
	public String showEditRoutes() {
		logger.info("entered into searchRoutes()GET");
		return "edit_routes_travel_admin";
	}
}