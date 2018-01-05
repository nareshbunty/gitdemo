package com.app.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.app.model.Item;
import com.app.repo.ItemRepository;
import com.app.service.IItemService;

@Service
public class ItemServiceImpl implements IItemService {

	@Autowired
	private ItemRepository repo;
	
	@Override
	public long save(Item item) {
		item.setCreatedDate(new Date());
		return repo.save(item).getItemId();
	}

	@Override
	public List<Item> saveMultiple(List<Item> list) {
		return repo.save(list);
	}

	@Override
	public void update(Item item) {
		item.setCreatedDate(
				repo.getOne(item.getItemId())
				.getCreatedDate());
		item.setLastModifiedDate(new Date());
		repo.save(item);
	}

	@Override
	public void deleteById(long itemId) {
		if(repo.exists(itemId)){
			repo.delete(itemId);
		}
	}

	@Override
	public Item getOneById(long itemId) {
		return repo.getOne(itemId);
	}

	@Override
	public List<Item> getAll() {
		List<Item> list=repo.findAll();
		Collections.sort(list);
		return list;
	}

	@Override
	public Page<Item> getAll(Specification<Item> s, Pageable pageable) {
		Page<Item> page=repo.findAll(s, pageable);
		return page;
	}
	
	@Override
	public List<Item> findItemsByVendor(Long vendorId) {
		return repo.findItemsByVendor(vendorId);
	}
}
