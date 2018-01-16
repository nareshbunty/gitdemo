package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.BookingService;
import com.happybus.processing.service.SearchBookingService;

/**
 * This class is acting as Resource class . It accessed over the N.w
 * 
 * @author Ashish.L
 */
@RestController
@RequestMapping(value = "book")
public class BookingHandler {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private SearchBookingService bookingHistory; 

	/**
	 * the method is a resource method for Booking boarding details
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author Ashish.L and Srikanth.G
	 */
	@RequestMapping(value = "boardingDetails", method = RequestMethod.GET)
	@ResponseBody
	public String boardingDetails(@RequestParam("routeId") Integer routeId) {
		return bookingService.boardingDetails(routeId);
	}

	/**
	 * the method is a resource method for showing seatLayout details
	 * 
	 * @param routeid
	 * @return jsonResponse
	 * @author Ashish.L and Nagaraj.E
	 */

	@RequestMapping(value = "seatLayoutDetails", method = RequestMethod.GET)
	@ResponseBody
	public String seatLayout(@RequestParam("routeId") Integer routeId) {
		return bookingService.seatLayout(routeId);
	}

	/**
	 * the method is a resource method for Booking
	 * 
	 * @param price
	 *            and noOfSeats
	 * @return jsonResponse
	 * @author Ashish.L
	 */

	@RequestMapping(value = "seatFareDetails", method = RequestMethod.GET)
	@ResponseBody
	public String fareCalculationDetails(@RequestParam("price") Double price,
			@RequestParam("noOfSeats") Integer noOfSeats) {
		return bookingService.fareCalculationDetails(price, noOfSeats);
	}

	/**
	 * the method is a resource method of Booking for registerUser
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author Ashish.L
	 */
	@RequestMapping(value = "saveBookingDetails", method = RequestMethod.POST)
	@ResponseBody
	public String saveBookingPassengerDetails(@RequestBody String jsonBooking) {
		return bookingService.saveBookingDetails(jsonBooking);
	}

	/**
	 *This method contain logic for cancelTicket
	 * 
	 * @author ainul.a and nagaraj.e
	 * @since 1.0 
	 */

	@RequestMapping(value = "cancelTicket", method = RequestMethod.GET)
	@ResponseBody
	public String cancelTicket(@RequestParam("mobile") String mobile, @RequestParam("bookingId") Integer bookingId) {
		return bookingService.cancelTicket(mobile, bookingId);
	}
	
	/**
	 * This method contains logic for Print Ticket
	 * 
	 * @author Bhusan
	 * @since 1.0
	 */
	@RequestMapping(value="printTicket", method=RequestMethod.GET)
	@ResponseBody
	public String printTicket(@RequestParam("mobile") String mobile, @RequestParam("bookingId")Integer bookingId){
		return bookingService.printTicket(mobile, bookingId);
	}
	
	/**
	 * This method contains logic  method for searchBooking
	 * 
	 * @author Satyabrata
	 * @param jsonSearchBooking
	 * @return jsonResponse
	 */
	@RequestMapping(value="searchBooking", method=RequestMethod.POST)
	@ResponseBody
	public String searchBookingHistory(@RequestBody String jsonSearchBooking,@RequestParam("userRole") String userRole, @RequestParam("userId") Long userId,
			@RequestParam("token") String token) {
		jsonSearchBooking=bookingHistory.searchBooking(jsonSearchBooking,userRole, userId, token);
		return jsonSearchBooking;
		
	}
	/**
	 *This method contain logic for MyBookings
	 * 
	 * @author nagaraj.e
	 * @since 1.0 
	 */
	@RequestMapping(value = "myBooking", method = RequestMethod.GET)
	@ResponseBody
	public String seatLayout(@RequestParam("userId") Long userId) {
		return bookingService.myBookings(userId); 
	}
	/**
	 * This method contain logic to update booking status final
	 * 
	 * @author nagaraj.e
	 * @since 1.0
	 */
	@RequestMapping(value = "updateBooking", method = RequestMethod.POST)
	@ResponseBody
	public String makePay(@RequestBody String jsonPayment) {
		return bookingService.updateBooking(jsonPayment);
	}

}
