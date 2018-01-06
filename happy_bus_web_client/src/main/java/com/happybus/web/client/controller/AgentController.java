package com.happybus.web.client.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.beans.Agent;
import com.happybus.beans.Bank_Details;
import com.happybus.beans.Response;
import com.happybus.beans.User;
import com.happybus.util.RolesConstants;
import com.happybus.web.client.service.AgentService;
@Controller
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	/*private static final String WEB_AGENT_REGISTRATION="happy_register_agent";*/
	
	@RequestMapping(value="addAgent",method=RequestMethod.GET)
	public String showAgentRegistrationPage(){
		return "happy_register_agent";

	}
	 
	  @ResponseBody
	  @RequestMapping(value="addAgent1",method=RequestMethod.POST)
	  public String agentRegistration(
			 HttpServletRequest req,
			 @ModelAttribute Agent agent,
			 @ModelAttribute Bank_Details bank_Details,
			 @ModelAttribute User user){
		  String jsonResponse="";
		  if(req.getSession(false)!=null && req.getSession(false).getAttribute("userId")!=null){
		agent.setBankDetails(bank_Details);
		 agent.setCreatedBy((Long)req.getSession(false).getAttribute("userId"));
		   
		user.setAddress(agent.getAgentAddress());
	    user.setEmail(user.getEmail());
	    user.setUserName(user.getEmail());
	    user.setUserRole(RolesConstants.ROLE_AGENT);
	    user.setCreatedBy((Long)req.getSession(false).getAttribute("userId"));
	    user.setStatus((byte)1); 
	    agent.setStatus((byte)1);
	    agent.setUser(user);
	    jsonResponse=agentService.registerAgent(agent,req.getSession(false).getAttribute("userId"),req.getSession(false).getAttribute("token"));
		  }else{
			  
		  }
		return jsonResponse;

}
	
	  @RequestMapping(value="searchAgent",method=RequestMethod.GET)	
	  public String showAgentSearchPage(){
		  return "happy_search_agent";
	  }
	  
	  @RequestMapping(value="searchAgent1",method=RequestMethod.GET)
	  @ResponseBody
	   public Response searchAgent(HttpServletRequest req,@RequestParam("agentId") Long agentId,@RequestParam("phoneNumber1") String phoneNumber1){
		
		Response response=agentService.searchAgent(agentId,phoneNumber1,req.getSession(false).getAttribute("userId"),req.getSession(false).getAttribute("token"),req.getSession(false).getAttribute("userRole"));
		return response;	
		}  
	  
	  
	  @RequestMapping(value="updateAgent",method=RequestMethod.POST)
		@ResponseBody
		public String updateAgent(
				@ModelAttribute Agent agent,
				 @ModelAttribute Bank_Details bank_Details,
				 @ModelAttribute User user){
			
		agent.setBankDetails(bank_Details);
		agent.setUser(user);
		String response=agentService.updateAgent(agent);
			return response;	
			} 
}