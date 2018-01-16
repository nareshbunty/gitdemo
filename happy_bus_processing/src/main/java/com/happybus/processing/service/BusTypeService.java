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

public interface BusTypeService {
	/**
	 * the method contain business logic for add BusType into db
	 * 
	 * @param BusType
	 * @return BusTypeId
	 * @author Priysheel Darbar
	 */
	public String saveBusType(String jsonBusType, Long userId, String token);
	
	/**
	 * the method contain business logic for get all BusType from db
	 * 
	 * @param no param
	 * @return List of BusTypeId
	 * @author Priysheel Darbar
	 */
	public String getAllBusType(Long userId, String token);
}
