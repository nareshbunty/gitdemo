package com.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.model.PurchaseOrder;

@Component
public class PurchaseOrderValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PurchaseOrder.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
	}
	

}
