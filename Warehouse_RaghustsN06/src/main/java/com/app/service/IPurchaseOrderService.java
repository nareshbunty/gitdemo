package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.model.PurchaseOrder;

public interface IPurchaseOrderService {

	public long save(PurchaseOrder po);
	public List<PurchaseOrder> saveMultiple(List<PurchaseOrder> list);
	public void update(PurchaseOrder po);
	public void deleteById(long poId);
	
	public PurchaseOrder getOneById(long poId);
	public List<PurchaseOrder> getAll();
	public Page<PurchaseOrder> getAll(Specification<PurchaseOrder> s,Pageable pageable);
}
