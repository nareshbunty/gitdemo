package com.app.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.ShipmentType;
import com.app.repo.ShipmentTypeRepository;
import com.app.service.IShipmentTypeService;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {
	
	@Autowired
	private ShipmentTypeRepository repo;
	
	@Override
	public long save(ShipmentType shipmentType) {
		shipmentType.setCreatedDate(new Date());
		return repo.save(shipmentType).getShipmentId();
	}

	@Override
	public List<ShipmentType> saveMultiple(List<ShipmentType> list) {
		return repo.save(list);
	}
	
	@Override
	public void update(ShipmentType shipmentType) {
		shipmentType.setCreatedDate(
				repo.getOne(shipmentType.getShipmentId())
				.getCreatedDate());
		shipmentType.setLastModifiedDate(new Date());
		repo.save(shipmentType);
	}

	@Override
	public void deleteById(long shipmentTypeId) {
		if(repo.exists(shipmentTypeId)){
			repo.delete(shipmentTypeId);
		}
	}

	@Override
	public ShipmentType getOneById(long shipmentTypeId) {
		return repo.getOne(shipmentTypeId);
	}

	@Override
	public List<ShipmentType> getAll() {
		List<ShipmentType> list=repo.findAll();
		Collections.sort(list);
		return list;
	}

	@Override
	public Page<ShipmentType> getAll(Specification<ShipmentType> s, Pageable pageable) {
		return repo.findAll(s,pageable);
	}

	@Override
	public List<ShipmentType> findByEnabled(String enabled) {
		return repo.findByEnabled(enabled);
	}
}
