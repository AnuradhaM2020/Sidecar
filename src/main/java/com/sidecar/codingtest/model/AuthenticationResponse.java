package com.sidecar.codingtest.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
	
	private static final long serialVersionUID = -2861043714936316004L;
	private final String jwt;
		

	public AuthenticationResponse(String jwt) {
		
		this.jwt = jwt;
	}

	public String getJwt() {
		return this.jwt;
	}
	
	

}
