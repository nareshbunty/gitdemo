/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.

 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;

/**
 * AmenitiesService is used to perform business operations on Amenities
 * 
 * @author Priysheel Darbar
 * @since 1.0
 */
public interface AmenitiesService {

	/**
	 * the method contain logic for add amenities into db
	 * 
	 * @param jsonAmenities
	 * @return String
	 * @author Priysheel Darbar
	 */
	public String saveAmenities(String jsonAmenities, long userId, String token);
	
	/**
	 * the method contain logic for show available amenities 
	 * 
	 * @param no param
	 * @return json List of Amenities Name
	 * @author Priysheel Darbar
	 */
	public String getAllAmenities(Long userId, String token);
	
}
