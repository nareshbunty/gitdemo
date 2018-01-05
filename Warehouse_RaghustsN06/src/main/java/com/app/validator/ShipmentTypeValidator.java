package com.app.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.model.ShipmentType;
import com.app.util.ShipmentTypeUtil;

@Component
public class ShipmentTypeValidator implements Validator {
	@Autowired
	private ShipmentTypeUtil util;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ShipmentType.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "","Enter Description..");
		
		ShipmentType shipmentType=(ShipmentType) target;
		if(!util.getShipmentModes().contains(shipmentType.getShipmentMode())){
			errors.rejectValue("shipmentMode", "", "ShipmentMode must be one of:"+util.getShipmentModes());
		}
		if(!Pattern.compile("[A-Z]{4,8}").matcher(shipmentType.getShipmentCode()).matches()){
			errors.rejectValue("shipmentCode", "", "Enter 4-8 upper case Letters Code");
		}
		if(shipmentType.getEnabled()!=null && !"YES".equals(shipmentType.getEnabled())){
			errors.rejectValue("enabled", "", "Enable must be YES or No Input");
		}
		if(shipmentType.getShipmentGrade()==null){
			errors.rejectValue("shipmentGrade", "", "Select any one Shipment Grade");
		}else if(!util.getShipmentGrades().contains(shipmentType.getShipmentGrade())){
			errors.rejectValue("shipmentGrade", "", "Shipment Grade must be one of:"+util.getShipmentGrades());
		}
	}
}
