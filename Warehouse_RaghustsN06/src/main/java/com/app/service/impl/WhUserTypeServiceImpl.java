package com.app.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.WhUserType;
import com.app.repo.WhUserTypeRepository;
import com.app.service.IWhUserTypeService;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {
	
	@Autowired
	private WhUserTypeRepository repo;
	
	@Override
	public long save(WhUserType whUserType) {
		whUserType.setCreatedDate(new Date());
		return repo.save(whUserType).getWhUserTypeId();
	}

	@Override
	public List<WhUserType> saveMultiple(List<WhUserType> list) {
		return repo.save(list);
	}

	@Override
	public void update(WhUserType whUserType) {
		whUserType.setCreatedDate(
				repo.getOne(whUserType.getWhUserTypeId())
				.getCreatedDate());
		whUserType.setLasModifiedDate(new Date());
		repo.save(whUserType);
	}

	@Override
	public void deleteById(long whUserTypeId) {
		if(repo.exists(whUserTypeId)){
			repo.delete(whUserTypeId);
		}
	}

	@Override
	public WhUserType getOneById(long whUserTypeId) {
		return repo.getOne(whUserTypeId);
	}

	@Override
	public List<WhUserType> getAll() {
		List<WhUserType> list=repo.findAll();
		Collections.sort(list);
		return list;
	}

	@Override
	public Page<WhUserType> getAll(Specification<WhUserType> spec, Pageable pageable) {
		return repo.findAll(spec, pageable);
	}

	@Override
	public List<WhUserType> findByUserType(String userType) {
		return repo.findByUserType(userType);
	}
}