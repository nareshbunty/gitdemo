package com.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.TblCampusTo;
import com.app.model.TblCampus;
import com.app.service.ICampusService;

@RestController
@RequestMapping("campus")
public class CampusRestController {
    @Autowired
    ICampusService campusService;

    @RequestMapping("getAll")
    public List<TblCampusTo> getAll() {
        List<TblCampus> allCampus = campusService.getAll();
        List<TblCampusTo> allCampusTos = new ArrayList<TblCampusTo>();
        for (TblCampus tblCampus : allCampus) {
            TblCampusTo tblCampusTo = new TblCampusTo();
            tblCampusTo.setId(tblCampus.getId());
            tblCampusTo.setCampusName(tblCampus.getCampusName());
            tblCampusTo.setCampusCode(tblCampus.getCampusCode());
            allCampusTos.add(tblCampusTo);
        }
        return allCampusTos;

    }
}