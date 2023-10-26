package com.example.rqchallenge.service;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
//    private static RestTemplate restTemplate;
//	
//	@Mock
//    private RestTemplate employeeRestTemplate;
//
//    @InjectMocks
//    private EmployeeServiceImpl empService = new EmployeeServiceImpl();
//    
//    @LocalServerPort
//    private int serverPort;
//    private String serverUrl = "http://localhost";
//    
//    @BeforeAll
//    public static void init() {
//        restTemplate = new RestTemplate();
//    }
//
//    @BeforeAll
//    public void setUp() {
//    	serverUrl = serverUrl.concat(":").concat(serverPort + "");
//    }
//    
//    @Test
//    void getAllEmployeesTest() throws IOException {
//        doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR)).when(employeeRestTemplate).getForObject(eq("https://dummy.restapiexample.com/api/v1/employees"), eq(Map.class));
//
////        List<Employee> res = empService.getAllEmployees();
//
////        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
//    }
}
