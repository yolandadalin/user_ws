package com.yolanda.app.ws.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolanda.app.ws.SpringApplicationContext;
import com.yolanda.app.ws.service.UserService;
import com.yolanda.app.ws.shared.dto.UserDto;
import com.yolanda.app.ws.ui.model.request.UserLoginRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {

			UserLoginRequestModel creds = new ObjectMapper().readValue(req.getInputStream(),
					UserLoginRequestModel.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		byte[] secretKeyBytes = Base64.getEncoder().encode(SecurityConstants.getTokenSecret().getBytes());
		//SignatureAlgorithm deprecated
		//SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
		SecretKey secretKey = Keys.hmacShaKeyFor(SecurityConstants.getTokenSecret().getBytes(StandardCharsets.UTF_8));
		
		Instant now = Instant.now();

		String userName = ((User) auth.getPrincipal()).getUsername();
//		String token = Jwts.builder().setSubject(userName)
//				.setExpiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME))).setIssuedAt(Date.from(now))
//				.signWith(secretKey, SignatureAlgorithm.HS512).compact();

		String token = Jwts.builder()
	            .subject(userName)
	            .issuedAt(Date.from(now))
	            .expiration(Date.from(now.plusMillis(SecurityConstants.EXPIRATION_TIME)))
	            .signWith(secretKey, SignatureAlgorithm.HS512)
	            .compact();
		
		UserService userService = (UserService)SpringApplicationContext.getBean("userServiceImpl");
		UserDto userDto = userService.getUser(userName);
		
		res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		res.addHeader("UserId", userDto.getUserId());
	}
}
