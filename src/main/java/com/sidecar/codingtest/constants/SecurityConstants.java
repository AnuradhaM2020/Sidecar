package com.sidecar.codingtest.constants;

public class SecurityConstants {
	
	public static final String SECRET_KEY = "secret";
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 5;  // 5hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/v1/sign-up";
    public static final String LOG_IN_URL = "/api/v1/authenticate";
}
