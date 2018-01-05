package com.app.provider;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Uom;
import com.app.service.IUomService;
import com.app.validator.UomValidator;

@RestController
public class UomRestController {
	
	@Autowired
	private IUomService service;
	
	@Autowired
	private UomValidator validator;

	
	@PostMapping("/rest/saveUom")
	public ResponseEntity<?> saveUom(@RequestBody Uom uom,BindingResult errors){
		validator.validate(uom, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors.getAllErrors());
		}else{
			long uomId=service.save(uom);
			return ResponseEntity.ok("Data is saved in App with id:"+uomId);
		}
	}

	@PostMapping("/rest/updateUom")
	public ResponseEntity<?> updateUom(@RequestBody Uom uom,BindingResult errors){
		validator.validate(uom, errors);
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors.getAllErrors());
		}else if(uom.getUomId()<=0){
			return ResponseEntity.badRequest().body("Uom Id must be Positive number");
		}else{
			service.update(uom);
			return ResponseEntity.ok("Data is updated in App");
		}
	}

	@GetMapping("/rest/deleteUom/{uomId}")
	public ResponseEntity<?> deleteUom(@PathVariable long uomId){
		String message=null;
		if(uomId<=0)
			return ResponseEntity.badRequest().body("Uom Id must be Positive number");
		try {
			service.deleteById(uomId);
			message="UOM ("+uomId+") is deleted";
		} catch (EmptyResultDataAccessException e) {
			message="Record Not Exist";
		}
		return ResponseEntity.ok(message);
	}

	@GetMapping("/rest/allUoms")
	public ResponseEntity<?> getAllUoms(){
		List<Uom> uomsList=service.getAll();
		if(uomsList!=null && uomsList.size()>0)
			return ResponseEntity.ok(uomsList);
		else
			return ResponseEntity.ok("No Data Found");
	}
	
	@GetMapping("/rest/getUom/{uomId}")
	public ResponseEntity<?> getUomById(@PathVariable long uomId){
		Uom uom=null;
		try {
			uom=service.getOneById(uomId);
			if(uom!=null && uomId==uom.getUomId())
				return ResponseEntity.ok(uom);
			else
				return ResponseEntity.ok("Uom '"+uomId+"' Not Found");
		} catch (EntityNotFoundException e) {
			return ResponseEntity.ok("Uom '"+uomId+"' Not Found");
		}
	}

}
