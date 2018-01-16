package com.dotridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dotridge.bean.PatientBean;
import com.dotridge.service.PatientService;

@Controller
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	
	
	@RequestMapping(value="/PatientRegform")
	public ModelAndView PatientRegForm(){
		ModelAndView mav = new ModelAndView("PatientRegistrationForm");
		
		return mav;
	}

	@RequestMapping(value ="/savePatient", method=RequestMethod.POST)
	public ModelAndView  savePatient(@ModelAttribute("patientBean")PatientBean patientBean,BindingResult result){
		patientService.savePatient(patientBean);
		return new ModelAndView("PatientRegistrationForm");
	} 
	
	
	@RequestMapping(value = "/viewAllPatients")
	public ModelAndView viewAllPatients() {
		try {
			
			List<PatientBean> uipatientslist = patientService.getAllPatients();
			ModelAndView mav = new ModelAndView();
			mav.setViewName("PatientList");
			
			mav.addObject("uipatientslist", uipatientslist);
			return mav;
		} catch (Exception e) {

		}
		return null;
	}
	
	

	
	@RequestMapping(value ="/deletePatient")
	public String deletePatient(@RequestParam("pId") int pId){
		
		patientService.deletePatient(Integer.valueOf(pId));
		return  "redirect:viewAllPatients";
	}
	
	
	
	@RequestMapping(value ="/editPatient")
	public String getPatient(@RequestParam ("pId")int pId,ModelMap model){
		PatientBean patientBean = patientService.getPatientById(pId);
		model.addAttribute("patientBean", patientBean);
		return  "PatientEdit";
	}
	
	@RequestMapping(value ="/updatePatient")
	public String updatePatient(@ModelAttribute("patientBean") PatientBean patientBean, ModelMap map){
		patientService.updatePatient(patientBean);
		return  "redirect:viewAllPatients";
	}
	
}
/*---
@RequestMapping(value ="/deleteEmp")
public String deleteEmployee(@RequestParam("empId") int empId){
	
	ser.deleteEmployee(empId);
	return  "redirect:viewAll";
}
@RequestMapping(value ="/viewAll")
public String getAllEmployee(@ModelAttribute Employee emp, ModelMap map){
	List<Employee> emplist=ser.getAllEmployee(emp);
	map.addAttribute("listobj",emplist );
	return  "ViewEmps";
}
@RequestMapping(value ="/editEmp")
public String getEmployee(@RequestParam ("empId")int empId,ModelMap map){
	Employee emp= ser.getEmployee(empId);
	map.addAttribute("listobj", emp);
	return  "EmployeeEdit";
}
@RequestMapping(value ="/updateEmp")
public String updateEmployee(@ModelAttribute Employee emp, ModelMap map){
	 ser.updateEmployee(emp);
	return  "redirect:viewAll";
}
@RequestMapping("/deletePatient")
public String deletePatient(HttpServletRequest request, Model model) {
	String pId = request.getParameter("pId");
	System.out.println("Patient delete Id..\t" + pId);
	patientService.deletePatient(Integer.valueOf(pId));
	return "redirect:getAllPatients";
}
@RequestMapping(value = "/updatePatient")
public ModelAndView updateAdmin(@ModelAttribute("patientBean") PatientBean patientBean) {

	System.out.println("patientBean in controller is:\t" + patientBean.toString());
	patientService.updatePatient(patientBean);
	try {
		List<PatientBean> uipatientslist = patientService.getAllPatients();
		ModelAndView mav = new ModelAndView();
		mav.setViewName("getPatientBoard");
		System.out.println("list of users" + uipatientslist.size());
		mav.addObject("uipatientslist", uipatientslist);
		return mav;
	} catch (Exception e) {
	}
	return null;
}
@RequestMapping(value = "/addPatient", method = RequestMethod.POST)
	public ModelAndView addPatient(@ModelAttribute("patientBean") PatientBean patientBean  , BindingResult result, 
			ModelMap model) {
		patientService.savePatient(patientBean);
		try {
			List<PatientBean> uipatientslist = patientService.getAllPatients();
			ModelAndView mav = new ModelAndView();
			mav.setViewName("getPatientBoard");
			System.out.println("list of patients" + uipatientslist.size());
			mav.addObject("uipatientslist", uipatientslist);
			return mav;
		} catch (Exception e) {

		}

		return null;

	}
	

*/