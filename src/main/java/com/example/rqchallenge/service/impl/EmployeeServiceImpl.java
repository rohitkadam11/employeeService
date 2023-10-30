package com.example.rqchallenge.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.exception.EmployeeCustomException;
import com.example.rqchallenge.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private final int TOO_MANY_CLIENTS = 429;
	private final int INTERNAL_SERVER_ERROR = 500;
	private final int BAD_REQUEST = 400;
	private final int SUCCESS = 200;
	private final int JSON_PARSE_ERROR = 422;
	@Autowired
	private RestTemplate restTemplate;

	String baseURL = "https://dummy.restapiexample.com/api/v1";

	@Override
	public List<Employee> getAllEmployees() throws EmployeeCustomException {
		String uri = baseURL + "/employees";
		List<Employee> allEmployees = new ArrayList<>();
		logger.debug("Fetching Employees");
		try {

			Map list = restTemplate.getForObject(uri, Map.class);
			if (list != null) {
				List emps = (List) list.get("data");
				for (Object emp : emps) {
					allEmployees.add(new Employee((Map) emp));
				}
			}
			return allEmployees;
		} catch (HttpClientErrorException exception) {
			logger.error("Error in reading employees {}", exception.getStatusText());
			switch (exception.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", exception.getRawStatusCode(),
						"Error While Fetching Employees");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", exception.getRawStatusCode(),
						"Error While Fetching Employees");
			case BAD_REQUEST:
				throw new EmployeeCustomException("Bad Request", exception.getRawStatusCode(),
						"Error While Fetching Employees");
			default:
				throw new EmployeeCustomException("Something Went Wrong", exception.getRawStatusCode(),
						"Error While Fetching Employees");
			}
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Process Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public List<Employee> getEmployeesByNameSearch(String name) throws EmployeeCustomException {
		try {
			List<Employee> allEmps = getAllEmployees();

			List<Employee> filteredEmployees = new ArrayList<>();
			for (Employee employee : allEmps) {
				if (employee.getName().contains(name))
					filteredEmployees.add(employee);
			}
			if (filteredEmployees.isEmpty())
				throw new EmployeeCustomException("Sucess", SUCCESS, "No Matching Results Found");

			return filteredEmployees;
		} catch (HttpClientErrorException e) {
			logger.error("Error in reading employees {}", e.getStatusText());
			switch (e.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", e.getRawStatusCode(), "Cannot Search Employee");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", e.getRawStatusCode(),
						"Cannot Search Employee");
			default:
				throw new EmployeeCustomException("Something Went Wrong", e.getRawStatusCode(),
						"Cannot Search Employee");
			}
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Process Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}

	}

	@Override
	public Employee getEmployeeById(String id) throws EmployeeCustomException {
		String url = baseURL + "/employee/" + id;
		Employee employee = null;
		try {
			Map apiResponse = restTemplate.getForObject(url, Map.class);
			if (apiResponse != null && apiResponse.get("status").equals("success") && apiResponse.get("data") != null) {
				ObjectMapper objMapper = new ObjectMapper();
				String json = new ObjectMapper().writeValueAsString(apiResponse.get("data"));
				employee = objMapper.readValue(json, Employee.class);
			}
		} catch (HttpClientErrorException e) {
			logger.error("Error in reading employees {}", e.getStatusText());
			switch (e.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", e.getRawStatusCode(), "Cannot Get Employee");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", e.getRawStatusCode(), "Cannot Get Employee");
			default:
				throw new EmployeeCustomException("Something Went Wrong", e.getRawStatusCode(), "Cannot Get Employee");
			}
		} catch (JsonProcessingException e) {
			// TODO: handle exception
			throw new EmployeeCustomException("Enable To Process Entity", JSON_PARSE_ERROR, e.getLocalizedMessage());
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Process Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}
		if (employee == null) {
			throw new EmployeeCustomException("Sucess", SUCCESS, "No Employee Found");
		} else {
			return employee;
		}
	}

	@Override
	public int getHighestSalaryOfEmployees() throws EmployeeCustomException {
		// TODO Auto-generated method stub
		try {
			List<Employee> allEmployees = getAllEmployees();
			if (allEmployees == null) {
				return 0;
			}
			Employee emp2 = allEmployees.stream().max((p1, p2) -> Integer.compare(p1.getSalary(), p2.getSalary()))
					.get();
			return emp2.getSalary();
		} catch (HttpClientErrorException e) {
			logger.error("Error in reading employees {}", e.getStatusText());
			switch (e.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", e.getRawStatusCode(),
						"Cannot Get Employee Highest Salary");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", e.getRawStatusCode(),
						"Cannot Get Employee Highest Salary");
			default:
				throw new EmployeeCustomException("Something Went Wrong", e.getRawStatusCode(),
						"Cannot Get Employee Highest Salary");
			}
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Process Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public List<String> getTop10HighestEarningEmployeeNames() throws EmployeeCustomException {
		try {
			List<Employee> allEmployees = getAllEmployees();
			if (allEmployees == null) {
				return null;
			}
			Set<Employee> sortedSet = new TreeSet<>(Comparator.comparingInt(Employee::getSalary));
			sortedSet.addAll(allEmployees);
			List<Employee> top10Emps = sortedSet.stream().limit(10).collect(Collectors.toList());
			if (top10Emps.isEmpty()) {
				return null;
			}
			List<String> top10EmpsNames = new ArrayList<>();
			for (Employee employee : top10Emps) {
				top10EmpsNames.add(employee.getName());
			}
			return top10EmpsNames;
		} catch (HttpClientErrorException e) {
			logger.error("Error in reading employees {}", e.getStatusText());
			switch (e.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", e.getRawStatusCode(),
						"Cannot Get Top 10 Employee Highest Salary");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", e.getRawStatusCode(),
						"Cannot Get Top 10 Employee Highest Salary");
			default:
				throw new EmployeeCustomException("Something Went Wrong", e.getRawStatusCode(),
						"Cannot Get Top 10 Employee Highest Salary");
			}
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Process Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public Employee createEmployee(Map<String, Object> employeeInput) throws EmployeeCustomException {
		String url = baseURL + "/create";
		try {
			Map response = restTemplate.postForObject(url, employeeInput, Map.class);
			if (response != null && response.get("status").equals("success")) {
				Map<String, Object> respObj = (Map<String, Object>) response.get("data");
				Employee emp = new Employee(respObj.get("name").toString(),
						Integer.parseInt(respObj.get("salary").toString()),
						Integer.parseInt(respObj.get("age").toString()),
						Integer.parseInt(respObj.get("id").toString()));
				return emp;
			} else {
				return null;
			}
		} catch (HttpClientErrorException e) {
			logger.error("Error in reading employees {}", e.getStatusText());
			switch (e.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", e.getRawStatusCode(), "Cannot Create Employee");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", e.getRawStatusCode(),
						"Cannot Create Employee");
			default:
				throw new EmployeeCustomException("Something Went Wrong", e.getRawStatusCode(),
						"Cannot Create Employee");
			}
		} catch (NullPointerException e) {
			logger.error("Error in creating employees {}", e.getMessage());
			throw new EmployeeCustomException(e.getMessage(), INTERNAL_SERVER_ERROR, "Cannot Create Employee");
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Process Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

	@Override
	public String deleteEmployee(String id) throws EmployeeCustomException {
		String url = baseURL + "/delete/" + id;
		try {
			restTemplate.delete(url);
			return id;
		} catch (HttpClientErrorException e) {
			logger.error("Error in Deleting employee {}", e.getStatusText());
			switch (e.getRawStatusCode()) {
			case TOO_MANY_CLIENTS:
				throw new EmployeeCustomException("Too Many Requests", e.getRawStatusCode(), "Cannot Delete Employee");
			case INTERNAL_SERVER_ERROR:
				throw new EmployeeCustomException("Internal Server Error", e.getRawStatusCode(),
						"Cannot Delete Employee");
			default:
				throw new EmployeeCustomException("Something Went Wrong", e.getRawStatusCode(),
						"Cannot Delete Employee");
			}
		} catch (Exception e) {
			throw new EmployeeCustomException("Enable To Delete Entity", INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
