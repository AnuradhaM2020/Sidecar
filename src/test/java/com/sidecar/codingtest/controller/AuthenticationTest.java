
package com.sidecar.codingtest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sidecar.codingtest.CodingtestApplicationTests;
import com.sidecar.codingtest.VO.UserVO;


public class AuthenticationTest extends CodingtestApplicationTests {

	@Override

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/*
	 * @Override protected void doInit() throws Exception { userSignUp("uname",
	 * "pwd").andExpect(status().isCreated()); }
	 */
	@Test
	public void usernameAlreadyExist() throws JsonProcessingException, Exception {
		userSignUp("uname", "pwd").andExpect(status().isBadRequest());
	}

	@Test
	public void userWithoutTokenIsForbidden() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1.*")).andExpect(status().isForbidden());
	}

	@Test
	public void userWithTokenIsAllowed() throws Exception {

		final String token = extractToken(authenticate("uname", "pwd").andReturn());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
	}

	@Test
	public void authenticated() throws Exception {
		authenticate("uname", "pwd").andExpect(status().isOk()).andExpect(jsonPath("$.jwt").exists()).andReturn();
	}

	@Test
	public void notAuthenticated() throws Exception {
		authenticate("uname", "wrong").andExpect(status().isForbidden());
	}

	private ResultActions userSignUp(String username, String password) throws JsonProcessingException, Exception {

		UserVO userVo = new UserVO();
		userVo.setUsername(username);
		userVo.setPassword(password);
		String inputJson = super.mapToJson(userVo);

		return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sign-up")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson));
	}

}
