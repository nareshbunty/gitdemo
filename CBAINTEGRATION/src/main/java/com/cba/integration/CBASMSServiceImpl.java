package com.cba.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CBASMSServiceImpl implements
CBASMSService{
	@Autowired
private RestTemplate restTemplate;
	@Value("${sms.url}")
	private String apiurl;
	@Value("${sms.apikey}")
	private String apiKey;
	@Value("${sms.senderid}")
	private String senderId;
	@Value("${sms.channel}")
	private String channel;
	@Value("${sms.dcs}")
	private String dcs;
	@Value("${sms.flashsms}")
	private String flashsms;
	@Value("${sms.route}")
	private String route;
	@Override
	public String sendSms(String mobile, String sms) {
String url=apiurl+ "APIKey="+apiKey+"&senderid="+senderId+"&channel="+channel+"&DCS="+dcs+"&flashsms="+flashsms+"&number="+mobile+"&text="+sms+"&route="+route;		
  String status=restTemplate.getForObject(url,String.class);		
return status;
	}

}
