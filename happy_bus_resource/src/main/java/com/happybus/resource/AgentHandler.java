package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.AgentService;

/**
 * This class is acting as Resource class . It accessed over the N.w
 * 
 * @author Anil ch
 */
@RestController
@RequestMapping(value="agent")
public class AgentHandler {
	@Autowired
	private AgentService agentService;
	
	/**
	 * the method is a resource method for registerAgent
	 * 
	 * @param jsonAgent
	 * @return jsonResponse
	 */
	@RequestMapping(value="addAgent",method=RequestMethod.POST)
	@ResponseBody
	
	public String registerAgent(@RequestBody String jsonAgent,@RequestParam Long userId,@RequestParam Object token){
		return agentService.registerAgent(jsonAgent, userId, token);
		
	}
	  
	  
	/**
	 * the method is a resource method for searchAgents
	 * 
	 * @param jsonAgent
	 * @return jsonResponse
	 */
	  @RequestMapping(value="searchAgent",method=RequestMethod.GET)
	  @ResponseBody
	  public String searchAgents(@RequestParam("agentId") Long agentId,
			                     @RequestParam("phoneNumber1")String phoneNumber1,
			                     @RequestParam("userId") Long userId,
			                     @RequestParam("token") String token,
			                     @RequestParam("userRole") String userRole
			                     ){
		  return agentService.searchAgents(agentId,phoneNumber1,userId,token,userRole);
	  }
	  
	  /**
	   * the method is a resource method for updateAgent
	   * 
	   * @param jsonAgent
	   * @return jsonResponse
	   */
	  @RequestMapping(value="updateAgent",method=RequestMethod.POST)
	  @ResponseBody
	  public String updateAgent(@RequestBody String jsonAgent){
		return agentService.updateAgents(jsonAgent);  
	  }
		
}
