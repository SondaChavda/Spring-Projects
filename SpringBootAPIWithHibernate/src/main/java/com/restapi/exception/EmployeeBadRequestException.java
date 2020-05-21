package com.restapi.exception;

public class EmployeeBadRequestException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495512761505011218L;

	public EmployeeBadRequestException(String msg)
	{
		super(msg);
	}
}
