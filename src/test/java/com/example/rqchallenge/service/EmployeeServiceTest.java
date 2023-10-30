package com.example.rqchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private EmployeeServiceImpl empService = new EmployeeServiceImpl();

	@Test
	public void getEmployeeByIdTest() throws Exception {
		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
		employees.put("id", "1");
		employees.put("employee_name", "John");
		employees.put("employee_salary", 36000);
		employees.put("employee_age", 27);
		employees.put("profile_image", "Test");

		Employee emp = new Employee(employees);
		Map<String, Object> map = new HashMap<>();
		map.put("data", employees);
		map.put("status", "success");

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(map);

		Employee employee = empService.getEmployeeById("1");
		assertEquals(emp, employee);
	}

	@Test
	void getEmployeesByNameSearchTest() throws Exception {
		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
		employees.put("id", "1");
		employees.put("employee_name", "John");
		employees.put("employee_salary", 36000);
		employees.put("employee_age", 27);
		employees.put("profile_image", "Test");

		Employee emp = new Employee(employees);
		List<Employee> list = new ArrayList<>();
		list.add(emp);
		List<Employee> expectedResponse = list;
		Map<String, Object> map = new HashMap<>();
		List<LinkedHashMap<String, Object>> retValue = new ArrayList<>();
		retValue.add(employees);
		map.put("data", retValue);

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(map);

		List<Employee> actualResponse = empService.getEmployeesByNameSearch("John");
		assertEquals(actualResponse, expectedResponse);
	}

	@Test
	void getAllEmployeesTest() throws Exception {
		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
		employees.put("id", 22);
		employees.put("employee_name", "John");
		employees.put("employee_salary", 36000);
		employees.put("employee_age", 27);
		employees.put("profile_image", "test");

		Employee emp = new Employee(22, "John", 36000, 27, "test");

		List<Employee> allEmp = new ArrayList<>();
		allEmp.add(emp);
		List<Employee> expectedResponse = allEmp;
		Map<String, Object> map = new HashMap<>();
		List<LinkedHashMap<String, Object>> retValue = new ArrayList<>();
		retValue.add(employees);
		map.put("data", retValue);

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(map);

		var actualResponse = empService.getAllEmployees();
		assertEquals(actualResponse, expectedResponse);
	}

	@Test
	void getHighestSalaryOfEmpTest() throws Exception {
		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
		employees.put("id", 22);
		employees.put("employee_name", "John");
		employees.put("employee_salary", 36000);
		employees.put("employee_age", 27);
		employees.put("profile_image", "test");

		Employee emp = new Employee(employees);

		int expectedResponse = emp.getSalary();
		Map<String, Object> map = new HashMap<>();
		List<LinkedHashMap<String, Object>> retValue = new ArrayList<>();
		retValue.add(employees);
		map.put("data", retValue);

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(map);

		int actualResponse = empService.getHighestSalaryOfEmployees();
		assertEquals(actualResponse, expectedResponse);
	}

	@Test
	void getTopTenHighestEarningEmployeeNamesTest() throws Exception {
		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
		employees.put("id", 22);
		employees.put("employee_name", "John");
		employees.put("employee_salary", 36000);
		employees.put("employee_age", 27);
		employees.put("profile_image", "test");

		Employee employee = new Employee(employees);

		List<String> expectedResponse = Arrays.asList(employee.getName());
		Map<String, Object> map = new HashMap<>();
		List<LinkedHashMap<String, Object>> retValue = new ArrayList<>();
		retValue.add(employees);
		map.put("data", retValue);

		when(restTemplate.getForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(map);

		var actualResponse = empService.getTop10HighestEarningEmployeeNames();
		assertEquals(actualResponse, expectedResponse);
	}

	@Test
	void createEmployeeTest() throws Exception {
		Map<String, Object> inputEmp = new HashMap<>();
		inputEmp.put("name", "John Snow");
		inputEmp.put("age", "29");
		inputEmp.put("salary", "67900");
		inputEmp.put("profileImage", "test");

		Map<String, Object> expectedOutput = new HashMap<>();
		expectedOutput.put("name", "John Snow");
		expectedOutput.put("age", "29");
		expectedOutput.put("salary", "67900");
		expectedOutput.put("profileImage", "test");
		expectedOutput.put("id", 1);
		HashMap<String, Object> map = new HashMap<>();
		map.put("status", "success");
		map.put("data", expectedOutput);

		when(restTemplate.postForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(map);

		Employee actualResponse = empService.createEmployee(inputEmp);
		assertEquals(expectedOutput.get("name"), actualResponse.getName());
		assertEquals(Integer.parseInt(expectedOutput.get("age").toString()), actualResponse.getAge());
		assertEquals(Integer.parseInt(expectedOutput.get("salary").toString()), actualResponse.getSalary());
	}

	@Test
	void deleteEmployeeTest() throws Exception {
		doNothing().when(restTemplate).delete(ArgumentMatchers.anyString());
		String actualResponse = empService.deleteEmployee("4");
		assertEquals(actualResponse, "4");
	}

}
