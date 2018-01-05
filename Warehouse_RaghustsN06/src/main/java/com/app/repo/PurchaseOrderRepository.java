package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.app.model.PurchaseOrder;

public interface PurchaseOrderRepository extends 
 JpaRepository<PurchaseOrder, Long>,JpaSpecificationExecutor<PurchaseOrder>{

}
