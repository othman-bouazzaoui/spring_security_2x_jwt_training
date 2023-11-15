package com.oth.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oth.security.entities.UserEntity;
import com.oth.security.service.UserService;
import com.oth.security.util.JwtHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.oth.security.util.SecurityConstants.AUTHORIZATION;
import static com.oth.security.util.SecurityConstants.PREFIX;

@Slf4j
@RestController
@RequestMapping("api/v1/tokens")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("new-access-token")
    public void getNewTokens(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            log.warn("Refresh Token is required to generate an access token valid !!");
            new ObjectMapper().writeValue(response.getOutputStream(), "Refresh Token is required");
        }
    }

}
