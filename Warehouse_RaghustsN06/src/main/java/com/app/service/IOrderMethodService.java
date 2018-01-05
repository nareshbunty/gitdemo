package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.model.OrderMethod;

public interface IOrderMethodService {

	public long save(OrderMethod orderMethod);
	public List<OrderMethod> saveMultiple(List<OrderMethod> list);
	public void update(OrderMethod orderMethod);
	public void deleteById(long orderMethodId);
	
	public OrderMethod getOneById(long orderMethodId);
	public List<OrderMethod> getAll();
	public Page<OrderMethod> getAll(Specification<OrderMethod> s,Pageable pageable);
	
	/**
	 * added for Item Module
	 */
	List<OrderMethod> findByOrderMode(String orderMode);
	
}