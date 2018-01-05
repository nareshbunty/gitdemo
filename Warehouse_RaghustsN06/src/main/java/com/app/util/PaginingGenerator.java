package com.app.util;

public class PaginingGenerator {

	public String getPaginationCode(String pageObjName,String getAllUrl){
		StringBuffer sb=new StringBuffer();
		sb.append("<c:if test=\"${!").append(pageObjName).append(".isFirst()}\">").append("\n")
		.append("<a href=\"").append(getAllUrl).append("?page=0\">First</a>&nbsp;</c:if>").append("\n")
		.append("<c:if test=\"${").append(pageObjName).append(".hasPrevious()}\">").append("\n")
		.append("<a href=\"").append(getAllUrl).append("?page=${").append(pageObjName).append(".getNumber()-1}\">Previous</a>&nbsp;</c:if>").append("\n")
		.append("<c:forEach begin=\"0\" end=\"${").append(pageObjName).append(".getTotalPages()-1}\" var=\"i\">").append("\n")
		.append("<c:choose>").append("\n")
		.append("<c:when test=\"${").append(pageObjName).append(".getNumber() eq i}\">").append("\n")
		.append("<c:out value=\"${i+1}\"/>&nbsp;").append("\n")
		.append("</c:when>").append("\n")
		.append("<c:otherwise>").append("\n")
		.append("<a href=\"").append(getAllUrl).append("?page=${i}\"><c:out value=\"${i+1}\"/></a>&nbsp;").append("\n")
		.append("</c:otherwise>").append("\n")
		.append("</c:choose>").append("\n")
		.append("</c:forEach>").append("\n")

		.append("<c:if test=\"${").append(pageObjName).append(".hasNext()}\">").append("\n")
		.append("<a href=\"").append(getAllUrl).append("?page=${").append(pageObjName).append(".getNumber()+1}\">Next</a>&nbsp;</c:if>").append("\n")


		.append("<c:if test=\"${!").append(pageObjName).append(".isLast()}\">").append("\n")
		.append("<a href=\"").append(getAllUrl).append("?page=${").append(pageObjName).append(".getTotalPages()-1}\">Last</a>&nbsp;</c:if>").append("\n");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(new PaginingGenerator().getPaginationCode("uoms", "getAllUoms"));
	}
	
	
}
