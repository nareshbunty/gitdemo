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

import com.app.model.ShipmentType;
import com.app.service.IShipmentTypeService;
import com.app.util.ShipmentTypeUtil;
import com.app.validator.ShipmentTypeValidator;

@Controller
public class ShipmentTypeController {
	
	@Autowired
	private IShipmentTypeService service;
	@Autowired
	private ShipmentTypeValidator validator;
	@Autowired
	private ShipmentTypeUtil util;
	
	/**
	 * 1. Show ShipmentTypeRegister Page
	 * @param map
	 * @return
	 */
	@GetMapping(value={"/regShipmentType","/insertShipmentType","/updateShipmentType"})
	public String showRegPage(ModelMap map){
		map.addAttribute("shipmentType", new ShipmentType());
		map.addAttribute("shipmentModes", util.getShipmentModes());
		map.addAttribute("shipmentGrades", util.getShipmentGrades());
		return "ShipmentTypeRegister";
	}
	
	/**
	 * 2. Save ShipmentType 
	 * @param shipmentType
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/insertShipmentType")
	public String save(@ModelAttribute ShipmentType shipmentType,BindingResult errors,ModelMap map){
		validator.validate(shipmentType, errors);
		if(errors.hasErrors()){
			map.addAttribute("shipmentType", shipmentType);
		}else{
			long shipmentTypeId=service.save(shipmentType);
			map.addAttribute("message", "ShipmentType '"+shipmentTypeId+"' created");
			map.addAttribute("shipmentType", new ShipmentType());
		}
		map.addAttribute("shipmentModes", util.getShipmentModes());
		map.addAttribute("shipmentGrades", util.getShipmentGrades());
		return "ShipmentTypeRegister";
	}
	
	/**
	 * 3. GetAll ShipmentTypes
	 * @param map
	 * @return
	 */
	@GetMapping("/getAllShipmentTypes")
	public String getAllRecords(ModelMap map){
		List<ShipmentType> list=service.getAll();
		map.addAttribute("shipmentTypeList", list);
		return "ShipmentTypeData";
	}
	
	/**
	 * 4. Delete shipmentType Based on Id
	 * @param shipmentTypeId
	 * @return
	 */
	@GetMapping("/deleteShipmentType")
	public String deleteRecord(@RequestParam long shipmentTypeId){
		service.deleteById(shipmentTypeId);
		return "redirect:getAllShipmentTypes";
	}
	
	/**
	 * 5. Show Edit Page for shipmentType
	 * @param shipmentTypeId
	 * @param map
	 * @return
	 */
	@GetMapping("/editShipmentType")
	public String showEditPage(@RequestParam long shipmentTypeId,ModelMap map){
		ShipmentType s=service.getOneById(shipmentTypeId);
		map.addAttribute("shipmentType", s);
		map.addAttribute("shipmentModes", util.getShipmentModes());
		map.addAttribute("shipmentGrades", util.getShipmentGrades());
		return "ShipmentTypeDataEdit";
	}
	
	
	/**
	 * 6. Update shipmentType Details...
	 * @param shipmentType
	 * @param errors
	 * @param map
	 * @return
	 */
	@PostMapping("/updateShipmentType")
	public String updateRecord(@ModelAttribute ShipmentType shipmentType,BindingResult errors,ModelMap map){
		String page=null;
		validator.validate(shipmentType, errors);
		if(errors.hasErrors()){
			map.addAttribute("shipmentType", shipmentType);
			map.addAttribute("shipmentModes", util.getShipmentModes());
			map.addAttribute("shipmentGrades", util.getShipmentGrades());
			page="ShipmentTypeDataEdit";
		}else{
			service.update(shipmentType);
			page="redirect:getAllShipmentTypes";
		}
		return page;
	}
}