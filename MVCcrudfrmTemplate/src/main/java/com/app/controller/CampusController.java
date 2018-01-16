package com.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.common.Constants;
import com.app.dto.TblCampusTo;
import com.app.mapper.TblCampusToTblCampusToMapper;
import com.app.mapper.TblCampusToToTblCampusMapper;
import com.app.model.TblCampus;
import com.app.service.ICampusService;
import com.app.validator.CreateCampusValidator;
import com.app.validator.EditCampusValidator;

@Controller
@RequestMapping("campusController")
public class CampusController {
    @Autowired
    ICampusService campusService;
    @Autowired
    CreateCampusValidator createCampusValidator;
    @Autowired
    TblCampusToToTblCampusMapper tblCampusToToTblCampusMapper;
    @Autowired
    TblCampusToTblCampusToMapper tblCampusToTblCampusToMapper;
    @Autowired
    EditCampusValidator editCampusValidator;
   

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView createCampus(final Model model) {
        TblCampusTo tblCampusTo = new TblCampusTo();
        model.addAttribute("tblCampusTo", tblCampusTo);
        return new ModelAndView("campus/createCampus");
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView createCampus(@ModelAttribute("tblCampusTo") final TblCampusTo tblCampusTo, final BindingResult result) {
        createCampusValidator.validate(tblCampusTo, result);
        if (result.hasErrors()) {
            return new ModelAndView("campus/createCampus");
        }
        else {
            TblCampus tblCampus = new TblCampus();
            tblCampusToToTblCampusMapper.map(tblCampusTo, tblCampus);
            campusService.saveOrUpdate(tblCampus);
        }
        return new ModelAndView("redirect:/campusController/view");
    }
    @RequestMapping("deleteCampus")
    public ModelAndView deleteCampus(@RequestParam("campusId") final int campusId, final RedirectAttributes redirectAttributes) {
    	/* List<TblCampus> allCampus = campusService.getAll();
         List<TblCampusTo> allCampusTos = new ArrayList<TblCampusTo>();*/
        
            TblCampus byId = campusService.getById(campusId);
            if (byId != null) {
                byId.setDeletionIndicator(Constants.Y);
                byId.setDateModified(new Date());
            }
            campusService.saveOrUpdate(byId);
            redirectAttributes.addFlashAttribute("success", "Deleted Successfully");
        
        return new ModelAndView("redirect:/campusController/view");
    }

   /* @RequestMapping("deleteCampus")
    public ModelAndView deleteCampus(@RequestParam("campusId") final int campusId, final RedirectAttributes redirectAttributes) {
        List<TblBuildingArea> byCampus = buildingAreaService.getByCampus(campusId);
        if (CollectionUtils.isEmpty(byCampus)) {
            TblCampus byId = campusService.getById(campusId);
            if (byId != null) {
                byId.setDeletionIndicator(Constants.Y);
                byId.setDateModified(new Date());
            }
            campusService.saveOrUpdate(byId);
            redirectAttributes.addFlashAttribute("success", "Deleted Successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("fail", "First Delete Building Of This Campus");
        }
        return new ModelAndView("redirect:/campusController/view");
    }*/

    @RequestMapping(value = "editCampus", method = RequestMethod.GET)
    public ModelAndView editCampus(@RequestParam("campusId") final int campusId, final Model model) {
        TblCampus byId = campusService.getById(campusId);
        TblCampusTo tblCampusTo = new TblCampusTo();
        if (byId != null) {
            tblCampusToTblCampusToMapper.map(byId, tblCampusTo);
        }
        model.addAttribute("tblCampusTo", tblCampusTo);
        return new ModelAndView("campus/editCampus");
    }

    @RequestMapping(value = "editCampus", method = RequestMethod.POST)
    public ModelAndView editCampus(@ModelAttribute("tblCampusTo") final TblCampusTo tblCampusTo, final Model model,
            final BindingResult bindingResult) {
        editCampusValidator.validate(tblCampusTo, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("campus/editCampus");
        }
        else {
            TblCampus byId = campusService.getById(tblCampusTo.getId());
            if (byId != null) {
                byId.setUserModified(1);
                tblCampusToToTblCampusMapper.map(tblCampusTo, byId);
            }
        }
        return new ModelAndView("redirect:/campusController/view");
    }

    /**
     *
     */
    public List<TblCampusTo> getAllCampus() {
        List<TblCampus> all = campusService.getAll();
        List<TblCampusTo> tblCampusToList = new ArrayList<>();
        for (TblCampus tblCampus : all) {
            TblCampusTo tblCampusTo = new TblCampusTo();
            tblCampusToTblCampusToMapper.map(tblCampus, tblCampusTo);
            tblCampusToList.add(tblCampusTo);
        }
        return tblCampusToList;
    }

    @RequestMapping("view")
    public ModelAndView getAllCampuses(final Model model) {
        List<TblCampusTo> allCampus = getAllCampus();
        model.addAttribute("tblCampusToList", allCampus);
        return new ModelAndView("campus/campusView");
    }
}