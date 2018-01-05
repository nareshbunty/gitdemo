package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.WhUserType;

public interface WhUserTypeRepository 
  extends JpaRepository<WhUserType, Long>,JpaSpecificationExecutor<WhUserType>{
	List<WhUserType> findByUserType(String userType);
	
}
