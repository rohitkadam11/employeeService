package com.example.reqchallenge.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	@JsonProperty("id")
	public int id;
	
	@JsonProperty("employee_name")
	public String name;
	
	@JsonProperty("employee_salary")
	public int salary;
	
	@JsonProperty("employee_age")
	public int age;
	
	@JsonProperty("profile_image")
	public String profileImage;
	
	public Employee(String name, int salary, int age, String profileImage) {
		super();
		this.name = name;
		this.salary = salary;
		this.age = age;
		this.profileImage = profileImage;
	}
	
	public Employee(String name, int salary, int age, int id) {
		super();
		this.name = name;
		this.salary = salary;
		this.age = age;
		this.id = id;
	}

	public Employee(Map<String, Object> emp) {
		this.id = Integer.parseInt(emp.get("id").toString());
		this.name = emp.get("employee_name").toString();
		this.salary = Integer.parseInt(emp.get("employee_salary").toString());
		this.age = Integer.parseInt(emp.get("employee_age").toString());
		this.profileImage = emp.get("profile_image").toString();
	}

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	
	
	
}
