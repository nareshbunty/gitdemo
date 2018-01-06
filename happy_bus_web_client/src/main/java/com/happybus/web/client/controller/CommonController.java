package com.happybus.web.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {
  @RequestMapping(value="/")
	public ModelAndView showHomePage(){
		String targetViewName="happy_home";
		return new ModelAndView(targetViewName);
	}
  
}
