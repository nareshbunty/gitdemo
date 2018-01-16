package com.app.service;

import java.util.List;

import com.app.model.TblCampus;

public interface ICampusService {

	List<TblCampus> getAll();

	//List<ParentChildTo> getAllCampusParentChildList(int ruleId);

	/**
	 * {@inheritDoc}
	 *
	 * @see org.nipun.it.rtls.nview.campus.service.ICampusService#getByCampusCode(java.lang.String)
	 */
	TblCampus getByCampusCode(String campusCode);

	TblCampus getById(int id);

	/**
	 * {@inheritDoc}
	 *
	 * @see org.nipun.it.rtls.nview.campus.service.ICampusService#isCampusExist(java.lang.String, int)
	 */
	boolean isCampusExist(String campusCode, int campusId);

	void saveOrUpdate(TblCampus campus);

}