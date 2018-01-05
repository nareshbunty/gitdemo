package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.OrderMethod;

public interface OrderMethodRepository 
  extends JpaRepository<OrderMethod, Long>,JpaSpecificationExecutor<OrderMethod>{
	List<OrderMethod> findByOrderMode(String orderMode);

}
