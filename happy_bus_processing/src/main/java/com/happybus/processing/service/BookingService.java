package com.happybus.processing.service;

/**
 * UserService is used to implement Bussiness Logic for Users
 * 
 * @author Ashish.l and nagaraj.e
 * @since 1.0
 */

public interface BookingService {

	/**
	 * the method contain logic for User Login
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author sathish.bandi
	 */
	
	public String boardingDetails(Integer routeId);
	/**
	 * UserService is used to implement Business Logic for BookingRetriving
	 * 
	 * @author Ashish.l and nagaraj.e
	 * @since 1.0
	 */
	public String saveBookingDetails(String jsonBooking);
    public String seatLayout(Integer routeId); 

    /**
	 * UserService is used to implement Business Logic for BookingRetriving
	 * 
	 * @author Ashish.l and Srikanth.g
	 * @since 1.0
	 */
    
    
    public String fareCalculationDetails(Double price,Integer noOfSeats);
    
	/**
	 *This method contain logic for MyBookings
	 * 
	 * @author nagaraj.e
	 * @since 1.0 
	 */
    public String myBookings(Long userId);
    
    /**
	 *This method contain logic for cancelTicket
	 * 
	 * @author ainul.a and nagaraj.e
	 * @since 1.0 
	 */
	public String cancelTicket(String mobile,Integer bookingId);
	
	/**
	 *This method contain logic for FinishBooking
	 * 
	 * @author nagaraj.e
	 * @since 1.0 
	 */
	public String updateBooking(String jsonPayment); 
	
	/**
	 *This method contain logic for printTicket
	 * 
	 * @author Bhusan
	 * @since 1.0 
	 */
	public String printTicket(String mobile,Integer bookingId);

    
}


