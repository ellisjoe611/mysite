package com.douzone.mysite.exception;

public class UserRepositoryException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UserRepositoryException() {
		super("UserRepositoryException occured...");
	}
	
	public UserRepositoryException(String msg) {
		super(msg);
	}
}
