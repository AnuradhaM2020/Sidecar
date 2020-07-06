package com.sidecar.codingtest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sidecar.codingtest.VO.UserVO;
import com.sidecar.codingtest.model.AuthenticationRequest;
import com.sidecar.codingtest.model.AuthenticationResponse;
import com.sidecar.codingtest.service.JwtUtil;
import com.sidecar.codingtest.service.UserDetailService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	UserDetailService userDetailService;
	@Autowired
	JwtUtil jwtToken;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value="/sign-up", nickname = "User Signup before get Auhenticate")
	// signup not required authentication
	@PostMapping("/sign-up")
	public ResponseEntity<UserVO> signUp(@RequestBody UserVO user) {
		LOGGER.info("in signUp()-- in UserController");
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		UserVO userVo = null;
		try {
			userVo = userDetailService.usersignUp(user);
		} catch (Exception e) {
			LOGGER.error("Username Already Exist",e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserVO>(userVo, HttpStatus.CREATED);
	}

	 @ApiOperation(value = "/authenticate", nickname =  "Authentication and Authorization Process")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthentication(@RequestBody AuthenticationRequest authRequest) throws Exception {
		LOGGER.info("In createAuthentication() -- UserController");
		ResponseEntity<?> responseEntity = null;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			LOGGER.error("Incorrect Username and Password", e);
			responseEntity =  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			throw new Exception("Incorrect Username and Password", e);
		} catch (Exception e) {
			LOGGER.error("Username and Password Authentication failed", e);
			responseEntity =  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			throw new Exception("Authentication Error", e);
		}
		// Further Granular Exception could be added like various 400 level http status error
		UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUsername());
		final String token = jwtToken.generateToken(userDetails);
		LOGGER.info("token Generated for the user");
		responseEntity = new ResponseEntity<>(new AuthenticationResponse(token), HttpStatus.OK);
		return responseEntity;

	}
}
