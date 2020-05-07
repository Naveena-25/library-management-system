package com.capgemini.librarymanagementspringrest.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.capgemini.librarymanagementspringrest.dto.LibraryResponse;
import com.capgemini.librarymanagementspringrest.exception.LMSException;

@RestControllerAdvice
public class ControllerAdvice {
	@ExceptionHandler
	public LibraryResponse exceptionHandler(LMSException lmsException) {
		LibraryResponse response = new LibraryResponse();
		response.setError(true);
		response.setMessage(lmsException.getMessage());

		return response;
	}
}
