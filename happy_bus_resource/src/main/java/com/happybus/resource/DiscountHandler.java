package com.happybus.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.DiscountsService;

@RestController
@RequestMapping(value = "discount")
public class DiscountHandler {
	@Autowired
	private DiscountsService discountsService;

	/**
	 * the method is a resource method for Add Coupon Details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	@RequestMapping(value = "addCoupon", method = RequestMethod.POST)
	@ResponseBody
	public String storeCouponDetails(@RequestBody String jsonCoupon) {
		return discountsService.storeCouponDetails(jsonCoupon);

	}

	/**
	 * the method is a resource method for Delete Coupon Details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	@RequestMapping(value = "deleteCoupon", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCouponDetails(@RequestBody String jsonCoupon) {
		return discountsService.deleteCouponDetails(jsonCoupon);

	}

	/**
	 * the method is a resource method for Search Coupon Details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	@RequestMapping(value = "searchCoupon", method = RequestMethod.GET)
	@ResponseBody
	public String searchCouponDetails() {
		return discountsService.searchCouponDetails();

	}

	/**
	 * the method is a resource method for Add Discount Details
	 * 
	 * @param jsonDiscount
	 * @return jsonResponse
	 * @author parvathi
	 */
	@RequestMapping(value = "addDiscounts", method = RequestMethod.POST)
	@ResponseBody
	public String insertDiscountDetails(@RequestBody String jsonDiscount) {
		return discountsService.insertDiscountDetails(jsonDiscount);

	}

	/**
	 * the method is a resource method for Search Discount Details
	 * 
	 * @return jsonResponse
	 * @author parvathi
	 */
	@RequestMapping(value = "searchDiscount", method = RequestMethod.GET)
	@ResponseBody
	public String deleteDiscountDetails() {
		return discountsService.searchDiscountDetails();

	}

	/**
	 * the method is a resource method for Delete Discount Details
	 * 
	 * @param jsonDiscount
	 * @return jsonResponse
	 * @author parvathi
	 */
	@RequestMapping(value = "deleteDiscount", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDiscountDetails(@RequestBody String jsonDiscount) {
		return discountsService.deleteDiscountDetails(jsonDiscount);

	}

}
