package com.dotridge.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dotridge.bean.PatientBean;
import com.dotridge.dao.PatientDao;
import com.dotridge.domain.Patient;
import com.dotridge.service.PatientService;
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
@Autowired
PatientDao patientDao;

	public PatientBean savePatient(PatientBean patientBean) {
		Patient patientDomain = patientDao.savePatient(mapBeanToDomain(patientBean));

		return mapDomainToBean(patientDomain);
	}

	public PatientBean updatePatient(PatientBean patientBean) {
		Patient patientDomain = patientDao.updatePatient(mapBeanToDomain(patientBean));

		return mapDomainToBean(patientDomain);
	}

	public void deletePatient(int pId) {
		patientDao.deletePatient(pId);
		
	}

	public List<PatientBean> getAllPatients() {
		List<Patient>  patientslist= patientDao.getAllPatients();
		List<PatientBean> uipatientslist = null;

		if (patientslist != null && !patientslist.isEmpty()) {
			
			uipatientslist = new ArrayList<PatientBean>();
			for (Patient patient : patientslist) {
				PatientBean patientBean = mapDomainToBean(patient);
				uipatientslist.add(patientBean);
			}
			
			return uipatientslist;
		} else {
			
			return uipatientslist;
		}
		

	}


	public PatientBean getPatientById(int pId) {
		return mapDomainToBean(patientDao.getPatientById(pId));
	}

	
	public boolean exists(PatientBean patientBean){
		
		 return patientDao.exists(mapBeanToDomain(patientBean));
	}

	public List<PatientBean> getAllPatientsByPaging(int currPage,
			int noOfRecPage) {
		// TODO Auto-generated method stub
		return null;
	}
	
public Patient mapBeanToDomain(PatientBean patientBean) {
		
		Patient patient=new Patient();
		if (patientBean.getpId() > 0) {

			patient.setpId(patientBean.getpId());
		}

		patient.setFirstName(patientBean.getFirstName());
		
		patient.setLastName(patientBean.getLastName());
		patient.setGender(patientBean.getGender());
		
		
		patient.setEmail(patientBean.getEmail());
		
		patient.setPhone(patientBean.getPhone());
		
		return patient;
	}

	public PatientBean mapDomainToBean(Patient patient) {
		PatientBean patientBean=new PatientBean();
		patientBean.setpId(patient.getpId());
		patientBean.setFirstName(patient.getFirstName());
		
		patientBean.setLastName(patient.getLastName());
		patientBean.setGender(patient.getGender());
		
		patientBean.setEmail(patient.getEmail());
		
		patientBean.setPhone(patient.getPhone());
		
		return patientBean;
	}



}
