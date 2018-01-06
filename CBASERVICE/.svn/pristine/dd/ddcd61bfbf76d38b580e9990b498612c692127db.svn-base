package com.cba.processing;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class CbaEmailServiceImpl
implements CbaEmailService{
    @Autowired
	private JavaMailSender javaMailSender;
	@Override
	public String sendEmail(String to, String subject, String body) {
	  String status="FAILURE";
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage);
		try {
			messageHelper.setTo(to);
		messageHelper.setSubject(subject);
		messageHelper.setText(body);
	    javaMailSender.send(mimeMessage);
	    status="SUCCESS";
		} catch (MessagingException e) {
			status="FAILURE";
		}
		return status;
	}
}
