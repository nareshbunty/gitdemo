<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>      
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item Register</title>
</head>
<body>
<h1>Welcome to Item Register Page!!</h1>
<form:form action="insertItem" method="post" modelAttribute="item">
<pre>
Item Code : <form:input path="itemCode"/>
<form:errors path="itemCode" cssClass="error"/>
Item Dimensions : 
	Width : <form:input path="itemWdth" />
	<form:errors path="itemWdth" cssClass="error"/>
    Length : <form:input path="itemLen"/> 
    <form:errors path="itemLen" cssClass="error"/>
    Height : <form:input path="itemheit"/>
    <form:errors path="itemheit" cssClass="error"/>
Base Cost : <form:input path="baseCost"/>
<form:errors path="baseCost" cssClass="error"/>
Base Currency : <form:select path="baseCurrency">
					<form:option value="">--select--</form:option>
					<form:options items="${baseCurrencies}"/>
				</form:select>
<form:errors path="baseCurrency" cssClass="error"/>				
Item UOM : <form:select path="itemUom">
			<form:option value="">--select--</form:option>
			<form:options items="${itemUoms}" itemLabel="uomModel" itemValue="uomId"/>
		   </form:select>
<form:errors path="itemUom" cssClass="error"/>
Order Methods (Sale Type ): 
		      <form:select path="itemSaleOrdMthd">
			    <form:option value="">--select--</form:option>
			   <form:options items="${itemSaleOrdMthds}" itemLabel="orderCode" itemValue="orderMethodId"/>
		      </form:select>	
<form:errors path="itemSaleOrdMthd" cssClass="error"/>
Order Methods (Purchase Type ):
			  <form:select path="itemPurchaseOrdMthd">
			    <form:option value="">--select--</form:option>
			    <form:options items="${itemPurchaseOrdMthds}" itemLabel="orderCode" itemValue="orderMethodId"/>
		      </form:select>
<form:errors path="itemPurchaseOrdMthd" cssClass="error"/>
Item Vendors :<form:select path="itemVendors">
			    <form:option value="">--select--</form:option>
			   <form:options items="${itemVendorsList}" itemLabel="whUserCode" itemValue="whUserTypeId"/>
		      </form:select>
<form:errors path="itemVendors" cssClass="error"/>		      
Item Customers:<form:select path="itemCustomers">
			    <form:option value="">--select--</form:option>
			    <form:options items="${itemCustomersList}" itemLabel="whUserCode" itemValue="whUserTypeId"/>
		      </form:select>
<form:errors path="itemCustomers" cssClass="error"/>		      
Description : <form:textarea path="description"/>	
<form:errors path="description" cssClass="error"/>
<input type="submit" value="Create Item"/>	      		      			                 
</pre>
</form:form>
<c:if test="${!empty message}">
<c:out value="${message}"/>
</c:if>
</body>
</html>