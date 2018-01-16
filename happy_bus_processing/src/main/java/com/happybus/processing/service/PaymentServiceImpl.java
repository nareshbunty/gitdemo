package com.happybus.processing.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.happybus.beans.Payments;
import com.happybus.beans.Response;
import com.happybus.integration.dao.PaymentsDAO;
import com.happybus.util.JsonUtil;
import com.happybus.util.StatusUtil;

@Service
public class PaymentServiceImpl implements PaymentService{

	
	@Autowired
	private PaymentsDAO paymentDAO;
	
	@Autowired
	private UserAuthenticationService userAuthenticationService;
	
	private static Logger logger=Logger.getLogger(PaymentServiceImpl.class);
	
	 
	@Override
	public String searchPayment(Date payment_time_from,Date payment_time_to) {
		// logger information
					//logger.info("PaymentFrom  : " + jsonPaymentFrom);
					//logger.info("Payment To   : " + jsonPaymentTo);

					// creating response object with default settings
					Response response = new Response();
					List<Payments> paymentList = null;
					response.setStatus(StatusUtil.HAPPY_STATUS_FAILURE);
					response.setMessage("Please fill Payment time & try again");

					
					try {
						paymentList = paymentDAO.searchPayment(payment_time_from, payment_time_to);
						
					} catch (DataAccessException dae) {
						response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
						response.setMessage("Unable to process!Please Try Again");
						logger.error("Exception Occured while Searching the Payment  Details ::" + dae.getMessage());
					} catch (Exception e) {
						e.printStackTrace();
						response.setStatus(StatusUtil.HAPPY_STATUS_EXCEPTION);
						response.setMessage("Unable to process!Please Try Again");
						logger.error("Exception Occured Searching  Payment  Details ::" + e.getMessage());
					}
					
					// convert json input into java object
					String jsonPaymentList = JsonUtil.convertJavaToJson(paymentList);
					response.setData(jsonPaymentList);

					// convert response to jsonresponse
					String jsonResponse = JsonUtil.convertJavaToJson(response);
					return jsonResponse;

		
	}
}