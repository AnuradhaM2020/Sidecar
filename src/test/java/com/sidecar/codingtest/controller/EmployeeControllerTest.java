
package com.sidecar.codingtest.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sidecar.codingtest.CodingtestApplicationTests;
import com.sidecar.codingtest.VO.EmployeeVO;

public class EmployeeControllerTest extends CodingtestApplicationTests {

	@Override

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void getEmployeeListTest() throws Exception {

		final String token = extractToken(authenticate("uname", "pwd").andReturn());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees")
				.header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String json = mvcResult.getResponse().getContentAsString();
		EmployeeVO[] employeeList = super.mapFromJsonToClass(json, EmployeeVO[].class);
		assertTrue(employeeList.length > 0);

	}

	@Test
	public void getEmployeeTest() throws Exception {
		final String token = extractToken(authenticate("uname", "pwd").andReturn());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employee/2")
				.header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String json = mvcResult.getResponse().getContentAsString();
		EmployeeVO employee = super.mapFromJsonToClass(json, EmployeeVO.class);
		assertNotNull(employee);

	}

	/* Test creating employee/ adding employee details in table */

	
	/*
	 * @Test public void addEmployeeTest() throws JsonProcessingException, Exception
	 * { final String token = extractToken(authenticate("uname",
	 * "pwd").andReturn()); EmployeeVO empvo = new EmployeeVO();
	 * empvo.setFirstname("Aaron"); empvo.setLastname("Smith");
	 * empvo.setGender("female"); empvo.setPosition("Module Lead");
	 * empvo.setSalary(200000.0); String inputJson = super.mapToJson(empvo);
	 * MvcResult result = mockMvc
	 * .perform(MockMvcRequestBuilders.post("/api/v1/employee").header(
	 * "Authorization", "Bearer " + token)
	 * .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
	 * .andReturn(); assertEquals(201, result.getResponse().getStatus());
	 * 
	 * String content = result.getResponse().getContentAsString();
	 * assertEquals(content, "Employee Created Successfully"); }
	 */
	/* Test Update employee details */
	
	/*
	 * @Test public void updateEmployeeTest() throws Exception { final String token
	 * = extractToken(authenticate("uname", "pwd").andReturn()); EmployeeVO empvo =
	 * new EmployeeVO(); empvo.setId(2); empvo.setFirstname("John");
	 * empvo.setLastname("kelh"); empvo.setGender("Male");
	 * empvo.setPosition("Architect"); empvo.setSalary(400000.0); String inputJson =
	 * super.mapToJson(empvo); MvcResult mvcResult = mockMvc
	 * .perform(MockMvcRequestBuilders.put("/api/v1/employee/2").header(
	 * "Authorization", "Bearer " + token)
	 * .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
	 * .andReturn();
	 * 
	 * assertEquals(200, mvcResult.getResponse().getStatus()); String content =
	 * mvcResult.getResponse().getContentAsString(); EmployeeVO employee =
	 * super.mapFromJsonToClass(content, EmployeeVO.class); assertNotNull(employee);
	 * }
	 * 
	 * 
	 * @Test public void deleteEmployeeTest() throws Exception { final String token
	 * = extractToken(authenticate("uname", "pwd").andReturn()); MvcResult mvcResult
	 * = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employee/40")
	 * .header("Authorization", "Bearer " +
	 * token).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	 * 
	 * assertEquals(200, mvcResult.getResponse().getStatus()); String content =
	 * mvcResult.getResponse().getContentAsString();
	 * assertEquals("Employee details deleted successfully", content); }
	 * 
	 */
}
