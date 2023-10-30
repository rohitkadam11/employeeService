package com.example.rqchallenge.entity;

import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

	public Employee(int id, String name, int salary, int age, String profileImage) {
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.age = age;
		this.profileImage = profileImage;
	}

	public Employee() {
		super();
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", age=" + age + ", profileImage="
				+ profileImage + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, id, name, profileImage, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return age == other.age && id == other.id && Objects.equals(name, other.name)
				&& Objects.equals(profileImage, other.profileImage) && salary == other.salary;
	}

}
