package com.happybus.web.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.beans.Coupons;
import com.happybus.beans.Discounts;
import com.happybus.web.client.service.DiscountService;


@Controller

public class DiscountController {
	@Autowired
	private DiscountService discountService;

	@RequestMapping(value = "addDiscount", method = RequestMethod.GET)

	public String showAddDiscount() {

		return "addDiscounts";
	}

	@RequestMapping(value = "discountDetails", method = RequestMethod.POST)
	@ResponseBody
	public String insertDiscountDetails(@ModelAttribute Discounts discount) {
		String jsonResponse = discountService.addDiscountDetails(discount);

		return jsonResponse;

	}
	
	@RequestMapping(value = "searchDiscountDetails", method = RequestMethod.GET)
	public String showSearchDiscount() {
		return "searchDiscounts";
	}
	

	@RequestMapping(value = "searchDiscount", method = RequestMethod.GET)
	@ResponseBody
	public String deleteCoupon(@ModelAttribute Discounts discount) {

		String jsonResponse = discountService.searchDiscountDetails();
		return jsonResponse;
	}

	@RequestMapping(value = "deleteDiscount", method = RequestMethod.GET)
	@ResponseBody
	public String showDeleteDiscount() {

    return "deleteDiscount";
	}

	@RequestMapping(value = "deleteDiscountDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDiscount(@ModelAttribute Discounts discount) {

		String jsonResponse = discountService.deleteDiscountDetails(discount);
		return jsonResponse;
	}

	

	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "addCoupon", method = RequestMethod.GET)

	public String showAddCoupon() {

		return "addCoupon";
		}
	
	
	@RequestMapping(value = "couponDetails", method = RequestMethod.POST)
	@ResponseBody
	public String storeCouponDetails(@ModelAttribute Coupons coupon) {
		String jsonResponse = discountService.storeCouponDetails(coupon);

		return jsonResponse;

	}
	
	
	
	
	
	@RequestMapping(value = "searchCouponDetails", method = RequestMethod.GET)
	public String showSearchCoupon() {
		return "searchCoupon";
	}
	
	
	

	@RequestMapping(value = "searchCoupons", method = RequestMethod.GET)
	@ResponseBody
	public String searchCoupon() {

		String jsonResponse = discountService.searchCouponDetails();
		return jsonResponse;
	}

	
	
	@RequestMapping(value = "deleteCoupon", method = RequestMethod.GET)
	
	public String showDeleteCoupon() {

		
		return  "deleteCoupon";
	}
	
	@RequestMapping(value = "deleteCouponDetails", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCoupon(@ModelAttribute Coupons coupon) {

		String jsonResponse = discountService.deleteCouponDetails(coupon);
		return jsonResponse;
	}



}
