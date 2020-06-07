package com.capgemini.librarymanagementspringrest.exception;

@SuppressWarnings("serial")
public class LMSException extends RuntimeException{
	public LMSException(String msg) {
		super(msg);
	}
}
