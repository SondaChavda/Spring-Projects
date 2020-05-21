package com.restapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobleException extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<EmployeeException> employeeNotFound(Exception e, WebRequest request)
	{
		return new ResponseEntity<>(setErrorEmployeeObject(e,HttpStatus.NOT_FOUND.value()),HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmployeeBadRequestException.class)
	public ResponseEntity<EmployeeException> employeeBadRequest(Exception e)
	{
		return new ResponseEntity<>(setErrorEmployeeObject(e,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
	}
	
	private EmployeeException setErrorEmployeeObject(Exception e,int status) {
		EmployeeException employeeException=new EmployeeException();
		employeeException.setTimeStamp(LocalDateTime.now());
		employeeException.setStatus(status);
		employeeException.setMessage(e.getMessage());
		return employeeException;
	}
	
}
