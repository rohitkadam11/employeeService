package com.example.rqchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
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

import com.example.rqchallenge.controller.EmployeeController;
import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RqChallengeApplicationTests {
	@LocalServerPort
	private static int serverPort;
	private static String serverUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Mock
	RestTemplate restTemplateForEmployee;

	@InjectMocks
	private EmployeeController employeeController;
	
	@Mock
    private EmployeeService employeeService;

	@BeforeAll
	public static void init() {
		restTemplate = new RestTemplate();
	}

	@BeforeAll
	public static void setUp() {
		serverUrl = serverUrl.concat(":").concat(serverPort + "");
	}

//	@Test
//	void contextLoads() {
//	}

//	@Test
//	void getAllEmployeesFailedtest() throws IOException {
//		doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(restTemplateForEmployee)
//				.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));
//
//		ResponseEntity<List<Employee>> res = employeeController.getAllEmployees();
//
//		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
//	}

//	@Test
//	void getAllEmployeesTest() throws IOException {
//		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
//		employees.put("id", 22);
//		employees.put("employee_name", "John");
//		employees.put("employee_salary", 36000);
//		employees.put("employee_age", 27);
//		employees.put("profile_image", "");
//
//		Employee emp = new Employee("John", 36000, 27, 22);
//		List<Employee> allEmp = new ArrayList();
//		allEmp.add(emp);
//		ResponseEntity<List<Employee>> expectedResponse = ResponseEntity.ok(allEmp);
//		Map<String, Object> map = new HashMap<>();
//		List<LinkedHashMap<String, Object>> retValue = new ArrayList<>();
//		retValue.add(employees);
//		map.put("data", retValue);
//		when(restTemplateForEmployee.getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"),
//				eq(Map.class))).thenReturn(map);
//
//		var response = employeeController.getAllEmployees();
//
//		assertEquals(response, expectedResponse);
//	}
	
//	@Test
//    void getEmployeeByIdTest() throws Exception {
//		LinkedHashMap<String, Object> employees = new LinkedHashMap<String, Object>();
//		employees.put("id", "1");
//		employees.put("employee_name", "John");
//		employees.put("employee_salary", 36000);
//		employees.put("employee_age", 27);
//		employees.put("profile_image", "");
//
//        Employee emp = new Employee(employees);
//
//
//        ResponseEntity<Employee> expectedResponse = ResponseEntity.ok(emp);
//        Map<String, Object> map = new HashMap<>();
//        map.put("data", employees);
//        map.put("status", "success");
//        when(restTemplateForEmployee.getForObject(eq("https://dummy.restapiexample.com/api/v1/employee/1"), eq(Map.class))).thenReturn(map);
//
//        var response = employeeController.getEmployeeById("1");
//
//        assertEquals( expectedResponse,response);
//    }

}
