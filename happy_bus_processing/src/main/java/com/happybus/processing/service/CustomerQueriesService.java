package com.happybus.processing.service;

public interface CustomerQueriesService {
	

	
	public String registerCustomerSupport(String jsonCustomerSupport,Long userId,String token);
	
	public String getQueries(Long userId,String token);

	public String saveQueries(String jsonQuery, Object userId);
	
	public String showQueries(Long userId,String token);

	public String updateSolutionToQueries(String jsonCustomerSupport,Object customerId,Long userId,String token);

	public String getIssueType(Long userId, String token);  /*,String solutionToQuery,Object queryId*/

}
 