package com.dotridge.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.dotridge.dao.PatientDao;
import com.dotridge.domain.Patient;

@Repository

public class PatientDaoImpl implements PatientDao {
	
@Autowired
HibernateTemplate ht;


	public Patient savePatient(Patient patient) {
		ht.save(patient);
		return patient;
	}



	public Patient updatePatient(Patient patient) {
		ht.update(patient);
		return patient;
	}

	public void deletePatient(int pId) {
		Patient p=new Patient();
		 p.setpId(pId);
		 ht.delete(p);
		 
		 }

	public List<Patient> getAllPatients() {
		
		return ht.loadAll(Patient.class);
	}


	public Patient getPatientById(int pId) {
	
		return ht.get(Patient.class, pId);
	}


	public boolean exists(Patient patient){
		return getPatientById(patient.getpId()) != null;
	}
	
	public List<Patient> getAllPatientsByPaging(int currPage, int noOfRecPage) {
		// TODO Auto-generated method stub
		return null;
	}

}
