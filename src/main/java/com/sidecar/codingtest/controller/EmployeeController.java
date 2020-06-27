package com.sidecar.codingtest.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sidecar.codingtest.VO.EmployeeVO;
import com.sidecar.codingtest.service.EmployeeSevice;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeSevice employeeSevice;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	
	@GetMapping("/employees")
	public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
		LOGGER.info("getAllEmployees in EmployeeController");
		//Empty content situations need to be handled here (no content in DB) 
		List<EmployeeVO> employees = employeeSevice.getAllEmployees();
		return new ResponseEntity<List<EmployeeVO>>(employees, HttpStatus.OK);
	}

	@RequestMapping("/employee/{id}")
	public ResponseEntity<EmployeeVO> getEmployee(@PathVariable int id) throws NoSuchElementException {
		LOGGER.info("getEmployee in EmployeeController");
		EmployeeVO empVo = employeeSevice.getEmployee(id);
		return new ResponseEntity<EmployeeVO>(empVo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/employee")
	public ResponseEntity<Integer> addEmployee(@RequestBody EmployeeVO employeeVo) {
		LOGGER.info("addEmployee in EmployeeController");
		EmployeeVO empVo = employeeSevice.addEmployee(employeeVo);
		return new ResponseEntity<>(empVo.getId(), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/employee/{id}")
	public ResponseEntity<EmployeeVO> updateEmployee(@PathVariable int id, @RequestBody EmployeeVO employeevo) {
		LOGGER.info("updateEmployee in EmployeeController");
		EmployeeVO empvVo = employeeSevice.updateEmployee(id, employeevo);
		return new ResponseEntity<EmployeeVO>(empvVo, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/employee/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
		LOGGER.info("deleteEmployee in EmployeeController");
		employeeSevice.deleteEmployee(id);
		return new ResponseEntity<String>("Deleted Employee Details", HttpStatus.OK);
	}

}
