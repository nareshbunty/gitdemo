package com.happybus.processing.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.Coupons;
import com.happybus.beans.Discounts;
import com.happybus.beans.Response;
import com.happybus.integration.dao.CouponsDAO;
import com.happybus.integration.dao.DiscountsDAO;
import com.happybus.util.JsonUtil;
import com.happybus.util.StatusUtil;

/**
 * UserService is used to implement Business Logic for Discounts
 * 
 * @author parvathi
 * @since 1.0
 */
@Service
public class DiscountsServiceImpl implements DiscountsService {
	private static Logger logger = Logger.getLogger(DiscountsServiceImpl.class);

	@Autowired
	private CouponsDAO couponDAO;
	@Autowired
	private DiscountsDAO discountsDAO;

	/**
	 * the method contain logic store coupon details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	@Override
	public String storeCouponDetails(String jsonCoupon) {
		String jsonResponse = "";
		int count = 0;
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Adding Coupon Details Success!");
		logger.info("Entered into storeCouponDetails :: " + jsonCoupon);

		Coupons coupon = JsonUtil.convertJsonToJava(jsonCoupon, Coupons.class);
		logger.info("Exception occured while converting json to java ! " + coupon.getCouponCode());

		if (coupon != null) {
			try {
				coupon.setCouponStatus((byte) 1);

				count = (int) couponDAO.storeCouponDetails(coupon);
				
			} catch (DataAccessException de) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Adding The Coupon Details In Database ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Adding The Coupon Details ::" + e.getMessage());
			}
		}
		// Convert Response Object Into Json
		if (count != 0) {
			jsonResponse = JsonUtil.convertJavaToJson(response);

			logger.info("Response Adding Coupon Details :: " + jsonResponse);

		}
		return jsonResponse;
	}

	/**
	 * the method contain logic delete coupon details
	 * 
	 * @param jsonCoupon
	 * @return jsonResponse
	 * @author parvathi
	 */
	@Override
	public String deleteCouponDetails(String jsonCoupon) {
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Deleting Coupon Details Success!");
		logger.info("Entered into deleteCouponDetails :: " + jsonCoupon);
		Coupons coupon = JsonUtil.convertJsonToJava(jsonCoupon, Coupons.class);
		if (coupon != null) {
			try {
				coupon.setCouponStatus((byte) 0);
				int count = couponDAO.deleteCouponDetails(coupon);

			} catch (DataAccessException de) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Deleting The Coupon Details ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Deleting The Coupon Details ::" + e.getMessage());
			}
		}
		// Convert Response Object Into Json

		String jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response Deleting Coupon Details :: " + jsonResponse);

		return jsonResponse;

	}

	@Override
	public String searchCouponDetails() {
		Response response = new Response();
		List<Coupons> couponsList = null;
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Searching Coupon Details Success!");
		logger.info("Entered into searchingCouponsDetails :: ");

		try {
			couponsList = couponDAO.searchCouponDetails();
			
		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Searching Coupon  Details ::" + de.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured Searching Coupons Details ::" + e.getMessage());
		}
		// adding list object to response object
		String jsonList = JsonUtil.convertJavaToJson(couponsList);
		response.setData(jsonList);

		// convert response to jsonresponse
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;

	}

	/**
	 * the method contain logic store discount details
	 * 
	 * @param jsonDiscount
	 * @return jsonResponse
	 * @author parvathi
	 */
	@Override
	public String insertDiscountDetails(String jsonDiscount) {
		String jsonResponse = "";
		int count = 0;
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Adding Discount Details Success!");
		logger.error("Entered into insertDiscountDetails :: " + jsonDiscount);

		Discounts discount = JsonUtil.convertJsonToJava(jsonDiscount, Discounts.class);
		logger.info("Exception occured while converting json to java :: "+discount.getDiscountAmount());
		if (discount != null) {
			try {
				discount.setDiscountStatus((byte) 1);

				count = (int) discountsDAO.insertDiscountDetails(discount);
				logger.info("Dao called from service class");

			} catch (DataAccessException de) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Adding The Discounts Details In Database ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Adding The Discounts Details ::" + e.getMessage());
			}
		}
		// Convert Response Object Into Json
		if (count != 0) {
			jsonResponse = JsonUtil.convertJavaToJson(response);

			logger.info("Response Adding Discount Details :: " + jsonResponse);

		}
		return jsonResponse;

	}

	/**
	 * the method contain logic delete discount details
	 * 
	 * @param jsonDiscount
	 * @return jsonResponse
	 * @author parvathi
	 */
	@Override
	public String deleteDiscountDetails(String jsonDiscount) {
		Response response = new Response();
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Deleting Discount Details Success! ");
		logger.info("Entered into deleteDiscountDetails :: " + jsonDiscount);
		Discounts discount = JsonUtil.convertJsonToJava(jsonDiscount, Discounts.class);
		if (discount != null) {
			try {
				discount.setDiscountStatus((byte) 0);
				logger.info("After setting discountStatus-------------------------- " + discount.getDiscountStatus());
				logger.info("After setting discountStatus-------------------------- " + discount.getDiscountId());
				int count = discountsDAO.deleteDiscountDetails(discount);

			} catch (DataAccessException de) {
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Deleting The Discount Details ::" + de.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
				response.setMessage("Unable to process!Please Try Again");
				logger.error("Exception Occured while Deleting The Discount Details ::" + e.getMessage());
			}
		}
		// Convert Response Object Into Json

		String jsonResponse = JsonUtil.convertJavaToJson(response);
		logger.info("Response Deleting discount Details :: " + jsonResponse);

		return jsonResponse;

	}

	/**
	 * the method contain logic search discount details
	 * 
	 * @return jsonResponse
	 * @author parvathi
	 */
	public String searchDiscountDetails() {
		Response response = new Response();
		List<Discounts> discountsList = null;
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Searching discounts Success!");
		logger.info("Entered into searchingDiscountDetails :: ");

		try {
			discountsList = discountsDAO.searchDiscountDetails();

		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Searching Discounts  Details ::" + de.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured Searching Discounts Details ::" + e.getMessage());
		}
		// adding list object to response object
		String jsonList = JsonUtil.convertJavaToJson(discountsList);
		response.setData(jsonList);

		// convert response to jsonresponse
		String jsonResponse = JsonUtil.convertJavaToJson(response);
		return jsonResponse;

	}

	@Override
	public String checkCouponDetails(String jsonCoupon) {
		Response response = new Response();
		
		response.setStatus(StatusUtil.HAPPY_STATUS_SUCCESS);
		response.setMessage("Searching Coupons Success!");
		try {
			//discountsList = discountsDAO.checkCouponDetails( coupon);

		} catch (DataAccessException de) {
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured while Searching Discounts  Details ::" + de.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
			response.setMessage("Unable to process!Please Try Again");
			logger.error("Exception Occured Searching Discounts Details ::" + e.getMessage());
		}
		
		
		return null;
	}

}
