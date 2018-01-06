package com.happybus.web.client.controller;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happybus.web.client.service.PaymentService;

@Controller
public class PaymentController {
	Logger logger = Logger.getLogger("PaymentController");
	
	@Autowired
	private PaymentService paymentService;

	@RequestMapping(value = "searchPaymentDetails", method = RequestMethod.POST)
	public String showSearchPayment() {
		return "searchPayment";
	}

	@RequestMapping(value = "searchPayment", method = RequestMethod.POST)
	@ResponseBody
	public String searchPayment(Date payment_time_from, Date payment_time_to) {

		String jsonResponse = paymentService.searchPaymentDetails(payment_time_from, payment_time_to);
		return jsonResponse;
	}
	
}