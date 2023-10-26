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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.service.EmployeeService;


@Service
public class EmployeeServiceImpl implements EmployeeService {
	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	String baseURL = "https://dummy.restapiexample.com/api/v1";
	
	@Override
	public List<Employee> getAllEmployees() throws Exception{
		String uri = baseURL + "/employees";
        List<Employee> allEmployees = new ArrayList<>();
        logger.debug("Fetching Employees");
        try {

            Map list = restTemplate.getForObject(uri, Map.class);
            if (list != null) {
                List emps = (List) list.get("data");
                for (Object emp : emps) {
                	allEmployees.add(new Employee((Map)emp));
                }
            }
            return allEmployees;
        } catch (HttpClientErrorException exception) {
            logger.error("Error in reading employees {}", exception.getStatusText());
            throw new Exception("Error in reading employees. "+exception.getStatusText());
        }
	}

	@Override
	public List<Employee> getEmployeesByNameSearch(String name) throws Exception {
		try {
			List<Employee> allEmps = getAllEmployees();
	        
	        List<Employee> filteredEmployees = new ArrayList<>();
	        for(Employee employee : allEmps) {
	            if(employee.getName().contains(name))
	            	filteredEmployees.add(employee);
	        }
	        if(filteredEmployees.isEmpty())
	            return null;

	        return filteredEmployees;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public Employee getEmployeeById(String id) throws Exception{
		String url = baseURL + "/employee/" + id;
        try {
            Map apiResponse = restTemplate.getForObject(url,Map.class);
            if (apiResponse != null && apiResponse.get("status").equals("success")) {
                Employee employee = new Employee((Map<String, Object>) apiResponse.get("data"));
                return employee;
            }
        } catch (RestClientException exception) {
            logger.error(exception.getMessage());
            throw new Exception("Unable to get data "+exception.getMessage());
        }
        return null;
	}

	@Override
	public int getHighestSalaryOfEmployees() throws Exception{
		// TODO Auto-generated method stub
		try {
			List<Employee> allEmployees = getAllEmployees();
			if(allEmployees == null) {
				return 0;
			}
			Employee emp2 = allEmployees
				    .stream()
				    .max((p1, p2) -> Integer.compare(p1.getSalary(), p2.getSalary()))
				    .get();
			return emp2.getSalary();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<String> getTop10HighestEarningEmployeeNames() throws Exception {
		try {
			List<Employee> allEmployees = getAllEmployees();
			if(allEmployees == null) {
				return null;
			}
			Set<Employee> sortedSet = new TreeSet<>(Comparator.comparingInt(Employee::getSalary));
	        sortedSet.addAll(allEmployees);
	        List<Employee> top10Emps = sortedSet.stream().limit(10).collect(Collectors.toList());
	        if(top10Emps.isEmpty()) {
	        	return null;
	        }
	        List<String> top10EmpsNames = new ArrayList<>();
	        for(Employee employee : top10Emps) {
	        	top10EmpsNames.add(employee.getName());
	        }
	        return top10EmpsNames;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public Employee createEmployee(Map<String, Object> employeeInput) throws Exception {
		String url = baseURL + "/create";
        try {
            Map response = restTemplate.postForObject(url, employeeInput, Map.class);
            if (response != null && response.get("status").equals("success")) {
            	Map<String,Object> respObj = (Map<String, Object>) response.get("data");
                Employee emp = new Employee(respObj.get("name").toString(),Integer.parseInt(respObj.get("Salary").toString()),
                		Integer.parseInt(respObj.get("Age").toString()),Integer.parseInt(respObj.get("id").toString()));
                return emp;
            } else {
            	return null;
            }
        } catch (RestClientException ex) {
            logger.error(ex.getMessage());
            throw new RestClientException(ex.getMessage());
        }
	}

	@Override
	public String deleteEmployee(String id) throws Exception {
		 String url = baseURL + "/delete/"+id;
	        try {
	            restTemplate.delete(url);
	            return id;
	        }
	        catch (RestClientException exception) {
	            logger.error(exception.getMessage());
	            throw new Exception(exception.getMessage());
	        }
	}
	
}
