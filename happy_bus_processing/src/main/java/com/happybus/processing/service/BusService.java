/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;



/**
 * BusTypeService is used to perform business operations on BusType
 * @author Priysheel Darbar
 * @since 1.0
 */

public interface BusService {

	/**
	 * the method contain business logic for add Bus into db
	 * 
	 * @param Bus
	 * @return BusId
	 * @author Priysheel Darbar
	 */
	public String saveBus(String jsonBus, Long userId, String token);
	
	
	
	
	/**
	 * the method contain logic to search bus for passengers
	 * 
	 * @param jsonPassenger
	 * @return jsonResponse
	 * @author Vipul Mehta and Kashi VishwaNath
	 */
	
	public String searchBusesForPassengers(String jsonPassenger);

	
	
	
	/**
	 * the method contain business logic for Search Buses from db
	 * 
	 * @param userId,token,userRole
	 * @return List of buses
	 * @author Shivprasad Kankatte
	 */
	
	public String searchAllBuses(Long userId, String userRole, String token);
	
}
