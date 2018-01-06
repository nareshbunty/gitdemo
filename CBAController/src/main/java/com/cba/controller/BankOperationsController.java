package com.cba.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cba.beans.AccountTransactions;
import com.cba.processing.OperationalService;

@Controller
public class BankOperationsController {
	private static Logger logger=Logger.getLogger(BankOperationsController.class);
	@Autowired
	private OperationalService operationalService;
   @RequestMapping(value="bankOperations",method=RequestMethod.GET)
	public String showBankOperations(){
		return "bankOperations";
	}
   @RequestMapping(value="deposit",method=RequestMethod.GET)
  @ResponseBody
   public String showDeposit(){
  		return "";
  	}
   @RequestMapping(value="checkAccountStatus",method=RequestMethod.GET)
   @ResponseBody
    public String checkAccountStatus(
    		@RequestParam("accountNumber") String accountNumber){
	   return operationalService.checkAccountStatusInfo(accountNumber);
   
   	}
   @RequestMapping(value="deposit",
		   method=RequestMethod.POST)
   @ResponseBody
   public String deposit(
		   @RequestParam("accountNumber") String accountNumber,
		   @RequestParam("txAmount") Double txAmt,
		   @RequestParam("txMode") String txMode,
		   @RequestParam("ddORChequeeNumber") String ddOrChequeeNumber,
		   @RequestParam("ddORChequeeIssuedBy") String issuedBy,
		   @RequestParam("ddORChequeeIssuedDate") String issuedDate,
		   @RequestParam("ddORChequeeIssuedBranch") String issuedBranch
		   ,@RequestParam("txDesc") String txDesc,
		  HttpServletRequest req){
	    if(req.getSession(false)!=null && req.getSession(false).getAttribute("employeeId")!=null && req.getSession(false).getAttribute("userRole")!=null){
	   AccountTransactions accountTransactions=new
			   AccountTransactions();
	   accountTransactions.setAccountNumber(accountNumber);
	   accountTransactions.setTransactionAmount(txAmt);
	   accountTransactions.setDdOrChequeNumber(ddOrChequeeNumber); 
	   accountTransactions.setTransactionMode(txMode);
	   accountTransactions.setIssuedBy(issuedBy);
	   SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy"); 
		 
	   try {
		   if(issuedDate!=null && issuedDate.trim().length()>0){
		accountTransactions.setIssuedDate(dateFormat.parse(issuedDate));
		   }
	} catch (ParseException e) {
logger.error("Exception Occured while converting the Issued Date into Date Type :"+e);		
	}
	   accountTransactions.setTransactionStatus("SUCCESS");
	   accountTransactions.setIssuedBranch(issuedBranch);
	   accountTransactions.setTransactionDesc(txDesc);
	   accountTransactions.setTransactionType("CR");
	   //empId(OR)userId get from session after implementing login
	   accountTransactions.setUpdatedBy((Integer)req.getSession(false).getAttribute("employeeId"));
	   return operationalService.deposit(accountTransactions);
	    }else{
	    	String jsonResponse = "{\"status\":\"SESSION_EXPIRED\",\"message\":\"Your Session is Expired!Please Login\"}";
	    	return jsonResponse;
	    }
	    }
   
  /* @RequestMapping(value="showAccountBalanceWindow",
			  method=RequestMethod.GET) 
	  @ResponseBody
	   public String getAccountBalance(HttpServletRequest req){
	   if(req.getSession(false)!=null && req.getSession(false).getAttribute("employeeId")!=null && req.getSession(false).getAttribute("userRole")!=null){
	  return "";
	   }else{   
				String jsonResponse = "{\"status\":\"SESSION_EXPIRED\",\"message\":\"Your Session is Expired!Please Login\"}";
		    	return jsonResponse;		   
		   }   

   }
*/
   
  @RequestMapping(value="getAccountBalance",
		  method=RequestMethod.GET) 
  @ResponseBody
   public String getAccountBalance(
	@RequestParam("accountNumber") String accountNumber,HttpServletRequest req){
	   if(req.getSession(false)!=null && req.getSession(false).getAttribute("employeeId")!=null && req.getSession(false).getAttribute("userRole")!=null){
	return operationalService.getAccountBalance(accountNumber);
	   }else{
			String jsonResponse = "{\"status\":\"SESSION_EXPIRED\",\"message\":\"Your Session is Expired!Please Login\"}";
	    	return jsonResponse;		   
	   }
	   }
  
}
