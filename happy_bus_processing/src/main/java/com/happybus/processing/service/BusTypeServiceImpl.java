/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.
 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.processing.service;
/**
 * BusTypeServiceImpl is used to perform business operations on BusType
 * @author Priysheel Darbar
 * @since 1.0
 */

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.BusType;
import com.happybus.beans.Response;
import com.happybus.integration.dao.BusTypeDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.util.JsonUtil;
import com.happybus.util.StatusUtil;

@Service
public class BusTypeServiceImpl implements BusTypeService {

	@Autowired
	private BusTypeDAO bustypeDao;
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	Logger logger = Logger.getLogger(BusTypeServiceImpl.class);

	/**
	 * the method contain business logic for add BusType into db
	 * 
	 * @param BusType
	 * @return BusTypeId
	 * @author Priysheel Darbar
	 */
	@Override
	public String saveBusType(String jsonBusType, Long userId, String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("BusType not added! Please try again");
		logger.info("Entered into saveBusType :" + jsonBusType);
		if (userAuthenticationService.isAuthenticated(userId, token)) {
			if (jsonBusType != null) {
				try {
					BusType busType = JsonUtil.convertJsonToJava(jsonBusType, BusType.class);
					if (bustypeDao.saveBusType(busType) > 0) {
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
						response.setMessage("BusType added successfully");
					}

				} catch (UserAuthenticationException uae) {
					response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
					response.setData("Unable to process");
					logger.error("Exception occure while authentication of user" + uae.getMessage());
					uae.printStackTrace();
				} catch (DataAccessException dae) {
					response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
					response.setMessage("Unable to process");
					logger.error("Exception occure while save busType " + dae.getMessage());
					dae.printStackTrace();
				} catch (Exception e) {
					response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
					response.setMessage("Unable to process");
					logger.error("Exception occure while save busType " + e.getMessage());
					e.printStackTrace();
				}
			}

		}

		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("saveBusType method endded. Response from saveBusType " + jsonResponse);
		return jsonResponse;
	}

	/**
	 * the method contain business logic for get all BusType from db
	 * 
	 * @param no
	 *            param
	 * @return List of BusTypeId
	 * @author Priysheel Darbar
	 */
	@Override
	public String getAllBusType(Long userId, String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("BusType details not retrive! Please try again");
		logger.info("Entered into getAllBusType");

		try {
			if (userAuthenticationService.isAuthenticated(userId, token)) {
				List<BusType> busTypeList = bustypeDao.getAllBusType();
				if (busTypeList.size() > 0 && busTypeList != null) {
					// String[] busTypeArray = new String[busTypeList.size()];
					// for (int i = 0; i < busTypeList.size(); i++) {
					// busTypeArray[i] =
					// JsonUtil.convertJavaToJson(busTypeList.get(i));
					// }
					response.setData(JsonUtil.convertJavaToJson(busTypeList));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("All BusType retrive successfully");
				} 
				else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("No BusType found in Database");
				}

			}
		} catch (UserAuthenticationException uae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setData("Unable to process");
			logger.error("Exception occure while authentication of user" + uae.getMessage());
			uae.printStackTrace();
		} catch (DataAccessException dae) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again");
			logger.error("Exception occure while get all BusType from Database");
			dae.printStackTrace();
		} catch (Exception e) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process! Please try again");
			logger.error("Exception occure while get all BusType from Database");
			e.printStackTrace();
		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
			return jsonResponse;
		
	}

}
