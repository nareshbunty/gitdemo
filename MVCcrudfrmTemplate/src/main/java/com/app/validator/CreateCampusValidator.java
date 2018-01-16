package com.app.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.dto.TblCampusTo;
import com.app.model.TblCampus;
import com.app.service.ICampusService;



@Component
public class CreateCampusValidator implements Validator {
    @Autowired
    ICampusService campusService;

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return TblCampusTo.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        TblCampusTo tblCampusTo = (TblCampusTo) target;
        TblCampus byCampusCode = campusService.getByCampusCode(tblCampusTo.getCampusCode());
        if (StringUtils.isEmpty(tblCampusTo.getCampusCode()) || StringUtils.isBlank(tblCampusTo.getCampusCode())) {
            errors.rejectValue("campusCode", "required.campusCode");
        }
        else if (byCampusCode != null) {
            errors.rejectValue("campusCode", "required.campusCodeExist");
        }

        if (StringUtils.isEmpty(tblCampusTo.getCampusName()) || StringUtils.isBlank(tblCampusTo.getCampusName())) {
            errors.rejectValue("campusName", "required.campusName");
        }
    }

}