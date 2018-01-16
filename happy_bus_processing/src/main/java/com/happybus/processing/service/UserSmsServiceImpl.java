package com.happybus.processing.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
@Service
public class UserSmsServiceImpl implements
UserSmsService{
private static Logger logger=Logger.getLogger(UserSmsService.class);
	@Override
	public String sendSms(String mobileNumber, String msg) {
		try {
			String uid = "uid="+"73617469736862";
			String pin = "&pin="+"25669ce0d7a59af99f1c65929f08cac3";
			String sender = "&sender="+"MYTBUS";
			String route = "&route="+"5";
			String tempid = "&tempid=81951";
			String mobile = "&mobile="+mobileNumber;
			String message = "&message="+msg;
			String pushid = "&pushid=1" ;
			
			HttpURLConnection conn = (HttpURLConnection) new URL("http://sms.paceinfonet.com/api/sms.php?").openConnection();
			String data = uid + pin + sender + route + tempid + mobile + message + pushid;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null) {
				stringBuffer.append(line);
			}
			rd.close();
			
			return stringBuffer.toString();
		} catch (Exception e) {
			logger.error("Error Occured while sending the sms "+e);
			throw new RuntimeException(e);
		}
	}

}
