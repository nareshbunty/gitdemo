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

import com.happybus.beans.Booking;
import com.happybus.beans.Response;
import com.happybus.beans.SearchBooking;
import com.happybus.integration.dao.SearchBookingDAO;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;

/**
 * SearchBookingServiceImpl is used to implement Bussiness Logic for Searching Booking Service
 * 
 * @author Satyabrata
 * @since 1.0
 */


@Service
public class SearchBookingServiceImpl implements SearchBookingService {

	private static Logger logger=Logger.getLogger(SearchBookingServiceImpl.class);
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	@Autowired
	private SearchBookingDAO searchBookingDAO;
	
	/**
	 * This method contain logic for searching BookingHistory By Super Admin and Travel Admin
	 * 
	 * @param jsonSearchBooking
	 * @param userRole
	 * @param userId
	 * @param token
	 * @return jsonSearchedBooking
	 * @author Satyabrata
	 */
	
	
	@Override
	public String searchBooking(String jsonSearchBooking,String userRole, Object userId, String token) {
		
		String jsonSearchedBooking="";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage(" Search Booking History not succesful! Please try again");
		logger.info("Entered into searchBooking Webservice :"+jsonSearchBooking+"" + userRole + "" + userId + "" + token);
		SearchBooking booking=JsonUtil.convertJsonToJava(jsonSearchBooking, SearchBooking.class);
		if (userRole.equals(RolesConstants.ROLE_SUPER_ADMIN) && userId != null) {
			try {
				logger.info("calling DAO");
				List<Booking> list = searchBookingDAO.searchBookingBySuperAdmin(booking);
				System.out.println("list" + list);
				
				if (list.size() > 0) {
					System.out.println("entered if");

					response.setData(JsonUtil.convertJavaListToJson(list));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("BookingHistory Searched Successfull");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("BookingHistory Search failure");
				}

			} catch (DataAccessException dae) {
				dae.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while searching BookingHistory" + dae.getMessage());
			} catch (Exception e) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while searching BookingHistory" + e.getMessage());
			}
			jsonSearchedBooking = JsonUtil.convertJavaToJson(response);
			logger.info("Response from search route :: " + jsonSearchedBooking);

		} else if (userRole.equals(RolesConstants.ROLE_TRAVEL_ADMIN) && userId != null) {
			try {

				List<Booking> list = searchBookingDAO.searchBookingByTravelAdmin(booking,userId);

				if (list.size() > 0) {

					response.setData(JsonUtil.convertJavaListToJson(list));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("BookingHistory searched successfull");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("BookingHistory searching failure");
				}

			} catch (DataAccessException dae) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while searching BookingHistory" + dae.getMessage());
			} catch (Exception e) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while searching BookingHistory" + e.getMessage());
			}
			jsonSearchedBooking = JsonUtil.convertJavaToJson(response);
			logger.info("Response from issueType :: " + jsonSearchedBooking);

		}

	
		return jsonSearchedBooking;
	}
}
