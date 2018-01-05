package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.model.ShipmentType;

public interface IShipmentTypeService {
	
	public long save(ShipmentType shipmentType);
	public List<ShipmentType> saveMultiple(List<ShipmentType> list);
	public void update(ShipmentType shipmentType);
	public void deleteById(long shipmentTypeId);
	
	public ShipmentType getOneById(long shipmentTypeId);
	public List<ShipmentType> getAll();
	public Page<ShipmentType> getAll(Specification<ShipmentType> s,Pageable pageable);

	public List<ShipmentType> findByEnabled(String enabled);
}
