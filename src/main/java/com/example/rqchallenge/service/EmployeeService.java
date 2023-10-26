package com.example.rqchallenge.service;

import java.util.List;
import java.util.Map;

import com.example.reqchallenge.entity.Employee;


public interface EmployeeService {
	public List<Employee> getAllEmployees() throws Exception;
	
	public List<Employee> getEmployeesByNameSearch(String name) throws Exception;
	
	public Employee getEmployeeById(String  id) throws Exception;
	
	public int getHighestSalaryOfEmployees() throws Exception;
	
	public List<String> getTop10HighestEarningEmployeeNames() throws Exception;
	
	public Employee createEmployee(Map<String, Object> employeeInput) throws Exception;
	
	public String deleteEmployee(String id) throws Exception;
}
