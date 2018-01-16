package com.happybus.processing.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.happybus.util.StatusUtil;

@Service
public class UserEmailServiceImpl implements UserEmailService{
   private static Logger logger=Logger.getLogger(UserEmailServiceImpl.class);
	@Autowired
	private JavaMailSender javaMailSender;
	@Override
	public String sendEmail(String to, String subject, String body) {
	String status=StatusUtil.HAPPY_STATUS_FAILURE;
	    MimeMessage message=javaMailSender.createMimeMessage();
	      MimeMessageHelper helper=new MimeMessageHelper(message);
	      try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			helper.setSentDate(new Date());
			//helper.setFrom("noreply@happybus.com");
		javaMailSender.send(message);
		status=StatusUtil.HAPPY_STATUS_SUCCESS;
		} catch (MessagingException e) {
			status=StatusUtil.HAPPY_STATUS_FAILURE;
		logger.error("Exception Occured while sending the email : "+e.getMessage());
		}
	      catch (Exception e) {
				status=StatusUtil.HAPPY_STATUS_FAILURE;
				logger.error("Exception Occured while sending the email : "+e.getMessage());
				
			}
	    
	    return status;
	}

}
