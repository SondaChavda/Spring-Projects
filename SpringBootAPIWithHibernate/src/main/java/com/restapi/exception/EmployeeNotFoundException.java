package com.restapi.exception;

public class EmployeeNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8537053969609915997L;

	public EmployeeNotFoundException(String msg)
	{
		super(msg);
	}
}
