package com.dotridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.dotridge.bean.PatientBean;
import com.dotridge.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
	
	@Autowired
	PatientService patientService;
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> savePatient(@RequestBody PatientBean patientBean, UriComponentsBuilder ucBuilder){
		//patientService.savePatient(patientBean);
		if (patientService.exists(patientBean)) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		patientService.savePatient(patientBean);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/patientBean/{id}").buildAndExpand(patientBean.getpId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping( method = RequestMethod.GET)
	public ResponseEntity<List<PatientBean>> getAllPatients(@ModelAttribute PatientBean patientBean){
		List<PatientBean> emplist=patientService.getAllPatients();
		

		if (emplist == null || emplist.isEmpty()) {
			return new ResponseEntity<List<PatientBean>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<PatientBean>>(emplist, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{pId}", method = RequestMethod.GET)
	public ResponseEntity<PatientBean>  getPatientById(@PathVariable ("pId")int pId){
		PatientBean emp= patientService.getPatientById(pId);
		if (emp == null) {
			return new ResponseEntity<PatientBean>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<PatientBean>(emp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{pId}", method = RequestMethod.PUT)
	public ResponseEntity<PatientBean> updatePatient(@PathVariable("pId") int pId, @RequestBody PatientBean patientBean){
		PatientBean empl = patientService.getPatientById(pId);
		if (empl == null) {
			return new ResponseEntity<PatientBean>(HttpStatus.NOT_FOUND);
		}

		//empl.setpId(patientBean.getpId());
		//empl.setName(emp.getEmpName());

		patientService.updatePatient(empl);
		return new ResponseEntity<PatientBean>(empl, HttpStatus.OK);
	}
	
	@RequestMapping(value = "{pId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletePatient(@PathVariable("pId") int pId){
		
		PatientBean emp = patientService.getPatientById(pId);
		if (emp == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		patientService.deletePatient(pId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}	
	
	
	