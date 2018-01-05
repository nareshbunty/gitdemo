package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.app.model.Item;

public interface IItemService {

	public long save(Item item);
	public List<Item> saveMultiple(List<Item> list);
	public void update(Item item);
	public void deleteById(long itemId);
	
	public Item getOneById(long itemId);
	public List<Item> getAll();
	public Page<Item> getAll(Specification<Item> s,Pageable pageable);
	
	public List<Item> findItemsByVendor(Long vendorId);
}
