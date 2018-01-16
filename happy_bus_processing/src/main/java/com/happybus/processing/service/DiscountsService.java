package com.happybus.processing.service;

/**
 * UserService is used to implement Business Logic for Discounts
 * 
 * @author parvathi
 * @since 1.0
 */
public interface DiscountsService {

	/**
	 * the method contain logic store coupon details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	public String storeCouponDetails(String jsonCoupon);

	/**
	 * the method contain logic delete coupon details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	public String deleteCouponDetails(String jsonCoupon);

	/**
	 * the method contain logic to search coupon details
	 * 
	 * @return coupon
	 * @author parvathi
	 */
	public String searchCouponDetails();

	/**
	 * the method contain logic store discount details
	 * 
	 * @param jsonDiscount
	 * @return jsonResponse
	 * @author parvathi
	 */

	public String insertDiscountDetails(String jsonDiscount);

	/**
	 * the method contain logic search discount details
	 * 
	 * @return jsonResponse
	 * @author parvathi
	 */
	public String searchDiscountDetails();

	/**
	 * the method contain logic delete discount details
	 * 
	 * @param jsonDiscount
	 * @return jsonResponse
	 * @author parvathi
	 */

	public String deleteDiscountDetails(String jsonDiscount);
	public String checkCouponDetails(String jsonCoupon);
	

}
