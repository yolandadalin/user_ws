package com.yolanda.app.ws.security;

import org.springframework.core.env.Environment;

import com.yolanda.app.ws.SpringApplicationContext;

public class SecurityConstants {

	public static final long EXPIRATION_TIME=864000000; // 10 days
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	public static final String SIGN_UP_URL="/users";
	
	public static String getTokenSecret()
    {
		Environment environment = (Environment) SpringApplicationContext.getBean("environment");
        return environment.getProperty("tokenSecret");
    }
	
}
