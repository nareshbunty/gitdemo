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

import com.app.model.WhUserType;
import com.app.service.IWhUserTypeService;
import com.app.util.WhUserTypeUtil;
import com.app.validator.WhUserTypeValidator;

@Controller
public class WhUserTypeController {
	
	@Autowired
	private IWhUserTypeService service;
	@Autowired
	private WhUserTypeValidator validator;
	@Autowired
	private WhUserTypeUtil util;

	/**
	 * 1. Show Register Page
	 * @param map
	 * @return
	 */
	@GetMapping("/regWhUserType")
	public String showPage(ModelMap map){
		map.addAttribute("whUserType", new WhUserType());
		util.getDynamics(map);
		return "WhUserTypeRegister";
	}
	
	/**
	 * 2. Save WhUserType
	 * @param whUserType
	 * @param errors
	 * @param map
	 * @return
	 */

	@PostMapping("/insertWhUserType")
	public String save(@ModelAttribute WhUserType whUserType,BindingResult errors,ModelMap map){
		validator.validate(whUserType, errors);
		System.out.println(errors);
		if(errors.hasErrors()){
			map.addAttribute("whUserType", whUserType);
		}else{
			long id=service.save(whUserType);
			map.addAttribute("message", "WhUserType '"+id+"' Saved");
			map.addAttribute("whUserType", new WhUserType());
		}
		util.getDynamics(map);
		return "WhUserTypeRegister";
	}
	
	/**
	 * 3. Get All WhUserTypes
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllWhUserTypes")
	public String getAll(ModelMap map){
		List<WhUserType> list=service.getAll();
		map.addAttribute("whUserTypesList", list);
		return "WhUserTypeData";
	}
	
	/**
	 * 4. Delete WhUserType by Id
	 * @param whUserTypeId
	 * @return
	 */
	@GetMapping("/deleteWhUserType")
	public String delete(@RequestParam long whUserTypeId){
		service.deleteById(whUserTypeId);
		return "redirect:getAllWhUserTypes";
	}
	
	/**
	 * 5. Show Edit Page
	 * @param whUserTypeId
	 * @param map
	 * @return
	 */
	@GetMapping("/editWhUserType")
	public String edit(@RequestParam long whUserTypeId,ModelMap map){
		map.addAttribute("whUserType", service.getOneById(whUserTypeId));
		util.getDynamics(map);
		return "WhUserTypeDataEdit";
	}
	
	/**
	 * 6. Update WhUserType
	 * @param whUserType
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updateWhUserType")
	public String update(@ModelAttribute WhUserType whUserType,BindingResult errors,ModelMap map){
		validator.validate(whUserType, errors);
		String page=null;
		if(errors.hasErrors()){
			map.addAttribute("whUserType", whUserType);
			util.getDynamics(map);
			page="WhUserTypeDataEdit";
		}else{
			service.update(whUserType);
			map.addAttribute("whUserType", new WhUserType());
			page="redirect:getAllWhUserTypes";
		}
		return page;
	}
	
}