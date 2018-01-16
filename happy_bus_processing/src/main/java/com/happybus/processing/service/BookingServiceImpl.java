/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.

 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.BookedSeats;
import com.happybus.beans.Booking;
import com.happybus.beans.Passenger;
import com.happybus.beans.Payments;
import com.happybus.beans.Response;
import com.happybus.beans.Routes;
import com.happybus.beans.SeatInfo;
import com.happybus.beans.User;
import com.happybus.integration.dao.BookingDAO;
import com.happybus.integration.dao.BusDAO;
import com.happybus.integration.dao.CouponsDAO;
import com.happybus.integration.dao.PassengerDAO;
import com.happybus.integration.dao.SeatNumberDAO;
import com.happybus.integration.dao.TravelsDAO;
import com.happybus.integration.dao.UserDAO;
import com.happybus.util.JsonUtil;
import com.happybus.util.StatusUtil;

/**
 * BookingServiceImpl is used to perform Business operations on Booking Table
 * 
 * @author Ashish.L
 * @since 1.0
 */
@Service
public class BookingServiceImpl implements BookingService {
	private static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private BookingDAO bookingDAO;
	@Autowired
	private TravelsDAO travelsDAO;
	@Autowired
	private CouponsDAO couponsDAO;
	@Autowired
	private BusDAO busDAO;
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SeatNumberDAO seatNumberDAO;
	@Autowired
	private PassengerDAO passengerDAO;

	@Override
	public String boardingDetails(Integer routeId) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Booking Failure!Please Try Again.");

		try {// handle exceptions

			Routes route = bookingDAO.boardingDetails(routeId);
			if (route != null) {
				String jsonRoute = JsonUtil.convertJavaToJson(route);
				response.setMessage("Boarding points sent");
				response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
				response.setData(jsonRoute);
				jsonResponse = JsonUtil.convertJavaToJson(response);
				return jsonResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while User Login ::" + e.getMessage());

		}

		return jsonResponse;
	}

	/**
	 * UserService is used to implement Bussiness Logic for BookingRetriving
	 * 
	 * @author Ashish.l and nagaraj.e
	 * @since 1.0
	 */
	@Override
	public String seatLayout(Integer routeId) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Please Try Again.");

		try {
			List<BookedSeats> bookedSeatsList = bookingDAO.getBookedSeats(routeId);
			SeatInfo seatInfo = seatNumberDAO.getSeatInfo(routeId);
			List<String> seatNoList = seatNumberDAO.getSeatNumbersList(routeId);
			seatInfo.setSeatNoList(seatNoList);
			seatInfo.setBookedSeatNoList(bookedSeatsList);

			String jsonSeatInfo = JsonUtil.convertJavaToJson(seatInfo);
			logger.info("JsonSeatInfo"+jsonSeatInfo); 
			response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
			response.setMessage("Success.");
			response.setData(jsonSeatInfo);
			jsonResponse = JsonUtil.convertJavaToJson(response);
			logger.info("response"+jsonResponse); 
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Shwoing Seats layout::" + e.getMessage());

		}
			logger.info("Response from Seat Layout"+jsonResponse);
		return jsonResponse;
	}

	@Override
	public String fareCalculationDetails(Double price, Integer noOfSeats) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Booking Failure!Please Try Again.");

		try {// handle exceptions
			Booking booking = new Booking();
			// price calculation
			if (price != null && noOfSeats != null) {
				booking.setFinalFare(price * noOfSeats);
				jsonResponse = JsonUtil.convertJavaToJson(booking);
				return jsonResponse;
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Booking fare Calculation::" + e.getMessage());
		}

		return jsonResponse;
	}

	/**
	 * This method contain logic for cancelTicket
	 * 
	 * @author ainul.a and nagaraj.e
	 * @since 1.0
	 */
	@Override
	public String cancelTicket(String mobile, Integer bookingId) {
		String jsonResponse = "";
		Response response = new Response();
		User user = new User();
		Booking booking = new Booking();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("CancelTicket is Failure!Please Try Again.");
		logger.info("Entered into CancelTIcketMethod :: " + mobile + "" + bookingId);
		if (mobile != null && bookingId != null) {
			try {// Handle Exceptions

				List<Object> list = bookingDAO.cancelTicketGetDate(mobile);
				user = (User) list.get(0);
				booking = (Booking) list.get(1);
				if (mobile.equals(user.getMobile()) && bookingId.equals(booking.getBookingId())) {
					if (booking.getStatus() == 1) {
						Integer count = bookingDAO.cancelTicket(bookingId);
						if (count > 0) {
							response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
							response.setMessage(
									"Your Ticket Cancelled Successfully Money will Refund in your Account with in Two Working Days ");
						}
					} else {
						response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
						response.setMessage("You Are Not Booked Any Ticket or Alredy Cancelled!!!");
					}

				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Entered Booking Id Wrong Please Try Again!!!");
				}

			} catch (DataAccessException de) {
				logger.error("Exception occured while retriving the  Data");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again!!!");

			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Updating The Profile ::" + e.getMessage());
			}

		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response From cancelTicket:" + jsonResponse);
		return jsonResponse;

	}
	
	/**This method contains logic for print ticket
	 * 
	 * @param moblie
	 * @param bookingId
	 * @return
	 * @author Bhusan
	 * @since 1.0
	 */

	public String printTicket(String mobile, Integer bookingId) {
		
		String jsonResponse = "";
		
		Response response = new Response();
		User user = new User();
		Booking booking = new Booking();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("PrintTicket is Failure!Please Try Again.");
		logger.info("Entered into PrintTIcketMethod :: " + mobile + "" + bookingId);
		
		if(mobile != null && bookingId != null){
			try{
				List<Object> list = bookingDAO.printTicketDetails(mobile);
				user = (User) list.get(0);
				logger.info(user.getMobile());
				booking = (Booking) list.get(1);
				logger.info(booking.getBookingId());
				List<Passenger> passengerList=bookingDAO.printTicketPassengerDetails(mobile);
				if(mobile.equals(user.getMobile()) &&  bookingId.equals(booking.getBookingId())){
					
					if(list!=null && list.size()>0 && passengerList !=null && passengerList.size()>0&&booking.getStatus()==1 ){
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Print Your Ticket...!");
						booking.setPassengerList(passengerList); 
						String jsonUser=JsonUtil.convertJavaToJson(user);
						String jsonBooking=JsonUtil.convertJavaToJson(booking);
						response.setDataList(new String[]{jsonUser,jsonBooking}); 					
						
					}
					else {
						response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
						response.setMessage("You Are Not Booked Any Ticket or Alredy Cancelled!!!");
					}
					
				}
				else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("You have Not Booked Any Ticket");
				}

				
			}
			catch (DataAccessException de) {
				de.printStackTrace();
				logger.error("Exception occured while retriving the  Data");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again!!!");
			}
			 catch (Exception e) {
					e.printStackTrace();
					response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
					response.setMessage("Unable to process!Please Try Again");
			 }
			
		}
		
			jsonResponse = JsonUtil.convertJavaToJson(response);
			logger.info("Response From PrintTicket:" + jsonResponse);
			return jsonResponse ;
	}

	/**
	 * the method contain logic for saving Booking Details
	 * 
	 * @param routeid
	 * @return jsonResponse
	 * @author Ashish.L and Nagaraj.e
	 */
	@Override
	public String saveBookingDetails(String jsonBooking) {

		String jsonResponse = "";
		Response response = new Response();
		logger.info("Entered into saveBookingDetails :: " + jsonBooking);
		Booking bookingobj = JsonUtil.convertJsonToJava(jsonBooking, Booking.class);
		logger.info("passengerList::" + bookingobj);		
		List<Passenger> list = bookingobj.getPassengerList();
		logger.info("passengerList::" + list);
		Integer count = 0;
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Booking  failed!Please Try Again.");
		logger.info("Entered into saveBookingDetails :: " + jsonBooking);
		if (bookingobj != null) {
			try {// Handle Exceptions
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.sql.Date bookingDate = new java.sql.Date(new java.util.Date().getTime());
				java.sql.Date dateOfJourney = new java.sql.Date(new java.util.Date().getTime());
				bookingobj.setStatus((byte) 0);
				bookingobj.setBookingDate(new java.util.Date()); 
				Integer bookingId = (Integer) bookingDAO.saveBookingDetails(bookingobj,bookingDate,dateOfJourney);
				if (bookingId != null && bookingId > 0) {
					logger.info(bookingId);
					// call
					// passengerDAO.saveBookingDetails(bookingId,passengerList);
					for (Passenger passengerdetails : list) {
						Passenger passenger = new Passenger();
						passenger.setSeatno(passengerdetails.getSeatno());
						passenger.setName(passengerdetails.getName());
						passenger.setStatus((byte) 0);
						passenger.setGender(passengerdetails.getGender());
						passenger.setAge(passengerdetails.getAge());
						int result = passengerDAO.savePassengerDetails(bookingId, passenger);
						count = count + result;
					}
					if (count > 0 && count == list.size()) {
						logger.info("Count Val=" + count + "List Size=" + list.size());
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Your Booking is Success");
						/*Booking booking = new Booking();
						// set BookingId into Booking obj
						booking.setBookingId(bookingId);
						booking.setNoOfPassengers(count);
						booking.setBoardingPoint(bookingobj.getBoardingPoint());
						booking.setDroppingPoint(bookingobj.getDroppingPoint());
						booking.setBookingDate(bookingobj.getBookingDate());
						booking.setDateOfJourney(bookingobj.getDateOfJourney());
						booking.setFinalFare(bookingobj.getFinalFare());
						booking.setUserId(bookingobj.getUserId());	
				*/	
						bookingobj.setBookingId(bookingId);
						bookingobj.setBookingDate(new Date());
						bookingobj.setDateOfJourney(dateOfJourney);
						bookingobj.setPassengerList(null);
						String jsonBookingResponse = JsonUtil.convertJavaToJson(bookingobj);
						response.setData(jsonBookingResponse);
					}
				}
			} catch (DataAccessException de) {
				de.printStackTrace();
				logger.error("Exception occured while retriving the  Data");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again!!!");

			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while saveingBookingDetails::" + e.getMessage());
			}

		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("saveBookingPassengerDetails:" + jsonResponse);
		return jsonResponse;
	}
	
	/**
	 *This method contain logic for MyBookings
	 * 
	 * @author nagaraj.e
	 * @since 1.0 
	 */
	
	@Override
	public String myBookings(Long userId) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Problem Occured While Showing the Bookings try Again!!.");

		try {
			List<Booking> myBooking = bookingDAO.myBookings(userId);
			if(myBooking!=null&&myBooking.size()>0){
				String jsonMyBooking=JsonUtil.convertJavaListToJson(myBooking);
				response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
				response.setMessage("Your Bookings are!!!"); 
				response.setData(jsonMyBooking); 
			}else{
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("Currently You Do not have any Bookings!!!"); 
			}
		}catch (DataAccessException de) {
			de.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Shwoing MyBooking::" + de.getMessage());
			}
		catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Shwoing MyBooking::" + e.getMessage());

		}
			jsonResponse=JsonUtil.convertJavaToJson(response); 
			logger.info("Response from My Bookings"+jsonResponse);
		return jsonResponse;
	}

	/**
	 *This method contain logic for FinishBooking
	 * 
	 * @author nagaraj.e
	 * @since 1.0 
	 */
	@Override
	public String updateBooking(String jsonPayment) {
		String jsonResponse = "";
		Response response = new Response();
		Payments payment=JsonUtil.convertJsonToJava(jsonPayment,Payments.class);
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("UpdateBooking  is Failure!Please Try Again.");
		logger.info("Entered into updateBooking :: "+ jsonPayment);
		if (payment!= null) {
			try {// Handle Exceptions
						Integer paymentId=(Integer) bookingDAO.savePaymentDetails(payment);		
						logger.info(paymentId);
						Integer count = bookingDAO.updateBooking(payment.getBookingId());
						if (count > 0&& paymentId>0) {
							response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
							response.setMessage("Your Booking is completed Successfully..! \n Thank You..! Visit Again..!");
						}
					 else {
						response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
						response.setMessage("Failure!!!");
					}
				
			} catch (DataAccessException de) {
				logger.error("Exception FinishBooking  Data");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again!!!");

			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Updating The Booking ::" + e.getMessage());
			}

		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response From updateBooking:" + jsonResponse);
		return jsonResponse;
	}

}
