package com.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.app.model.Item;
import com.app.service.IItemService;
import com.app.service.IShipmentTypeService;
import com.app.service.IWhUserTypeService;

@Component
public class PurchaseOrderUtil {

	@Autowired
	private IWhUserTypeService whUserService;
	@Autowired
	private IShipmentTypeService shipMentService;
	@Autowired
	private IItemService itemService;
	
	public List<String> getQualityChecks(){
		List<String> list=new ArrayList<String>();
		list.add("Required");
		list.add("Not Required");
		return list;
	}
	
	public void getDynamics(ModelMap map) {
		map.addAttribute("qualityChecks", getQualityChecks());
		map.addAttribute("vendorsList",whUserService.findByUserType("Vendor"));
		map.addAttribute("shipmentTypes",shipMentService.findByEnabled("YES"));
	}
	
	public List<Item> getVendorItems(Long vendorId){
		 return itemService.findItemsByVendor(vendorId);
	}


}
