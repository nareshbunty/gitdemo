package com.happybus.processing.service;

public interface RoutesService {
	/**
	 * UserService is used to implement Bussiness Logic for Users
	 * 
	 * @author Nagaraj.e
	 * @since 1.0
	 */

	/**
	 * the method contain logic for User Login
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author sathish.bandi
	 */

	public String searchRoutes(String userRole,Object userId, String Token);

	public String addRoute(String json, String userRole, Long userId, String token);
	
	public String getRoutes(String userRole,Long userId,String token,Integer routeId,Integer travelId);
	
	public String editRoutes(String userRole,Long userId,String token,Integer travelId,String jsonRoutes);

}
