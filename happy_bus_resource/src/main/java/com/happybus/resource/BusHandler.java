/*
 * Copyright (c) 2017- 2018, HappyBus and/or its affiliates. All rights reserved.

 * HappyBus PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.happybus.resource;

/**
 * This class is acting as Resource class . It accessed over the N.w
 * 
 * @author Priysheel Darbar
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.AmenitiesService;
import com.happybus.processing.service.BusService;
import com.happybus.processing.service.BusTypeService;


@RestController
@RequestMapping(value = "bus")
public class BusHandler {

	@Autowired
	private BusService busService;
	@Autowired
	private BusTypeService busTypeService;
	@Autowired
	private AmenitiesService amenitiesService;
	
	
	

	/**
	 * the method is a resource method for searchBus
	 * 
	 * @author Vipul Mehta
	 * @param SearchBusParam
	 * @return jsonResponse
	 */
	@RequestMapping(value="searchBusesForPassengers", method=RequestMethod.POST)
	@ResponseBody
	public String searchBusesForPassengers(@RequestBody String json){
	System.out.println("search bus params json in "+ json);	
	json= busService.searchBusesForPassengers(json);
		return json;
		
	}

	/**
	 * the method is a resource method for saveBus
	 * 
	 * @author Priysheel Darbar
	 * @param jsonUser
	 * @return jsonResponse
	 */
	@RequestMapping(value="saveAmenities",method=RequestMethod.POST)
	@ResponseBody
	public String saveAmenities(@RequestBody String jsonAmenities, @RequestParam("userId")long userId, @RequestParam("token")String token){
		return amenitiesService.saveAmenities(jsonAmenities,userId,token);
	}
	
	@RequestMapping(value="getAllAmenities",method=RequestMethod.POST)
	@ResponseBody
	public String getAmenities(@RequestParam("userId")Long userId, @RequestParam("token")String token){
		return amenitiesService.getAllAmenities(userId,token);
	}
	
	@RequestMapping(value="saveBusType",method=RequestMethod.POST)
	@ResponseBody
	public String saveBusType(@RequestBody String jsonBusType,@RequestParam("userId")Long userId,@RequestParam("token")String token){
		return busTypeService.saveBusType(jsonBusType,userId,token);
	}
	
	@RequestMapping(value = "saveBus", method = RequestMethod.POST)
	@ResponseBody
	public String saveBus(@RequestBody String jsonBus, @RequestParam("userId") Long userId, @RequestParam("token") String token) {
		return busService.saveBus(jsonBus,userId,token);
	}

	@RequestMapping(value = "getAllBusType", method = RequestMethod.POST)
	@ResponseBody
	public String getAllBusType(@RequestParam("userId")Long userId,@RequestParam("token")String token ) {
		return busTypeService.getAllBusType(userId, token);
	}
	
	@RequestMapping(value = "getBusType", method = RequestMethod.POST)
	@ResponseBody
	public String getAllBusType(@RequestParam("userId")Long userId, @RequestParam("token")String token, @RequestParam("busTypeId")Object busTypeId) {
		// return busService.getAllBusType();
		return busTypeService.getAllBusType(userId, token);
	}

	@RequestMapping(value = "searchAllBuses", method = RequestMethod.GET)
	public String searchAllBuses(@RequestParam("userId") Long userId, @RequestParam("userRole") String userRole,
			@RequestParam("token") String token) {
		return busService.searchAllBuses(userId, userRole, token);

	}


}
