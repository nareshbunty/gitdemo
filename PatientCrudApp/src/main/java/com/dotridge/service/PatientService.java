package com.dotridge.service;

import java.util.List;

import com.dotridge.bean.PatientBean;

public interface PatientService {
	public PatientBean savePatient(PatientBean patientBean);

	public PatientBean updatePatient(PatientBean patientBean);

	public void deletePatient(int pId);
	
	public List<PatientBean> getAllPatients();
	
	public PatientBean getPatientById(int pId);
	
	public boolean exists(PatientBean patientBean);
	
	public List<PatientBean> getAllPatientsByPaging(int currPage, int noOfRecPage);
}
