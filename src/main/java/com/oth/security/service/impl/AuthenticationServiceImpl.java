package com.oth.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oth.security.entities.UserEntity;
import com.oth.security.service.AuthenticationService;
import com.oth.security.service.UserService;
import com.oth.security.util.JwtHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.oth.security.util.SecurityConstants.AUTHORIZATION;
import static com.oth.security.util.SecurityConstants.PREFIX;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final UserService userService;

	public AuthenticationServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@SneakyThrows
	@Override
	public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String authorizationToken = request.getHeader(AUTHORIZATION.getValue());

		if (authorizationToken != null && authorizationToken.startsWith(PREFIX.getValue())) {
			String jwt = authorizationToken.substring(7);
			Algorithm algorithm = JwtHelper.getAlgorithm();
			JWTVerifier jwtVerifier = JWT.require(algorithm).build();

			DecodedJWT verify = jwtVerifier.verify(jwt);
			String username = verify.getSubject();
			UserEntity user = userService.findUserByUserName(username);
			log.info("renew token for username {}", username);
			String accessToken = JwtHelper.generateAccessToken(request.getRequestURI(), user);

			Map<String, String> idToken = new HashMap<>();
			idToken.put("accessToken", accessToken);

			response.setContentType("application/json");

			new ObjectMapper().writeValue(response.getOutputStream(), idToken);

		} else {
			log.warn("Refresh Token is required to generate an access token !!");
			new ObjectMapper().writeValue(response.getOutputStream(), "Refresh Token is required");
		}
	}

}
