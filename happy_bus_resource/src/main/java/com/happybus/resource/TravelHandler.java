package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.TravelService;

@RestController
@RequestMapping("travel")
public class TravelHandler {
    @Autowired
	private TravelService travelService;
    @RequestMapping(value="addTravel",method=RequestMethod.POST)
	@ResponseBody
    public String registerTravel(
    @RequestBody String jsonTravel,
   @RequestParam("userId") Long userId,
    @RequestParam("token") String token){
		return travelService.registerTravel(jsonTravel,userId, token);
	}
    /**
	 * This method contains check travel register number alredy there or not
	 * @param registerNumber
	 * @return
	 */
	@RequestMapping(value = "checkTravelRegisterNumber/{registerNumber}")
	@ResponseBody
	public String checkTravelRegisterNumber(@PathVariable("registerNumber") String registerNumber) {
		return travelService.checkTravelRegisterNumber(registerNumber);
	}
	
	/**
	 * This method contains logic  method for searchBooking
	 * 
	 * @author Pratik
	 * @param jsonSearchTravel
	 * @return jsonResponse
	 */
	@RequestMapping(value="searchTravel", method=RequestMethod.POST)
	@ResponseBody
	public String searchTravels(@RequestBody String jsonSearchTravel){
		jsonSearchTravel=travelService.searchTravels(jsonSearchTravel);
		return jsonSearchTravel;
		
	}
	
	
	
}
