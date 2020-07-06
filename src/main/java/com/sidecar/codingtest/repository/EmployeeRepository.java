package com.sidecar.codingtest.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.sidecar.codingtest.model.Employee;
import com.sidecar.codingtest.model.UserEntity;


public interface EmployeeRepository extends CrudRepository<Employee, Serializable>{
	Employee findByFirstname(String firstname);
	
}
