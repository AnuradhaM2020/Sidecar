package com.sidecar.codingtest;



import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.sidecar.codingtest.model.AuthenticationRequest;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodingtestApplication.class)
public class CodingtestApplicationTests {
	
	
	@Test
	public void contextLoads() {
	}

	protected MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private static Set<Class> inited = new HashSet<>();

	@Autowired
	WebApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(ctx).apply(springSecurity()).build();
		if (!inited.contains(getClass())) {
			doInit();
			inited.add(getClass());
		}
	}

	/*
	 * @Before public void init() throws Exception { if
	 * (!inited.contains(getClass())) { doInit(); inited.add(getClass()); } }
	 */

	protected void doInit() throws Exception {
	}
 
	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	@SuppressWarnings("unchecked")
	protected <T> T mapFromJsonToClass(String json, Class<?> cls) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return (T) mapper.readValue(json, cls);
	}

	protected ResultActions authenticate(String username, String password) throws Exception {
		final AuthenticationRequest auth = new AuthenticationRequest();
		auth.setUsername(username);
		auth.setPassword(password);
		System.out.println("uname:.. " + auth.getUsername() + "pwd:.. " + auth.getPassword());
		return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/authenticate").content(mapToJson(auth))
				.contentType(MediaType.APPLICATION_JSON));
	}

	protected String extractToken(MvcResult result) throws UnsupportedEncodingException {
		System.out.println("Extrect token...");
		return JsonPath.read(result.getResponse().getContentAsString(), "$.jwt");
	}

}
