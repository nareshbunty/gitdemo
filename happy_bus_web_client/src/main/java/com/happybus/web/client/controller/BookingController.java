/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.

 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.web.client.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.happybus.beans.Booking;
import com.happybus.beans.Passenger;
import com.happybus.beans.Payments;
import com.happybus.util.JsonUtil;
import com.happybus.web.client.service.BookingService;

/**
 * This class is acting as Controller class . It accessed over the N.w
 * 
 * @author Ashish.L
 */
@Controller
public class BookingController {

	@Autowired
	private BookingService bookingService;

	private static final String WEB_PASSENGER_BOARDING_DETAILS = "happy_boarding_details";
	private static final String WEB_PASSENGER_CANCEL_TICKET = "happy_cancel_ticket";

	private static final String WEB_PASSENGER_PRINT_TICKET = "happy_print_ticket";

	/**
	 * the method is used to boarding details page.
	 * 
	 * 
	 * @return viewName
	 * @author Ashish.L
	 */
	@RequestMapping(value = "boardingDetailsPage", method = RequestMethod.GET)
	public String showBoardingDetailsPage() {
		return WEB_PASSENGER_BOARDING_DETAILS;
	}

	/**
	 * the method is used to show cancelTicketPage.
	 * 
	 * 
	 * @return viewName
	 * @author nagaraj.e anul
	 */
	@RequestMapping(value = "cancelTicketForm", method = RequestMethod.GET)
	public String showcancelTicketForm() {
		return WEB_PASSENGER_CANCEL_TICKET;
	}
	
	/**
	 * This method contain the logic to display printTicketPage
	 * 
	 * 
	 * @return viewName
	 * @author Bhusan
	 */
	@RequestMapping(value="printTicketForm", method=RequestMethod.GET)
	public String showPrintTicketForm(){
		return WEB_PASSENGER_PRINT_TICKET;
	}

	/**
	 * This method contain logic for cancelTicket
	 * 
	 * @author ainul.a and nagaraj.e
	 * @since 1.0
	 */
	@RequestMapping(value = "cancelTicket", method = RequestMethod.POST)
	@ResponseBody
	public String cancelTicket(@RequestParam("mobile") String mobile, @RequestParam("bookingid") Integer bookingId) {
		String jsonResponse = "";
		jsonResponse = bookingService.cancelTicket(mobile, bookingId);
		return jsonResponse;
	}
	@RequestMapping(value = "printTicket", method = RequestMethod.POST)
	@ResponseBody
	public String printTicket(@RequestParam("mobile") String mobile, @RequestParam("bookingid") Integer bookingId) {
		String jsonResponse = "";
		jsonResponse = bookingService.printTicket(mobile, bookingId);
		return jsonResponse;
	}

	/**
	 * This method contain logic for myBookings
	 * 
	 * @author nagaraj.e
	 * @since 1.0
	 */
	@RequestMapping(value = "mybookings", method = RequestMethod.POST)
	@ResponseBody
	public String myBookings(HttpServletRequest request) {
		String jsonResponse = "";
		jsonResponse = bookingService.myBookings(request.getSession(false).getAttribute("userId"));
		return jsonResponse;
	}

	/**
	 * This method contain logic for myBookings
	 * 
	 * @author nagaraj.e
	 * @since 1.0
	 */
	@RequestMapping(value = "mybooking", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView myBookings() {
		ModelAndView modelAndView = new ModelAndView("happy_mybookings");
		return modelAndView;
	}

	/**
	 * the method is a controller method for Booking boarding details
	 * 
	 * @param routeid
	 * @return jsonResponse
	 * @author Ashish.L
	 */
	@RequestMapping(value = "boardingDetails", method = RequestMethod.POST)
	@ResponseBody
	public String boardingDetails(
			@RequestParam("routeId") Integer routeId/*
													 * ,
													 * 
													 * @RequestParam(
													 * "boardingPoints") String
													 * boardingPoints,@
													 * RequestParam(
													 * "droppingPoints") String
													 * droppingPoints
													 */) {
		String jsonResponse = "";
		jsonResponse = bookingService
				.boardingDetails(routeId/* ,boardingPoints,droppingPoints */);
		return jsonResponse;
	}

	/**
	 * the method is a controller method for showing seatLayout details
	 * 
	 * @param routeid
	 * @return jsonResponse
	 * @author Ashish.L
	 */
	@RequestMapping(value = "seatLayoutDetails", method = RequestMethod.GET)
	@ResponseBody
	public String seatLayoutDetails(@RequestParam("routeId") Integer routeId) {
		String jsonResponse = "";
		jsonResponse = bookingService.seatLayoutDetails(routeId);
		return jsonResponse;
	}

	/**
	 * the method is a controller method for saving Booking details
	 * 
	 * @param routeid
	 * @return jsonResponse
	 * @author Ashish.L
	 */
	@RequestMapping(value = "passengerBookingDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveBookingDetails(@RequestBody String jsonPassengerList) {
		String jsonResponse = "";
		System.out.println(jsonPassengerList);
		jsonResponse = bookingService.saveBookingDetails(jsonPassengerList);
		return jsonResponse;
	}

	@RequestMapping(value = "bookingInfo", method = RequestMethod.POST)
	public ModelAndView bookingInfo(@RequestParam("routeId") Integer routeId, @RequestParam("userId") Long userId,
			@RequestParam("bookingId") Integer bookingId, @RequestParam("noOfPassengers") Integer noOfPassengers,
			@RequestParam("boardingPoint") String boardingPoint, @RequestParam("droppingPoint") String droppingPoint,
			@RequestParam("finalFare") Double finalFare, @RequestParam("dateOfJourney") String dateOfJourney,
			@RequestParam("passengerList") String passengerList) {
		List<Passenger> passenger = (List<Passenger>) JsonUtil.convertJsonToJavaList(passengerList, Passenger.class);
		Booking booking = new Booking();
		booking.setUserId(userId);
		booking.setRouteId(routeId);
		booking.setBoardingPoint(boardingPoint);
		booking.setDroppingPoint(droppingPoint);
		booking.setFinalFare(finalFare);
		booking.setBookingId(bookingId);
		booking.setNoOfPassengers(noOfPassengers);

		// List passengerList=new
		// booking.setPassengerList(passengerList);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {
			booking.setDateOfJourney(format.parse(dateOfJourney));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("bookingInfo");
		modelAndView.addObject("booking", booking);
		modelAndView.addObject("passengerList", passenger);
		return modelAndView;
	}

	/**
	 * This method contain details of paymentGateway
	 * 
	 * @author nagaraj.e
	 * @since 1.0
	 */
	@RequestMapping(value = "paymentGateway", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView makePayment(@RequestParam("bookingId") String bookingId,@RequestParam("finalFare") String finalFare) {
		ModelAndView modelAndView = new ModelAndView("happy_payment");
		modelAndView.addObject("bookingId", bookingId);
		modelAndView.addObject("finalFare", finalFare);
		return modelAndView;

	}

	/**
	 * This method contain logic to update booking status and paymentId
	 * 
	 * @author nagaraj.e
	 * @since 1.0
	 */
	@RequestMapping(value = "updateBooking", method = RequestMethod.GET)
	@ResponseBody
	public String updateBooking(@ModelAttribute Payments payment) {
		String jsonResponse = "";
		jsonResponse=bookingService.updateBooking(payment);
		return jsonResponse;

	}

}
