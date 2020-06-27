package com.sidecar.codingtest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sidecar.codingtest.VO.EmployeeVO;
import com.sidecar.codingtest.model.Employee;
import com.sidecar.codingtest.repository.EmployeeRepository;

@Service
public class EmployeeSevice {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public List<EmployeeVO> getAllEmployees(){
		LOGGER.info("getAllEmployees in EmployeeSevice");
		List<Employee> employees = new ArrayList<Employee>();
		 employeeRepository.findAll().forEach(employees::add);
		 	return  employees.stream().
					map(this::convertToVO).
					collect(Collectors.toList());
	}
	
	
	  public EmployeeVO getEmployee(int id){
		  LOGGER.info("getEmployee in EmployeeSevice");
		  Employee employee = employeeRepository.findById(id).get();
			  return  convertToVO(employee);
		  }
	 
	
	public EmployeeVO addEmployee(EmployeeVO  employeeVo){
		  LOGGER.info("addEmployee in EmployeeSevice");
		Employee employee = convertToEntity(employeeVo);
		employee = employeeRepository.save(employee);
		EmployeeVO empVo = convertToVO(employee);
		return empVo;
	}
	
	public EmployeeVO updateEmployee(int id, EmployeeVO employeevo){
		  LOGGER.info("updateEmployee in EmployeeSevice");
		Employee employee = convertToEntity(employeevo);
		employeeRepository.save(employee);
		EmployeeVO empVo = convertToVO(employee);
		return empVo;
	}
	
	public void deleteEmployee(int id){
		  LOGGER.info("deleteEmployee in EmployeeSevice");
		employeeRepository.deleteById(id);
	}
	
	private Employee convertToEntity(EmployeeVO empVo) {
		Employee employee = new Employee();
		employee.setId(empVo.getId());
		employee.setFirstname(empVo.getFirstname());
		employee.setLastname(empVo.getLastname());
		employee.setGender(empVo.getGender());
		employee.setPosition(empVo.getPosition());
		employee.setSalary(empVo.getSalary());
		return employee ;
	}
	
	private EmployeeVO convertToVO(Employee employee) {
		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setId(employee.getId());
		employeeVO.setFirstname(employee.getFirstname());
		employeeVO.setLastname(employee.getLastname());
		employeeVO.setGender(employee.getGender());
		employeeVO.setPosition(employee.getPosition());
		employeeVO.setSalary(employee.getSalary());
		return employeeVO ;
	}

}
