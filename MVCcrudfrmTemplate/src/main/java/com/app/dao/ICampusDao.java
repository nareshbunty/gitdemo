package com.app.dao;

import java.util.List;

import com.app.model.TblCampus;

public interface ICampusDao {

	List<TblCampus> getAll();

	TblCampus getByCampusCode(String campusCode);

	TblCampus getById(int id);

	boolean isCampusExist(String campusCode, int campusId);

	void saveOrUpdate(TblCampus campus);

}