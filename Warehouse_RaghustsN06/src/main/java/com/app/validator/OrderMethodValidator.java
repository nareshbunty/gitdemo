package com.app.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.model.OrderMethod;
import com.app.util.OrderMethodUtil;

@Component
public class OrderMethodValidator implements Validator{
	@Autowired
	private OrderMethodUtil util;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return OrderMethod.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "", "Please Enter Description");
		OrderMethod om=(OrderMethod) target;
		
		
		if(!Pattern.compile("[A-Z]{4,8}").matcher(om.getOrderCode()).matches()){
			errors.rejectValue("orderCode", "", "Enter 4-8 Upper case letters");
		}
		if(!util.getOrderMethods().contains(om.getOrderMetd())){
			errors.rejectValue("orderMetd", "", "Order Method must be on of :"+util.getOrderMethods());
		}
		if(om.getOrderMode()==null || !util.getOrderModes().contains(om.getOrderMode())){
			errors.rejectValue("orderMode", "", "Select One Order Mode from :"+util.getOrderModes());
		}
		
		
		
		
		
		
	}

}
