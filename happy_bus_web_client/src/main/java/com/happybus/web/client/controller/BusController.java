package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.beans.Amenities;
import com.happybus.beans.Bus;
import com.happybus.beans.SearchBusParams;
import com.happybus.util.JsonUtil;
import com.happybus.web.client.service.BusService;
import com.happybus.web.client.service.SearchBusService;

@Controller
public class BusController {

	Logger logger = Logger.getLogger("BusController");
	@Autowired
	private SearchBusService searchBusService;

	@Autowired
	private BusService busService;

	@RequestMapping(value = "addBus", method = RequestMethod.GET)
	public String showBusRegistration() {

		return "busRegistration";
	}

	@RequestMapping(value = "addBusType", method = RequestMethod.GET)
	public String showBusTypeRegistration() {

		return "busTypeRegistration";
	}

	@RequestMapping(value = "addAmenities", method = RequestMethod.GET)
	public String showAmenitiesRegistration() {
		return "amenitiesRegistration";
	}

	@RequestMapping(value = "saveAmenities", method = RequestMethod.POST)
	@ResponseBody
	public String saveAmenities(@ModelAttribute Amenities amenities, HttpServletRequest req) {
		System.out.println("save amenities coantroller calling");
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			jsonResponse = busService.registerAmenities(amenities, req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
	}

	/*
	 * @RequestMapping(value="saveBusType",method=RequestMethod.POST)
	 * 
	 * @ResponseBody public String saveAmenities(@ModelAttribute BusType
	 * busType, HttpServletRequest req){
	 * System.out.println("save busType coantroller calling"); String
	 * jsonResponse = ""; if(req.getSession(false)!=null &&
	 * req.getSession(false).getAttribute("userId")!=null){
	 * jsonResponse=busService.registerBusType(busType,req.getSession(false).
	 * getAttribute("userId"),req.getSession(false).getAttribute("token")); }
	 * else{
	 * 
	 * } return jsonResponse; }
	 */
	@RequestMapping(value = "getAllAmenities", method = RequestMethod.POST)
	@ResponseBody
	public String getAllAmenities(HttpServletRequest req) {
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			jsonResponse = busService.getAllAmenities(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
	}

	@RequestMapping(value = "getAllBusType", method = RequestMethod.POST)
	@ResponseBody
	public String getAllBusType(HttpServletRequest req) {
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			jsonResponse = busService.getAllBusType(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
	}

	@RequestMapping(value = "saveBus", method = RequestMethod.POST)
	@ResponseBody
	public String saveBus(@RequestBody String jsonBus, HttpServletRequest req) {

		System.out.println("save bus coantroller calling");

		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			Bus bus = JsonUtil.convertJsonToJava(jsonBus, Bus.class);
			System.out.println(bus);
			bus.setCreatedBy((Long) req.getSession(false).getAttribute("userId"));
			bus.setStatus((byte) 1);
			jsonResponse = busService.registerBus(bus, req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		} else {

		}
		return jsonResponse;
	}

	@ResponseBody
	@RequestMapping(value = "searchBus", method = RequestMethod.POST)
	public String searchBus(@ModelAttribute SearchBusParams searchBusParams) {
		String jsonBusDetails = searchBusService.searchBus(searchBusParams);
		return jsonBusDetails;
	}

	@RequestMapping(value = "searchAllBus", method = RequestMethod.GET)
	public String showBuses() {
		return "happy_searchbus_admin";
	}

	@ResponseBody
	@RequestMapping(value = "searchAllBuses", method = RequestMethod.POST)
	public String searchAllBuses(HttpServletRequest req) {

		logger.info("entered into searchAllBuses()POST");

		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			System.out.println("BusController.searchAllBuses()");
			System.out.println(req.getSession(false).getAttribute("userId"));
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse = busService.searchBusByAdmin(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("userRole"), req.getSession(false).getAttribute("token"));
		} else {

		}
		System.out.println(jsonResponse);
		return jsonResponse;
		

	}
}
