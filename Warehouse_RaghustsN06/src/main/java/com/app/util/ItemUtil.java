package com.app.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.app.service.IOrderMethodService;
import com.app.service.IUomService;
import com.app.service.IWhUserTypeService;

@Component
public class ItemUtil {
	@Autowired
	private IUomService uomService;
	@Autowired
	private IOrderMethodService omService;
	@Autowired
	private IWhUserTypeService whUserService;
	
	
	public List<String> getBaseCurrencies(){
		List<String> list=new LinkedList<String>();
		list.add("INR");
		list.add("USD");
		list.add("AUD");
		return list;
	}
	
	public void getDynamics(ModelMap map){
		map.addAttribute("baseCurrencies", getBaseCurrencies());
		map.addAttribute("itemUoms",uomService.getAll());
		map.addAttribute("itemSaleOrdMthds",omService.findByOrderMode("Sale"));
		map.addAttribute("itemPurchaseOrdMthds",omService.findByOrderMode("Purchase"));
		map.addAttribute("itemVendorsList",whUserService.findByUserType("Vendor"));
		map.addAttribute("itemCustomersList",whUserService.findByUserType("Customer"));
	}
}
