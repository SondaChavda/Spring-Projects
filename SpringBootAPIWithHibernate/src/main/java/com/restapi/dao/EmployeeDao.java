package com.restapi.dao;

import java.util.List;

import com.restapi.model.Employee;

public interface EmployeeDao {

	public Employee addEmployee(Employee employee) throws Exception;
	public List<Employee> getAllEmployee();
	public void removeEmployee(int id) throws Exception;
	public int updateEmployeeDetails(int id,Employee employee) throws Exception;
	public List<Employee> searchEmployee(String column,String searchValue);
	public List<Employee> filterSalary(long min, Long max, String order, Integer startPos, Integer endPos);
}
