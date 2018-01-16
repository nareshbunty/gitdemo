/*

 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;
/**
 * BusService is used to perform business operations on Bus
 * @author Priysheel Darbar
 * @since 1.0
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.Bus;
import com.happybus.beans.Response;
import com.happybus.beans.SearchBusParams;
import com.happybus.integration.dao.BusDAO;
import com.happybus.integration.dao.SearchBusDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;

@Service
public class BusServiceImpl implements BusService {
	@Autowired
	private SearchBusDAO searchBusDAO;
	@Autowired
	private BusDAO busDao;
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	@Autowired
	private UserEmailService userEmailService;
	@Autowired
	private UserService userService;
	Logger logger = Logger.getLogger(BusServiceImpl.class);

	/**
	 * the method contain business logic for add Bus into db
	 * 
	 * @param Bus
	 * @return BusId
	 * @author Priysheel Darbar
	 */
	@Override
	public String saveBus(String jsonBus, Long userId, String token) {
		String jsonResponse = "";
		logger.info("Entered into saveBus() " + jsonBus + " " + userId + " " + token);

		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Bus not added! Please try again.");

		try {

			if (userAuthenticationService.isAuthenticated(userId, token)) {
				Bus bus = JsonUtil.convertJsonToJava(jsonBus, Bus.class);

				if (bus != null && bus.getBusType() != null && bus.getAmenities() != null && userId != null) {
					if (busDao.saveBus(bus, userId) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("Bus added successfully");
					}
				}
			}
		} catch (UserAuthenticationException uae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage(uae.getMessage());
			logger.error("Exception Occured while Registering the Travel :: " + uae.getMessage());

		} catch (DataAccessException dae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again");
			logger.error("DataAccessException occure while save Bus into Database");
			dae.printStackTrace();
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again");
			logger.error("Exception occure while save Bus into Database");
			e.printStackTrace();
		}

		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("saveBus() method endded. Response from saveBus " + jsonResponse);
		return jsonResponse;

	}

	@Override
	public String searchBusesForPassengers(String jsonPassenger) {
		// logger
		logger.info("Entered into searchBusses  : " + jsonPassenger);

		// creating response object with default settings
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Unable to process, please try again");

		// converting json input into java object
		SearchBusParams sourceDestinationDate = JsonUtil.convertJsonToJava(jsonPassenger, SearchBusParams.class);

		// logger
		logger.info("Entered into searchBusses  : " + sourceDestinationDate.getSource() + " "
				+ sourceDestinationDate.getDestination() + " " + sourceDestinationDate.getDate_of_journey());
		try {
			// Interacting with Search bus DAO
			List searchedBusesList = searchBusDAO.searchBusesForPassengers(sourceDestinationDate);

			// logger
			logger.info("List : " + searchedBusesList + " size : " + searchedBusesList.size());

			if (searchedBusesList != null && searchedBusesList.size() > 0) // checking
																			// weather
																			// buses
																			// are
																			// available
			{
				// converting resulted list of buses into json
				String jsonSearchBussesList = JsonUtil.convertJavaListToJson(searchedBusesList);
				response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
				response.setData(jsonSearchBussesList);
				response.setMessage("Search Bus Operation Successful");

				// logger
				logger.info("Response of  searchBusses  : " + jsonSearchBussesList);
			} else {
				response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
				response.setMessage("No Buses found for the provided source and destination");
			}

		} // try
		catch (DataAccessException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process, please try again");

		}

		return JsonUtil.convertJavaToJson(response);

	}

	@Override
	public String searchAllBuses(Long userId, String userRole, String token) {

		String jsonResponse = "";
		String busJson = "";
		logger.info("Entered into searchAllBuses()" + userId + " " + token + " " + userRole);
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Buses not found Please try again.");
		logger.info("Entered into searchAllBuses :" + userRole + "" + userId + "" + token);
		List<Bus> buses = new ArrayList<>();
		// List<Bus> bus = buses;
		try {
			if (userId != null && userRole.equals(RolesConstants.ROLE_SUPER_ADMIN) && token != null) {
				buses = busDao.searchAllbus();
				if (buses != null && buses.size() > 0) {
					busJson = JsonUtil.convertJavaListToJson(buses);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("buses are retrived");
					response.setData(busJson);
				}
			} else if (userId != null && userRole.equals(RolesConstants.ROLE_TRAVEL_ADMIN) && token != null) {
				System.out.println("BusServiceImpl.searchAllBuses()" + buses);
				buses = busDao.searchAllbusByUserId(userId);
				if (buses != null && buses.size() > 0) {
					busJson = JsonUtil.convertJavaListToJson(buses);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("buses are retrived");
					response.setData(busJson);
				}
			}
		} catch (UserAuthenticationException e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Sorry! you are not authenticate user");
			logger.error("Exception Occured while user authentication :: " + e.getMessage());
		} catch (DataAccessException e) {
			System.out.println("BusServiceImpl.searchAllBuses()");
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again");
			logger.error("DataAccessException occure while get Bus from Database" + e.getMessage());
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again");
			logger.error("DataAccessException occure while get Bus from Database" + e.getMessage());
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("response from serarch Buses" + jsonResponse);
		return jsonResponse;
	}

}
