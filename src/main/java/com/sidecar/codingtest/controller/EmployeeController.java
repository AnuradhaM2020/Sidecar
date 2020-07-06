package com.sidecar.codingtest.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sidecar.codingtest.VO.EmployeeVO;
import com.sidecar.codingtest.configuration.CustomResponseEntity;
import com.sidecar.codingtest.service.EmployeeSevice;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	
	@Autowired
	EmployeeSevice employeeSevice;
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@Cacheable(value = "employees")
	@ApiOperation(value = "Get all the employees details")
	@GetMapping("/employees")
	public CustomResponseEntity<List<EmployeeVO>> getAllEmployees() {
		
		LOGGER.info("getAllEmployees in EmployeeController");
		List<EmployeeVO> employees = employeeSevice.getAllEmployees();
		if(employees.isEmpty()) {
			return new CustomResponseEntity<List<EmployeeVO>>(employees, HttpStatus.NO_CONTENT);
		}
		
		return new CustomResponseEntity<List<EmployeeVO>>(employees, HttpStatus.OK);
	}
	@Cacheable(value = "employees", key = "#id")
	@ApiOperation(value = "/employee/{id}", nickname = "Get the employee details")
	@RequestMapping("/employee/{id}")
	public CustomResponseEntity<EmployeeVO> getEmployee(@PathVariable int id) throws NoSuchElementException {
		LOGGER.info("getEmployee in EmployeeController");
		EmployeeVO empVo = employeeSevice.getEmployee(id);
		
		return new CustomResponseEntity<EmployeeVO>(empVo, HttpStatus.OK);
	}
	
	@CachePut(value = "employees", key="#employeeVo.id")
	@ApiOperation(value = "/employee")
	@ApiImplicitParam(
	        name = "employeeVo",
	        dataType = "EmployeeVO")
	@RequestMapping(method = RequestMethod.POST, value = "/employee")
	public CustomResponseEntity<String> addEmployee(@RequestBody EmployeeVO employeeVo) {
		LOGGER.info("addEmployee in EmployeeController");
		employeeSevice.addEmployee(employeeVo);
		return new CustomResponseEntity<>("Employee Created Successfully", HttpStatus.CREATED);
	}
	
	@CachePut(value = "employees", key = "#employeevo.id")
	@ApiOperation(value = "/employee")
	@RequestMapping(method = RequestMethod.PUT, value = "/employee/{id}")
	public CustomResponseEntity<EmployeeVO> updateEmployee(@PathVariable int id, @RequestBody EmployeeVO employeevo) {
		LOGGER.info("updateEmployee in EmployeeController");
		EmployeeVO empvVo = employeeSevice.updateEmployee(id, employeevo);
		return new CustomResponseEntity<EmployeeVO>(empvVo, HttpStatus.OK);
	}
	
	@CacheEvict(value = "employees", key ="#id")
	@ApiOperation(value = "/employee/{id}")
	@RequestMapping(method = RequestMethod.DELETE, value = "/employee/{id}")
	public CustomResponseEntity<String> deleteEmployee(@PathVariable int id) {
		LOGGER.info("deleteEmployee in EmployeeController");
		employeeSevice.deleteEmployee(id);
		return new CustomResponseEntity<String>("Employee details deleted successfully", HttpStatus.OK);
	}
	
	/*
	 * @CacheEvict(value = "employees", allEntries = true)
	 * 
	 * @RequestMapping("/clearcache") public String clearCache(){ return
	 * "Cache is Cleared" ; }
	 */

}
