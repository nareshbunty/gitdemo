package com.cba.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.cba.processing.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String home(){
		return "loginForm";
	}
	@RequestMapping(value="/error",method=RequestMethod.GET)
	public ModelAndView error(){
	return new ModelAndView("loginForm","status","Internal Server Problem");
	}
  @RequestMapping(value="login",method=RequestMethod.GET)
	public ModelAndView
	showLoginpage(@RequestParam(required=false,name="error")  String error){
	 ModelAndView modelAndView=new ModelAndView();
	     modelAndView.setViewName("loginForm");
	     if(error!=null && error.equals("Invalid")){
	   modelAndView.addObject("status","Invalid User Name (OR) Password"); 	 
	     }
	     else if(error!=null && error.equals("Session_EXPIRED")){
	   modelAndView.addObject("status","Your Session Expired!Please Login"); 	 
	     }
	     
	     return modelAndView;
	}
  @RequestMapping(value="dashboard",method=RequestMethod.GET)
  public ModelAndView dashboard(HttpServletRequest req){
	 try{
	  User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   String userName=user.getUsername();
	   HttpSession session=req.getSession(false);
	   if(session!=null){
	  session.setAttribute("userName",userName);
	 //get employeeId based on userName condition,after that store that employeeId in session
	  Integer employeeId=userService.getEmployeeId(userName);  
	  session.setAttribute("employeeId",employeeId);
	  Collection<GrantedAuthority> authorities =user.getAuthorities();
	       Iterator<GrantedAuthority> itr=authorities.iterator();
	       while(itr.hasNext()){
	       GrantedAuthority authority=itr.next();	   
	        String userRole=authority.getAuthority();
	         session.setAttribute("userRole",userRole); 
	       }
		return new ModelAndView("dashboard"); 
	   }
	   else{
		return new ModelAndView("loginForm","status","Session Problem!Please Login Once Again.");   
	   }
	 }catch(Exception e){
	return new ModelAndView(new RedirectView("error"));	 
	 }
  }
  @RequestMapping(value="logout",method=RequestMethod.GET)
  public ModelAndView logout(){
	  String status="You Are LoggedOut Successfully";
	return new ModelAndView("loginForm","status",status);  
  }
}
