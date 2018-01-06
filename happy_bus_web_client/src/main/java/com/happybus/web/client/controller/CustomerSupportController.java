package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.happybus.web.client.service.CustomerService;

@Controller
public class CustomerSupportController {

	@Autowired
	private CustomerService customerService;

	private static Logger logger = Logger.getLogger(CustomerSupportController.class);
	private static final String WEB_CUSTOMER_SUPORT_GET_QUERY = "customer_support_frame_get_query";
	private static final String WEB_CUSTOMER_SUPORT_DASHBOARD = "customerSupportDashboard";
	private static final String WEB_CUSTOMER_SUPORT_SUBMIT_QUERY = "customer_support_frame_submit_query";
	private static final String WEB_CUSTOMER_SUPORT_SHOW_QUERY = "customer_support_show_query";

	@RequestMapping(value = "customerSupportDashboard", method = RequestMethod.GET)
	public String showcustomerSupportDashboardPage() {
		return WEB_CUSTOMER_SUPORT_DASHBOARD;
	}

	@RequestMapping(value = "getQuery", method = RequestMethod.GET)
	public String getQueries() {
		logger.info("entered into showCustomerSupportGetQuery()geT");
		return WEB_CUSTOMER_SUPORT_GET_QUERY;
	}

	@ResponseBody
	@RequestMapping(value = "getQuery1", method = RequestMethod.POST)
	public String getQueries(HttpServletRequest req) {
		logger.info("entered into showCustomerSupportGetQuery()Post");
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			System.out.println(req.getSession(false).getAttribute("userId") != null);
			System.out.println(req.getSession(false).getAttribute("token"));
			jsonResponse = customerService.getQueries(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		}
		logger.info(jsonResponse);
		return jsonResponse;
	}

	/*
	 * @RequestMapping(value = "submitQuery", method = RequestMethod.GET) public
	 * String submitQueries() {
	 * logger.info("entered into showSubmitQuery()geT"); return
	 * WEB_CUSTOMER_SUPORT_SUBMIT_QUERY ; }
	 */

	/**
	 * the method used to get submit query form
	 * 
	 * @param
	 * @return happy_forgot_password
	 * @author nagaraj.e and srikanth.g
	 */
	@RequestMapping(value = "submitQuery", method = RequestMethod.GET)
	public ModelAndView submitQuery(HttpServletRequest request) {
		logger.info("entered into submitQuery get");
		ModelAndView modelAndView = new ModelAndView("customer_support_frame_submit_query");
	/*	Response response = customerService.getIssueType(request.getSession(false).getAttribute("userId"),
				request.getSession(false).getAttribute("token"));
		System.out.println(response);
		if (response != null && response.getStatus().equals(StatusUtil.HAPPY_STATUS_SUCCESS)) {
			System.out.println("entered into if of submit query get");
			System.out.println(response.getData());
			List<IssueType> listObj=new ArrayList<>();
		listObj =JsonUtil.convertJsonToJava(response.getData(),ArrayList.class);	
			logger.info(listObj);
			String response1=JsonUtil.convertJavaListToJson(listObj);
			
			request.setAttribute("issueType",response1);
		
			request.setAttribute("userId", request.getSession(false).getAttribute("userId"));
			request.setAttribute("IssueTypeId", issue.getIssueTypeId());
			request.setAttribute("IssueTypeName", issue.getIssueTypeName());
			}*/
		return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "getIssueType", method = RequestMethod.POST)
	public String submitQueries1(HttpServletRequest req) {
		logger.info("entered into submitQueries1 post");
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			System.out.println(req.getSession(false).getAttribute("userId") != null);
			System.out.println(req.getSession(false).getAttribute("token"));
			jsonResponse = customerService.getIssueType(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		}
		else{
			System.out.println("entered into  else of client");
		}
		logger.info(jsonResponse);
		return jsonResponse;
	}
	@ResponseBody
	@RequestMapping(value = "submitQuery", method = RequestMethod.POST)
	public String submitQueries(HttpServletRequest req,@RequestParam("issueType") Integer issueType,
			@RequestParam("description") String description) {
		logger.info("entered into submit CustomerSupportsubmitQuery()Post"+issueType+""+description);
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			System.out.println(req.getSession(false).getAttribute("userId") != null);
			System.out.println(req.getSession(false).getAttribute("token"));
			jsonResponse = customerService.saveQueries(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"), issueType,description);

		}
		logger.info(jsonResponse);
		return jsonResponse;
	}

	

	

	/**
	 * the method used to show getProfileForm
	 * 
	 * @param
	 * @return happy_forgot_password
	 * @author nagaraj.e and srikanth.g
	 */

	@RequestMapping(value = "showQuery", method = RequestMethod.GET)
	public ModelAndView showQueries(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView("customer_support_show_query");
		/*Response response = customerService.showQuery(request.getSession(false).getAttribute("userId"),
				request.getSession(false).getAttribute("token"));
		logger.info("Entered into showQueries");
		if (response != null && response.getStatus().equals(StatusUtil.HAPPY_STATUS_SUCCESS)) {
			
			
			List<CustomerSupport> listCustomerQueryData=new ArrayList<>();
			listCustomerQueryData =JsonUtil.convertJsonToJava(response.getData(),ArrayList.class);	
				logger.info(listCustomerQueryData);
				String response1=JsonUtil.convertJavaListToJson(listCustomerQueryData);
				
				request.setAttribute("CustomerQuerydata",response1);
		
			
			System.out.println("Entered into if");
			request.setAttribute("userId", request.getSession(false).getAttribute("userId"));
			CustomerSupport customerSupport = JsonUtil.convertJsonToJava(response.getData(), CustomerSupport.class);
			;
			request.setAttribute("queryId", customerSupport.getQueryId());
			request.setAttribute("query", customerSupport.getQuery());
			request.setAttribute("querySolution", customerSupport.getQuerySolution());

		}
		System.out.println("Entered");	
		*/
		return modelAndView;
	}
	@ResponseBody
	@RequestMapping(value = "showQuery", method = RequestMethod.POST)
	public String showQueries1(HttpServletRequest req) {
		logger.info("entered into showCustomerSupportshowQuery()Post");
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {
			System.out.println(req.getSession(false).getAttribute("userId") != null);
			System.out.println(req.getSession(false).getAttribute("token"));
			jsonResponse = customerService.showQuery(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"));
		}
		else{
			System.out.println("entered into  else of client");
		}
		logger.info(jsonResponse);
		return jsonResponse;
	}

	
	

	
	

	/*
	 * @RequestParam("solutionToQuery") String
	 * solutionToQuery,@RequestParam("queryId") Object queryId
	 * 
	 * @ModelAttribute CustomerSupport customerSupport solutionToQuery,queryId
	 */
	@ResponseBody
	@RequestMapping(value = "updateQueryDetails", method = RequestMethod.POST)
	public String updateQueryDetails(HttpServletRequest req, @RequestParam("customerUserId") Object customerId,
			@RequestParam("solutionToQuery") String solutionToQuery, @RequestParam("queryId") Integer queryId) {
		logger.info("entered into showCustomerSupportsubmitQuery()Post");
		String jsonResponse = "";
		if (req.getSession(false) != null && req.getSession(false).getAttribute("userId") != null) {

			System.out.println(req.getSession(false).getAttribute("userId") != null);
			System.out.println(req.getSession(false).getAttribute("token"));

			jsonResponse = customerService.solutionToQueries(req.getSession(false).getAttribute("userId"),
					req.getSession(false).getAttribute("token"), customerId, solutionToQuery, queryId);

		}
		logger.info(jsonResponse);
		return jsonResponse;
	}

}
