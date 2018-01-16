package com.dotridge.dao;

import java.util.List;

import com.dotridge.bean.PatientBean;
import com.dotridge.domain.Patient;

public interface PatientDao {
	public Patient savePatient(Patient patient);

	public Patient updatePatient(Patient patient);

	public void deletePatient(int pId);
	
	public List<Patient> getAllPatients();
	
	public Patient getPatientById(int pId);
	
	public boolean exists(Patient patient);
	public List<Patient> getAllPatientsByPaging(int currPage, int noOfRecPage);
}
