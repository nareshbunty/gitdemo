/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.happybus.processing.service;

/**
 * SearchBookingService is used to implement Bussiness Logic for Searching Booking Service
 * 
 * @author Satyabrata
 * @since 1.0
 */

public interface SearchBookingService {
	
	/**
	 * the method contain logic for Search Booking by travel admin
	 * 
	 * @param bookingId,bookingDate,busRegNo
	 * @return jsonResponse
	 * @author Satyabrata
	 * @param userRole 
	 * @param token 
	 * @param userId 
	 */
	
	//public String searchBookingByTravelAdmin(Integer bookingId,Date bookingDate,String busRegNo);
	public String searchBooking(String jsonSearchBooking,String userRole,Object userId, String Token);
}
