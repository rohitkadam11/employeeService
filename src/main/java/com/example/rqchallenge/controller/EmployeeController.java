package com.example.rqchallenge.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.service.EmployeeService;

@Component
public class EmployeeController implements IEmployeeController{

	@Autowired 
	EmployeeService employeeService;
	
	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
		// TODO Auto-generated method stub
		try {
			List<Employee> employees = this.employeeService.getAllEmployees();
			if(employees != null) {
				return ResponseEntity.ok(employees);
			}
			return ResponseEntity.internalServerError().build();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
		
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws Exception {
		try {
			List<Employee> filteredEmployees = this.employeeService.getEmployeesByNameSearch(searchString);
			
			if(filteredEmployees == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(filteredEmployees);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) throws Exception{
		try {
			Employee emp = this.employeeService.getEmployeeById(id);
			if(emp == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(emp);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws Exception{
		try {
			int salary = employeeService.getHighestSalaryOfEmployees();
			return ResponseEntity.ok(salary);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws Exception{
		try {
			List<String> empNames = this.employeeService.getTop10HighestEarningEmployeeNames();
			if(empNames == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(empNames);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) throws Exception{
		Employee emp = this.employeeService.createEmployee(employeeInput);
		if(emp == null) {
			return ResponseEntity.noContent().build();
		}
		System.out.println(emp.toString());
		return ResponseEntity.ok(emp);
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) throws Exception{
		String deletedId = employeeService.deleteEmployee(id);
		if(id == null) {
			ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok("Deleted Employee with id: "+deletedId);
	}

}
