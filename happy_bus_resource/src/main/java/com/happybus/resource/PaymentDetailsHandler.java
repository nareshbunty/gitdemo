package com.happybus.resource;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.happybus.processing.service.BookingService;
import com.happybus.processing.service.PaymentService;
import com.happybus.processing.service.SearchBookingService;


@RestController
@RequestMapping(value = "payment")
public class PaymentDetailsHandler {

	@Autowired
	private PaymentService paymentService; 
	/**
	 * the method is a resource method for Booking boarding details
	 * 
	 * @param jsonUser
	 * @return jsonResponse
	 * @author Rajlaxmi
	 */
	@RequestMapping(value = "searchPayment", method = RequestMethod.GET)
	@ResponseBody
	public String searchPayment(@RequestParam("payment_time_from") Date payment_time_from,
								@RequestParam("payment_time_to") Date payment_time_to) {
		return paymentService.searchPayment(payment_time_from, payment_time_to);
	}


} 
	
