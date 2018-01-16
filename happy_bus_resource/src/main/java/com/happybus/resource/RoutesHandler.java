package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.RoutesService;

//import com.happybus.processing.service.RoutesService;

/**
 * This class is acting as Resource class . It accessed over the N.w
 * 
 * @author Ashish.L
 */
@RestController
@RequestMapping(value = "Routes")
public class RoutesHandler {

	@Autowired
	private RoutesService routesService;

	@RequestMapping(value = "searchRoutes", method = RequestMethod.GET)
	@ResponseBody
	public String searchRoutes(@RequestParam("userRole") String userRole, @RequestParam("userId") Long userId,
			@RequestParam("token") String token) {

		return routesService.searchRoutes(userRole, userId, token);
	}
	

	@RequestMapping(value = "getRoutes", method = RequestMethod.GET)
	@ResponseBody
	public String getRoutes(@RequestParam("userRole") String userRole, @RequestParam("userId") Long userId,
			@RequestParam("token") String token,@RequestParam Integer routeId,@RequestParam Integer travelId ) {

		return routesService.getRoutes(userRole, userId, token,routeId,travelId);
	
	}

	@RequestMapping(value = "editRoutes", method = RequestMethod.POST)
	@ResponseBody
	public String editRoutes(@RequestParam("userRole") String userRole, @RequestParam("userId") Long userId,
			@RequestParam("token") String token,@RequestParam("travelId") Integer travelId,@RequestBody String jsonRoutes ) {

		return routesService.editRoutes(userRole, userId, token,travelId, jsonRoutes);
	}

	@RequestMapping(value = "addRoutes", method = RequestMethod.GET)
	@ResponseBody
	public String addRoute(@RequestBody String json, @RequestParam("userRole") String userRole,
			@RequestParam("userId") Long userId, @RequestParam("token") String token)

	{

		return routesService.addRoute(json, userRole, userId, token);

	}

}