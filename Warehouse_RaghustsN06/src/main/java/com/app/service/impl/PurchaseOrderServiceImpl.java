package com.app.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.PurchaseOrder;
import com.app.repo.PurchaseOrderRepository;
import com.app.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repo;
	
	@Override
	public long save(PurchaseOrder po) {
		po.setCreatedDate(new Date());
		return repo.save(po).getOrderId();
	}

	@Override
	public List<PurchaseOrder> saveMultiple(List<PurchaseOrder> list) {
		return repo.save(list);
	}

	@Override
	public void update(PurchaseOrder po) {
		po.setCreatedDate(
				repo.getOne(po.getOrderId())
				.getCreatedDate());
		po.setLastModifiedDate(new Date());
		repo.save(po);
	}

	@Override
	public void deleteById(long poId) {
		if(repo.exists(poId)){
			repo.delete(poId);
		}
	}

	@Override
	public PurchaseOrder getOneById(long poId) {
		return repo.getOne(poId);
	}

	@Override
	public List<PurchaseOrder> getAll() {
		List<PurchaseOrder> list=repo.findAll();
		Collections.sort(list);
		return list;
	}

	@Override
	public Page<PurchaseOrder> getAll(Specification<PurchaseOrder> s, Pageable pageable) {
		Page<PurchaseOrder> page=repo.findAll(s, pageable);
		return page;
	}
}