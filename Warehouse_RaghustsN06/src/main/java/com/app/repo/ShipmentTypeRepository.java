package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.ShipmentType;

public interface ShipmentTypeRepository 
  extends JpaRepository<ShipmentType, Long>,JpaSpecificationExecutor<ShipmentType>{

	public List<ShipmentType> findByEnabled(String enabled);
}
