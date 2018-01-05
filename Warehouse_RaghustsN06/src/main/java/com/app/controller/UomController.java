package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.model.Uom;
import com.app.service.IUomService;
import com.app.spec.UomSpecification;
import com.app.util.UomUtil;
import com.app.validator.UomValidator;

@Controller
public class UomController {

	@Autowired
	private IUomService service;
	@Autowired
	private UomUtil uomUtil;
	@Autowired
	UomValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder){
		
	}
	
	
	
	/**
	 * 1. Show Uom Register JSP
	 * on enter URL 'regUom' in
	 * Browser
	 */
	@GetMapping(value={"/regUom","/"})  
	public String showRegPage(ModelMap map){
		map.addAttribute("uom",new Uom());
		map.addAttribute("uomTypes",uomUtil.getUomTypes());
		return "UomRegister";
	}

	/**
	 * 2. On submit UomRegister JSP
	 * Form, Insert Data into DB.
	 * 
	 */
	@PostMapping("/insertUom")
	public String saveUom(@ModelAttribute Uom uom,BindingResult errors,ModelMap map){
		validator.validate(uom, errors);
		if(errors.hasErrors()){
			map.addAttribute("uom",uom);
		}else{
			long uomId=service.save(uom);
			map.addAttribute("message", "UOM ("+uomId+") Created ");
			map.addAttribute("uom",new Uom());
		}
		map.addAttribute("uomTypes",uomUtil.getUomTypes());
		return "UomRegister";
	}

	/**
	 * 3. Data Fetch From DB to UI
	 */
	@GetMapping("/getAllUoms")
	public String getUoms(@PageableDefault(size=5,sort="uomId",direction=Direction.DESC) Pageable pageable,
			@ModelAttribute Uom uom,ModelMap map){
		Specification<Uom> spec=new UomSpecification(uom);
		Page<Uom> uomPage=service.getAll(spec,pageable);
		List<Uom> uomList=uomPage.getContent();
		uomUtil.replaceWitValues(uomList);
		map.addAttribute("uoms", uomList);
		map.addAttribute("uomPage", uomPage);
		return "UomData";
	}
	/**
	 * 4. On click delete read Req Param
	 * and delete record using service layer
	 */
	@GetMapping("/deleteUom")
	public String deleteUomById(@RequestParam("uomId")int uomId){
		service.deleteById(uomId);
		return "redirect:getAllUoms";
	}

	/**
	 * 5. Show Edit Page..
	 */
	@GetMapping("/editUom")
	public String showEdit(@RequestParam("uomId")int uomId,ModelMap map){
		Uom ob=service.getOneById(uomId);
		map.addAttribute("uom", ob);
		map.addAttribute("uomTypes",uomUtil.getUomTypes());
		return "UomDataEdit";
	}

	/**
	 * 6. Update Data On click Update Button
	 */
	@PostMapping("/updateUom")
	public String updateUom(@ModelAttribute Uom uom,BindingResult errors,ModelMap map){
		validator.validate(uom, errors);
		String page = null;
		if(errors.hasErrors()){
			map.addAttribute("uom",uom);
			map.addAttribute("uomTypes",uomUtil.getUomTypes());
			page = "UomDataEdit";
		}else{
			service.update(uom);
			page ="redirect:getAllUoms";
		}
		return page;
	}

}
