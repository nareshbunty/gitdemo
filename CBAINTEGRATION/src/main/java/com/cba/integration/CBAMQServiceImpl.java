package com.cba.integration;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
@Service
public class CBAMQServiceImpl implements CBAMQService {
	@Autowired
private JmsTemplate jmsTemplate;
	@Value("${activemq.destination}")
	private String desitinationMQ;
	@Override
	public String
	transactionsMessageQueue(String jsonAccountTransactions) {
	String status="FALIURE";
	jmsTemplate.send(desitinationMQ,new MessageCreator() {
		
		@Override
		public Message createMessage(Session session) throws JMSException {
		Message message=session.createMessage();
		   message.setObjectProperty("jsonAccountTransactions",jsonAccountTransactions);
			return message;
		}
	});
	status="SUCCESS";
		return status;
	}

}
