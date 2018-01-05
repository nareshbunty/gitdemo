package com.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.app.model.Item;

public interface ItemRepository 
extends JpaRepository<Item, Long>,JpaSpecificationExecutor<Item>{
	@Query("select itm from com.app.model.Item itm inner join itm.itemVendors ven where ven.whUserTypeId=?1")
	List<Item> findItemsByVendor(Long vendorId);
}
