package com.app.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.model.WhUserType;
import com.app.util.WhUserTypeUtil;

@Component
public class WhUserTypeValidator implements Validator{

	@Autowired
	private WhUserTypeUtil util;

	@Override
	public boolean supports(Class<?> clazz) {
		return WhUserType.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "whUserIdNumber", "", "Enter ID Number..");
		
		WhUserType whu=(WhUserType) target;

		if(whu.getUserType()==null){
			errors.rejectValue("userType", "", "Please choose one User Type");
		}else if(!util.getUserTypes().contains(whu.getUserType())){
			errors.rejectValue("userType", "", "User Type must be from:"+util.getUserTypes());
		}
		if(!Pattern.compile("[A-Z]{4,8}").matcher(whu.getWhUserCode()).matches()){
			errors.rejectValue("whUserCode", "", "User Code must 4-8 Uppercase caracters");
		}
		if(!Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", 
				Pattern.CASE_INSENSITIVE).matcher(whu.getWhUserEmail()).matches()){
			errors.rejectValue("whUserEmail", "", "Invalid Email Format");
		}
		if(!Pattern.compile("[0-9]{10}").matcher(whu.getWhUserContact()).matches()){
			errors.rejectValue("whUserContact", "", "It must be 10 Digits");
		}
		if(!util.getWhUserIdTypes().contains(whu.getWhUserIdType())){
			errors.rejectValue("whUserIdType", "", "Choose one ID type");
		}else if("OTHER".equalsIgnoreCase(whu.getWhUserIdType())	&&	(
				whu.getWhUserIdTypeOther()==null	|| "".equals(whu.getWhUserIdTypeOther().trim()) 
				)){
			errors.rejectValue("whUserIdTypeOther", "", "Provide Other ID Type Details");
		}
		
	}

}
