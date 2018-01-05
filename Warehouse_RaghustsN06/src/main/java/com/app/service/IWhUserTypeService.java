package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.model.WhUserType;


public interface IWhUserTypeService {

	public long save(WhUserType whUserType);
	public List<WhUserType> saveMultiple(List<WhUserType> list);
	public void update(WhUserType whUserType);
	public void deleteById(long whUserTypeId);
	
	public WhUserType getOneById(long whUserTypeId);
	public List<WhUserType> getAll();
	public Page<WhUserType> getAll(Specification<WhUserType> spec,Pageable pageable);
	
	public List<WhUserType> findByUserType(String userType);
}
