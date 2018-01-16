package com.app.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.app.dto.TblCampusTo;
import com.app.service.ICampusService;

@Component
public class EditCampusValidator implements Validator {
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
        boolean campusExist = campusService.isCampusExist(tblCampusTo.getCampusCode(), tblCampusTo.getId());
        if (StringUtils.isEmpty(tblCampusTo.getCampusCode()) || StringUtils.isBlank(tblCampusTo.getCampusCode())) {
            errors.rejectValue("campusCode", "required.campusCode");
        }
        else if (campusExist == true) {
            errors.rejectValue("campusCode", "required.campusCodeExist");
        }

        if (StringUtils.isEmpty(tblCampusTo.getCampusName()) || StringUtils.isBlank(tblCampusTo.getCampusName())) {
            errors.rejectValue("campusName", "required.campusName");
        }
    }

}