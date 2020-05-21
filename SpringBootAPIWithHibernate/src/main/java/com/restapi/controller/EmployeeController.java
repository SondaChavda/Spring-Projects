package com.restapi.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.restapi.exception.EmployeeBadRequestException;
import com.restapi.exception.EmployeeNotFoundException;
import com.restapi.model.Employee;
import com.restapi.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	
	@RequestMapping("/display")
	public String startService()
	{
		return "Appliaction is up....";
	}
	
	@RequestMapping(value="/addEmployee",method=RequestMethod.POST)
	@Consumes("application/json")
	@Produces("application/json")
	public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) throws EmployeeBadRequestException
	{
		Employee employee2;
		int empID=0;
		if(employee.getName().isEmpty())
			throw new EmployeeBadRequestException("Employee name not valid");
		if(!"Male".equalsIgnoreCase(employee.getGender()) && !"Female".equalsIgnoreCase(employee.getGender()))
			throw new EmployeeBadRequestException("Employee gender not valid");
		if(employee.getSalary() <= 0)
			throw new EmployeeBadRequestException("Employee salary not valid");
		
		try {
			employee2 = employeeService.addEmployee(employee);
			empID=employee2.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new EmployeeBadRequestException("Employee not valid");
		}
		
		return new ResponseEntity<Object>("Employee successfully added :"+empID,HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/employees",method=RequestMethod.GET)
	@Produces("application/json")
	public ResponseEntity<List<Employee>> getAllEmployee()
	{
		List<Employee> employees=employeeService.getAllEmployee();
		
		return new ResponseEntity<List<Employee>>(employees,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) throws EmployeeNotFoundException
	{
		try {
			employeeService.removeEmployee(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmployeeNotFoundException("Employee not found : "+ id);
		}
		
		return  new ResponseEntity<Object>("Employee removed Id :"+id,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	@Consumes("application/json")
	public  ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) throws EmployeeNotFoundException
	{
		int updateCount=0;
		try
		{
			 updateCount=employeeService.updateEmployeeDetails(id, employee);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			throw new EmployeeNotFoundException(e.getMessage());
		}
		
		return new ResponseEntity<Object>("Employee updated successfully: "+updateCount,HttpStatus.OK);
	}
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	@Produces("application/json")
	public  ResponseEntity<List<Employee>> searchEmployee(@RequestParam(value="query",required=true) String query)
	{
		List<Employee> employees=employeeService.searchEmployee(query);
		
		return new ResponseEntity<List<Employee>>(employees,HttpStatus.OK);
	}

	@RequestMapping(value="/filterSalary",method=RequestMethod.GET)
	public ResponseEntity<List<Employee>> filterSalary(@RequestParam(value="min",defaultValue="0",required=false) Long min,@RequestParam(value="max",defaultValue="0",required=false) Long max,@RequestParam(value="Order",defaultValue="Asc") String order,@RequestParam(value="startPos",defaultValue="0",required=false) Integer startPos,@RequestParam(value="endPos",defaultValue="0",required=false) Integer endPos)
	{
		List<Employee> employees=employeeService.filterSalary(min, max, order, startPos, endPos);
		return new ResponseEntity<List<Employee>>(employees,HttpStatus.OK);
	}
	
}
