package com.restapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restapi.dao.EmployeeDao;
import com.restapi.model.Employee;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDao employeeDao;
	
	public Employee addEmployee(Employee employee) throws Exception
	{
		return employeeDao.addEmployee(employee);
	}
	
	public List<Employee> getAllEmployee()
	{
		return employeeDao.getAllEmployee();
	}
	
	public void removeEmployee(int id) throws Exception
	{
		employeeDao.removeEmployee(id);
	}
	
	public int updateEmployeeDetails(int id,Employee employee) throws Exception
	{
		return employeeDao.updateEmployeeDetails(id, employee);
	}
	
	public List<Employee> searchEmployee(String query)
	{
		if(query.contains("="))
		{
			String column=query.split("=")[0];
			String searchValue=query.split("=")[1];
		
			return employeeDao.searchEmployee(column, searchValue);
		}
		return new ArrayList<Employee>();
	}
	
	public List<Employee> filterSalary(Long min, Long max, String order, Integer startPos, Integer endPos)
	{
		return employeeDao.filterSalary(min, max, order, startPos, endPos);
	}
}
