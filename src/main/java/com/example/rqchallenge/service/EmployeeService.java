package com.example.rqchallenge.service;

import java.util.List;
import java.util.Map;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.exception.EmployeeCustomException;

public interface EmployeeService {
	public List<Employee> getAllEmployees() throws EmployeeCustomException;

	public List<Employee> getEmployeesByNameSearch(String name) throws EmployeeCustomException;

	public Employee getEmployeeById(String id) throws EmployeeCustomException;

	public int getHighestSalaryOfEmployees() throws EmployeeCustomException;

	public List<String> getTop10HighestEarningEmployeeNames() throws EmployeeCustomException;

	public Employee createEmployee(Map<String, Object> employeeInput) throws EmployeeCustomException;

	public String deleteEmployee(String id) throws EmployeeCustomException;
}
