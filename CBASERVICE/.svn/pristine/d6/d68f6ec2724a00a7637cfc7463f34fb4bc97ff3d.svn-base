package com.cba.processing;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.cba.beans.AccountStatusInfo;
import com.cba.beans.AccountTransactions;
import com.cba.beans.Response;
import com.cba.beans.util.ResponseCodes;
import com.cba.dao.AccountDAO;
import com.cba.dao.AccountTransactionsDAO;
import com.cba.dao.CustomerDAO;
import com.cba.integration.CBAMQService;
import com.cba.integration.CBASMSService;
import com.cba.processing.util.JsonUtil;

@Service
public class OperationalServiceImpl
implements OperationalService{
	@Autowired
	private CBAMQService cbaMQService;
	private static Logger logger=Logger.getLogger(OperationalServiceImpl.class);
	@Autowired
private AccountDAO accountDAO;
	@Autowired
	private CbaEmailService cbaEmailService;
	@Autowired
	private CBASMSService cbaSMSService;
	@Autowired
private AccountTransactionsDAO accountTransactionsDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Override
	public String checkAccountStatusInfo(String accountNumber) {
   Response response=new Response();
   response.setStatus(ResponseCodes.STATUS_FAILURE);
   response.setMessage("Unable to process your request!please try again");
try{
   AccountStatusInfo accountStatusInfo=accountDAO.checkAccountStatus(accountNumber);
  String jsonAccountStatus=JsonUtil.convertJavaToJson(accountStatusInfo);
     response.setStatus(ResponseCodes.STATUS_SUCCESS);
     response.setMessage("Account Found");
      response.setData(jsonAccountStatus);
}catch(EmptyResultDataAccessException e){
	response.setMessage(accountNumber+" Account Does not exist");

}catch(DataAccessException de){
response.setMessage("Unable to process your request!please try again");

}
	String jsonResponse=JsonUtil.convertJavaToJson(response);  
	  return jsonResponse;
	}
	@Override
	public String deposit(AccountTransactions accountTransactions) {
	Response response=new Response();
	response.setStatus(ResponseCodes.STATUS_FAILURE);
	response.setMessage("Depoist Failure!Please Try Again.");
	try{
	Map<String,Object> map=accountTransactionsDAO.deposit(accountTransactions);
	if(map!=null && map.size()>0){
	Object transactionId=map.get("TX_ID_OUT");
	if(transactionId!=null){
	response.setStatus(ResponseCodes.STATUS_SUCCESS);
	response.setMessage(accountTransactions.getTransactionAmount()+" is successfully despoisted into "+accountTransactions.getAccountNumber()+" .And The Transaction Id is "+transactionId);
	response.setData(transactionId.toString());
	accountTransactions.setTransactionId((Integer)transactionId);
	try{
		String jsonAccountTransactions=JsonUtil.convertJavaToJson(accountTransactions);
	String status=cbaMQService.transactionsMessageQueue(jsonAccountTransactions);
	logger.info("MQ STATUS :"+status);
	}catch(Exception e){
	logger.error("Exception Occured while sending the Message Queue : "+e);	
	}
	String email="";
	String mobile="";
	try{
		//send sms and email
	Map<String,Object> customerDetailsMap=customerDAO.getCustomerEmailAndMobile(accountTransactions.getAccountNumber());
	if(customerDetailsMap!=null && customerDetailsMap.size()>0){
	 email=(String)customerDetailsMap.get("email");
	 mobile=(String)customerDetailsMap.get("PHONE_NUMBER1");
	String subject="CBA Alerts";
	String body="Dear "+accountTransactions.getAccountNumber()+" User .\n This is inform you to "+accountTransactions.getTransactionAmount()+" is Credited Into Your Account.\nAnd The Transaction Reference Id is "+transactionId;
	String status=cbaEmailService.sendEmail(email,subject,body);
	logger.info("Email Status : "+status);
	}
	}catch(Exception e){
		logger.error("Exception Occured while sending the Email :"+e);
			
	}
	try{
		String sms=" Dear User "+accountTransactions.getTransactionAmount()+" is credited into your account";
String status=cbaSMSService.sendSms(mobile,sms);
logger.info("SMS Status :"+status);
	}catch(Exception e){
		logger.error("Exception Occured while sending the SMS :"+e);
				
	}
	}
	}
	}catch(DataAccessException de){
	response.setStatus(ResponseCodes.STATUS_FAILURE);
	response.setMessage("Unable to process your Request!Please Try Again.");
	logger.error("Exception Occured while Deposit "+de);
	}
	String jsonResponse=JsonUtil.convertJavaToJson(response);
	return jsonResponse;
	}
	@Override
public String getAccountBalance(String accountNumber) {
	Response response=new Response();
	response.setStatus(ResponseCodes.STATUS_FAILURE);
	response.setMessage("Account Does Not Exist");
	try{
	Map<String,Object> map
	=accountDAO.getAccountBalance(accountNumber);
	if(map!=null && map.size()>0){	
  response.setStatus(ResponseCodes.STATUS_SUCCESS);
   response.setMessage("Account Found");
   response.setDataList(new String[]{map.get("total_balance").toString(),map.get("account_status").toString()});
	}
	}catch(EmptyResultDataAccessException e){
		response.setMessage("Account Does Not Exist");
		
	}catch(DataAccessException e){
		response.setMessage("Unable To process your request!please try again");	
	}
	String jsonResponse=JsonUtil.convertJavaToJson(response);
	return jsonResponse;
	}
}












