package com.app.mapper;

import com.app.dto.TblCampusTo;
import com.app.model.TblCampus;

public class TblCampusToTblCampusToMapper {
    public void map(final TblCampus tblCampus, final TblCampusTo tblCampusTo) {
        tblCampusTo.setCampusCode(tblCampus.getCampusCode());
        tblCampusTo.setCampusName(tblCampus.getCampusName());
        tblCampusTo.setId(tblCampus.getId());
    }
}