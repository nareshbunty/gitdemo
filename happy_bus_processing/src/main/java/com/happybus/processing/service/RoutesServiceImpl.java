package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.Response;
import com.happybus.beans.Routes;
import com.happybus.integration.dao.RoutesDAO;
import com.happybus.processing.exceptions.UserAuthenticationException;
import com.happybus.util.JsonUtil;
import com.happybus.util.RolesConstants;
import com.happybus.util.StatusUtil;

@Service
public class RoutesServiceImpl implements RoutesService {

	private static Logger logger = Logger.getLogger(RoutesServiceImpl.class);
	@Autowired
	private RoutesDAO routesDAO;
	@Autowired
	private UserAuthenticationService userAuthenticationService;

	// to search the routes by travel admin and super admin
	@Override
	public String searchRoutes(String userRole, Object userId, String token) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage(" Search Routes not succesful! Please try again");
		logger.info("Entered into searchRoutes Webservice :" + userRole + "" + userId + "" + token);
		if (userRole.equals(RolesConstants.ROLE_SUPER_ADMIN) && userId != null) {
			try {

				List<Routes> list = routesDAO.searchRoutes();
				System.out.println("list" + list);

				if (list.size() > 0) {
					System.out.println("entered if");

					response.setData(JsonUtil.convertJavaListToJson(list));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Searchroutes successfull");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Searchroutes failure");
				}

			} catch (DataAccessException dae) {
				dae.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while geting Searchroutes" + dae.getMessage());
			} catch (Exception e) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while get geting Searchroutes" + e.getMessage());
			}
			jsonResponse = JsonUtil.convertJavaToJson(response);
			logger.info("Response from search route :: " + jsonResponse);

		} else if(userRole.equals(RolesConstants.ROLE_TRAVEL_ADMIN) && userId != null) {
			try {
                     logger.info("calling");
				List<Routes> routes = routesDAO.searchRoutes(userId);

				if (routes.size() > 0) {

					response.setData(JsonUtil.convertJavaListToJson(routes));
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Searchroutes successfull");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Searchroutes failure");
				}

			} catch (DataAccessException dae) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while geting Searchroutes" + dae.getMessage());
			} catch (Exception e) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while get geting Searchroutes" + e.getMessage());
			}
			jsonResponse = JsonUtil.convertJavaToJson(response);
			logger.info("Response from searchroute :: " + jsonResponse);

		}

		return jsonResponse;
	}

	public String addRoute(String json, String userRole, Long userId, String token) {
		// creating response object and defining default values
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("unable to process, plz try again with valid inputs");

		logger.info("entered into routesservice.addroute()");

		// Business Logic

		try {
			if (userAuthenticationService.isAuthenticated(userId, token)) {
				logger.info("entered into outer if");

				System.out.println("vipul1:  " + userRole);
				if (userRole.equals(RolesConstants.ROLE_SUPER_ADMIN)
						|| userRole.equals(RolesConstants.ROLE_TRAVEL_ADMIN)) {
					logger.info("entered into outer if");
					System.out.println("vipul2:  " + userRole);
					// converting json into domain object
					Routes route = JsonUtil.convertJsonToJava(json, Routes.class);

					// calling DAO layer
					Integer count = routesDAO.addRoute(route);
					if (count > 0 || count != null) {
						response.setMessage("added successfully");
						response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					} else
						response.setMessage("unable to add");

				} else {
					logger.info("entered into inner else");
					response.setMessage("you are not adimin, please check your userName and PassWord");

				} // inner if-else

			} else {// Actually no need of writing this else
				logger.info("entered into inner else");
				// write proper msg in response object
				// for not authenticated user
				response.setMessage("Authentication Failed, please login again");
			} // outer if- else
		} catch (UserAuthenticationException e) {
			logger.info("Exception occured while Authentication of userId and token");
			response.setMessage("Unable to process");
		}

		return JsonUtil.convertJavaToJson(response);
	}

	public String editRoutes(String userRole, Long userId, String token,Integer travelId, String  jsonRoutes) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage(" Search Routes not succesful! Please try again");
		Routes routes = JsonUtil.convertJsonToJava(jsonRoutes, Routes.class);
		logger.info("Entered into searchRoutes Webservice :" + userRole + "" + userId + "" + token);
		if (userRole.equals(RolesConstants.ROLE_SUPER_ADMIN) && userId != null) {
			try {

				Integer count = routesDAO.editRoutes(routes);
				System.out.println("count" + count);

				if (count > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Your Edit Route Successfull");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("EditRoutes failure");
				}

			} catch (DataAccessException dae) {
				dae.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while geting Editroutes" + dae.getMessage());
				
			} catch (Exception e) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while get geting routes" + e.getMessage());
				e.printStackTrace();
			}
			jsonResponse = JsonUtil.convertJavaToJson(response);
			logger.info("Response from search route :: " + jsonResponse);

		} else if (userRole.equals(RolesConstants.ROLE_TRAVEL_ADMIN) && userId != null) {
			try {

				Integer count = routesDAO.editRoutes(travelId);
				System.out.println("count" + count);

				if (count > 0) {
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Your Edit Route Successfull");
				} else {
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("EditRoutes failure");
				}

			} catch (DataAccessException dae) {
				dae.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while geting Editroutes" + dae.getMessage());
			
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process! Please try again.");
				logger.error("Exception occured while get geting Editroutes" + e.getMessage());
			}
			jsonResponse = JsonUtil.convertJavaToJson(response);
			logger.info("Response from edit route :: " + jsonResponse);
		}
		return jsonResponse;
	}

	@Override
	public String getRoutes(String userRole, Long userId, String token, Integer routeId,Integer travelId) {
		String jsonResponse = "";
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
		response.setMessage("Your Registration is Failure!Please Try Again.");
		logger.info("Entered into getRouteMethod :: " + routeId);
		if (routeId != null) {
			try {// Handle Exceptions
					// getting user Old Data For Editing
				Routes routes = routesDAO.getRoute(routeId);
				
				if (routes != null) {
					
					jsonResponse = JsonUtil.convertJavaToJson(routes);
					response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
					response.setMessage("Plesase Update your Route");
					response.setData(jsonResponse);
				}
			} catch (DataAccessException de) {
				logger.error("Exception occured while retriving the  Data");
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("unable to process your request!please try again");
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Updating The Route ::" + e.getMessage());
			}

		}
		jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response From edit Route:" + jsonResponse);
		return jsonResponse;
	}

}
