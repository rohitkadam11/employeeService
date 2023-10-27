package com.example.rqchallenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
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
		LinkedHashMap<String, Object> employees =new LinkedHashMap<String, Object>();
		employees.put("id", "1");
		employees.put("employee_name", "John");
		employees.put("employee_salary", 36000);
		employees.put("employee_age", 27);
		employees.put("profile_image", "Test");
		
        Employee emp = new Employee(employees);
        Map<String, Object> map = new HashMap<>();
        map.put("data", employees);
        map.put("status", "success");
        when(restTemplate.getForObject(
            "https://dummy.restapiexample.com/api/v1/employee/1", Map.class))
          .thenReturn(map);

        Employee employee = empService.getEmployeeById("1");
        assertEquals(emp, employee);
    }
	
	@Test
	void getEmployeesByNameSearchTest() throws Exception  {
		LinkedHashMap<String, Object> employees =new LinkedHashMap<String, Object>();
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
		when(restTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class)))
				.thenReturn(map);

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

		Employee emp = new Employee("John", 36000, 27, 22,"test");
		List<Employee> allEmp = new ArrayList<>();
		allEmp.add(emp);
		List<Employee> expectedResponse = allEmp;
		Map<String, Object> map = new HashMap<>();
		List<LinkedHashMap<String, Object>> retValue = new ArrayList<>();
		retValue.add(employees);
		map.put("data", retValue);
		when(restTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"),
				eq(Map.class))).thenReturn(map);

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
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(employees);
        map.put("data", retValue);
        when(restTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        int actualResponse = empService.getHighestSalaryOfEmployees();

        assertEquals(actualResponse,expectedResponse);
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
        List<LinkedHashMap<String,Object>> retValue = new ArrayList<>();
        retValue.add(employees);
        map.put("data", retValue);
        when(restTemplate.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class))).thenReturn(map);

        var actualResponse = empService.getTop10HighestEarningEmployeeNames();

        assertEquals(actualResponse, expectedResponse);
    }
	
	@Test
    void createEmployeeTest() throws Exception {
        Map<String, Object> inputEmp = new HashMap<>();
        inputEmp.put("name", "John Snow");
        inputEmp.put("Age", "29");
        inputEmp.put("Salary", "67900");
        inputEmp.put("profileImage", "test");
        
        Map<String,Object> expectedOutput = new HashMap<>();
        expectedOutput.put("name", "John Snow");
        expectedOutput.put("Age", "29");
        expectedOutput.put("Salary", "67900");
        expectedOutput.put("profileImage", "test");
        expectedOutput.put("id", 1);
        HashMap<String,Object> expecteOutput = new HashMap<>();
        expecteOutput.put("status", "success");
        expecteOutput.put("data", expectedOutput);
        when(restTemplate
                .postForObject(eq("https://dummy.restapiexample.com/api/v1/create"), eq(inputEmp),eq(Map.class)))
                .thenReturn(expecteOutput);

        Employee actualResponse = empService.createEmployee(inputEmp);
        assertEquals(expectedOutput.get("name"), actualResponse.getName());
        assertEquals(Integer.parseInt(expectedOutput.get("Age").toString()), actualResponse.getAge());
        assertEquals(Integer.parseInt(expectedOutput.get("Salary").toString()), actualResponse.getSalary());
    }
	
	@Test
    void deleteEmployeeTest() throws Exception {
        doNothing()
                .when(restTemplate)
                .delete(eq("https://dummy.restapiexample.com/api/v1/delete/4"));

        String actualResponse = empService.deleteEmployee("4");
        assertEquals(actualResponse,"4");

    }

}
