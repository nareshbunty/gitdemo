package com.happybus.processing.exceptions;

public class UserAuthenticationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

public UserAuthenticationException(){
}
public UserAuthenticationException(String msg){
	super(msg);
}
public UserAuthenticationException(Exception e){
	super(e);
}
}
