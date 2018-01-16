/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.

 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.Amenities;
import com.happybus.beans.Response;
import com.happybus.integration.dao.AmenitiesDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.util.JsonUtil;
import com.happybus.util.StatusUtil;

/**
 * AmenitiesService is used to perform business operations on Amenities
 * 
 * @author Priysheel Darbar
 * @since 1.0
 */
@Service
public class AmenitiesServiceImpl implements AmenitiesService {

	private static Logger logger = Logger.getLogger(AmenitiesServiceImpl.class);
	@Autowired
	private AmenitiesDAO amenitiesDao;
	@Autowired
	private UserAuthenticationService userAuthenticationService;

	/**
	 * the method contain logic for add amenities into db
	 * 
	 * @param jsonAmenities
	 * @return String
	 * @author Priysheel Darbar
	 */
	public String saveAmenities(String jsonAmenities, long userId, String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("amenities not added! Please try again");
		logger.info("Entered into saveAmenities: " + jsonAmenities);

		try {
			if (userAuthenticationService.isAuthenticated(userId, token)) {
				if (jsonAmenities != null) {
					Amenities amenities = JsonUtil.convertJsonToJava(jsonAmenities, Amenities.class);
					if (amenitiesDao.saveAmenities(amenities) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("amenitites added successfully.");
					}
				}
			}
		} catch (UserAuthenticationException uae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process");
			logger.error("Exception occure while authenticate user " + uae.getMessage());
		} catch (DataAccessException dae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to proccess!Please try again.");
			logger.error("Exception occure while save amenities into db" + dae.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to proccess!Please try again.");
			logger.error("Exception occure while save amenities into db" + e.getMessage());
		}

		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response from Amenities :: " + jsonResponse);
		return jsonResponse;

	}

	/**
	 * the method contain logic for show available amenities
	 * 
	 * @param no
	 *            param
	 * @return Json List of Amenities Name
	 * @author Priysheel Darbar
	 */
	public String getAllAmenities(Long userId, String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("amenities not retrive! Please try again");
		logger.info("Entered into getAllAmenities :");

		try {
			if (userAuthenticationService.isAuthenticated(userId, token)) {
				List<Amenities> amenitiesList = amenitiesDao.getAllAmenities();
				if (amenitiesList.size() > 0) {
					// String[] amenitiesArray = new
					// String[amenitiesList.size()];
					// for (int i = 0; i < amenitiesList.size(); i++) {
					// amenitiesArray[i] =
					// JsonUtil.convertJavaToJson(amenitiesList.get(i));
					// }
					//

					response.setData(JsonUtil.convertJavaToJson(amenitiesList));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("All Amenities retrive successfully");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("No amenities found in database");
				}
			}
		} catch (UserAuthenticationException uae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setData("Unable to process");
			logger.error("Exception occure while authentication of user" + uae.getMessage());
			uae.printStackTrace();
		} catch (DataAccessException dae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again.");
			logger.error("Exception occured while get all amenities" + dae.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again.");
			logger.error("Exception occured while get all amenities" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response from Amenities :: " + jsonResponse);
		return jsonResponse;
	}
}
