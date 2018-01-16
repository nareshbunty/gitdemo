package com.happybus.processing.service;
/**
 * SearchBusService is used to implement Bussiness Logic to search bus
 * 
 * @author Vipul Mehta and Kashi VishwaNath
 * @since 1.0
 */
public interface SearchBusService {
	
	/**
	 * the method contain logic to search bus for passengers
	 * 
	 * @param jsonPassenger
	 * @return jsonResponse
	 * @author Vipul Mehta and Kashi VishwaNath
	 */
	
	public String searchBusesForPassengers(String jsonPassenger);

}
