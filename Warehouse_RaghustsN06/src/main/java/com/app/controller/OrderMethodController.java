package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.model.OrderMethod;
import com.app.service.IOrderMethodService;
import com.app.util.OrderMethodUtil;
import com.app.validator.OrderMethodValidator;

@Controller
public class OrderMethodController {
	@Autowired
	private IOrderMethodService service;
	@Autowired
	private OrderMethodValidator validator;
	@Autowired
	private OrderMethodUtil util;

	/**
	 * 1. Show Register Page
	 * @param map
	 * @return
	 */
	@GetMapping("/regOrderMethod")
	public String showPage(ModelMap map){
		map.addAttribute("orderMethod", new OrderMethod());
		util.getDynamics(map);
		return "OrderMethodRegister";
	}
	
	/**
	 * 2. Save OrderMethod
	 * @param orderMethod
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/insertOrderMethod")
	public String save(@ModelAttribute OrderMethod orderMethod,BindingResult errors,ModelMap map){
		validator.validate(orderMethod, errors);
		if(errors.hasErrors()){
			map.addAttribute("orderMethod", orderMethod);
		}else{
			long id=service.save(orderMethod);
			map.addAttribute("message", "OrderMethod '"+id+"' Saved");
			map.addAttribute("orderMethod", new OrderMethod());
		}
		util.getDynamics(map);
		return "OrderMethodRegister";
	}
	
	/**
	 * 3. Get All OrderMethods
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllOrderMethods")
	public String getAll(ModelMap map){
		List<OrderMethod> list=service.getAll();
		map.addAttribute("orderMethodsList", list);
		return "OrderMethodData";
	}
	
	/**
	 * 4. Delete OrderMethod by Id
	 * @param orderMethodId
	 * @return
	 */
	@GetMapping("/deleteOrderMethod")
	public String delete(@RequestParam long orderMethodId){
		service.deleteById(orderMethodId);
		return "redirect:getAllOrderMethods";
	}
	
	/**
	 * 5. Show Edit Page
	 * @param orderMethodId
	 * @param map
	 * @return
	 */
	@GetMapping("/editOrderMethod")
	public String edit(@RequestParam long orderMethodId,ModelMap map){
		map.addAttribute("orderMethod", service.getOneById(orderMethodId));
		util.getDynamics(map);
		return "OrderMethodDataEdit";
	}
	
	/**
	 * 6. Update OrderMethod
	 * @param orderMethod
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updateOrderMethod")
	public String update(@ModelAttribute OrderMethod orderMethod,BindingResult errors,ModelMap map){
		validator.validate(orderMethod, errors);
		String page=null;
		if(errors.hasErrors()){
			map.addAttribute("orderMethod", orderMethod);
			page="OrderMethodDataEdit";
			util.getDynamics(map);
		}else{
			service.update(orderMethod);
			map.addAttribute("orderMethod", new OrderMethod());
			page="redirect:getAllOrderMethods";
		}
		return page;
	}
	
}
