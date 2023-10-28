package com.example.rqchallenge.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.exception.EmployeeCustomException;
import com.example.rqchallenge.service.EmployeeService;

@Component
public class EmployeeController implements IEmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Override
	public ResponseEntity<List<Employee>> getAllEmployees() throws EmployeeCustomException {
		// TODO Auto-generated method stub
		try {
			List<Employee> employees = this.employeeService.getAllEmployees();
			if (employees != null) {
				return ResponseEntity.ok(employees);
			}
			return ResponseEntity.internalServerError().build();
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

	@Override
	public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws EmployeeCustomException {
		try {
			List<Employee> filteredEmployees = this.employeeService.getEmployeesByNameSearch(searchString);

			if (filteredEmployees == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(filteredEmployees);
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(String id) throws Exception {
		try {
			Employee emp = this.employeeService.getEmployeeById(id);
			if (emp == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(emp);
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

	@Override
	public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws Exception {
		try {
			int salary = employeeService.getHighestSalaryOfEmployees();
			return ResponseEntity.ok(salary);
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

	@Override
	public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws Exception {
		try {
			List<String> empNames = this.employeeService.getTop10HighestEarningEmployeeNames();
			if (empNames == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(empNames);
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

	@Override
	public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) throws Exception {
		try {
			Employee emp = this.employeeService.createEmployee(employeeInput);
			if (emp == null) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(emp);
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

	@Override
	public ResponseEntity<String> deleteEmployeeById(String id) throws Exception {
		try {
			String deletedId = employeeService.deleteEmployee(id);
			if (id == null) {
				ResponseEntity.internalServerError().build();
			}
			return ResponseEntity.ok(deletedId);
		} catch (EmployeeCustomException e) {
			throw new EmployeeCustomException(e.getMessage(), e.getErrorCode(), e.getErrorDescrption());
		}
	}

}
