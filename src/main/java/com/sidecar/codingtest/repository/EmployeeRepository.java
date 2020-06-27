package com.sidecar.codingtest.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

import com.sidecar.codingtest.model.Employee;


public interface EmployeeRepository extends CrudRepository<Employee, Serializable>{
	
	
}
