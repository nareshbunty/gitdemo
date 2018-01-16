package com.app.mapper;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.app.common.Constants;
import com.app.dto.TblCampusTo;
import com.app.model.TblCampus;

@Component
public class TblCampusToToTblCampusMapper {
    public void map(final TblCampusTo tblCampusTo, final TblCampus tblCampus) {
        tblCampus.setCampusCode(tblCampusTo.getCampusCode());
        tblCampus.setCampusName(tblCampusTo.getCampusName());
        tblCampus.setDeletionIndicator(Constants.N);
        tblCampus.setDateCreated(new Date());
        tblCampus.setUserCreated(1);
    }
}