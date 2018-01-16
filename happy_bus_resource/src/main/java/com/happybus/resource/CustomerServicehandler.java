package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.CustomerQueriesService;

@RestController
@RequestMapping("support")
public class CustomerServicehandler {
	@Autowired
	private CustomerQueriesService customerQueriesService;
	
	/**
	 * the method is a resource method for submitting queries
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 */
	@RequestMapping(value = "submitQuery", method = RequestMethod.POST)
	@ResponseBody
	public String submitQuery(@RequestBody String jsonQuery, @RequestParam("userId") Object userId){
	
	  	return customerQueriesService.saveQueries(jsonQuery, userId);


	}
	
	
	/**
	 * the method is a resource method for registering customersupport
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 */
	@RequestMapping(value = "addCustomerSupport", method = RequestMethod.POST)
	@ResponseBody
	public String registerCustomerSupport(@RequestBody String jsonUser,@RequestParam("userId")Long userId,@RequestParam("token")String token) {

		return customerQueriesService.registerCustomerSupport(jsonUser, userId,token); 
	}
	
	/**
	 * the method is a resource method for registering customersupport
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 */
	
	
	
	
	@RequestMapping(value = "getQuery", method = RequestMethod.GET)
	@ResponseBody
	public String getQuery(@RequestParam("userId")Long userId,@RequestParam("token")String token) {

		return customerQueriesService.getQueries(userId, token);
				
	}
	
	
	
	
	@RequestMapping(value="getIssueType",method=RequestMethod.GET)
	@ResponseBody
	public String getAmenities(@RequestParam("userId")Long userId, @RequestParam("token")String token ){
		return customerQueriesService.getIssueType(userId, token);/**/
	}
	
	/**
	 * the method is a resource method for registering customersupport
	 * 
	 * @param userId
	 * @return jsonResponse
	 */
	
	
	
	
	@RequestMapping(value = "showQueries", method = RequestMethod.GET)
	@ResponseBody
	public String showQuery(@RequestParam("userId") Long userId, @RequestParam("token")String token) {

		return customerQueriesService.showQueries(userId,token);
	}
	
	/**
	 * the method is a resource method for registering customersupport
	 * 
	 * @param userId
	 * @return jsonResponse
	 */
	
	
	
	
	@RequestMapping(value = "solutionToQuery", method = RequestMethod.POST)
	@ResponseBody
	public String solutionToQuery(@RequestBody String jsonCustomerSupport,@RequestParam("customerId") Long customerId,@RequestParam("userId")Long userId, @RequestParam("token")String token ) {

		return customerQueriesService.updateSolutionToQueries(jsonCustomerSupport,customerId,userId,token);
	}
	
	/*,@RequestParam String solutionToQuery,@RequestParam Object queryId*/
}
