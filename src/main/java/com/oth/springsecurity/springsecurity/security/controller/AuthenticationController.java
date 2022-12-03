package com.oth.springsecurity.springsecurity.security.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oth.springsecurity.springsecurity.security.entities.UserEntity;
import com.oth.springsecurity.springsecurity.security.service.UserService;
import com.oth.springsecurity.springsecurity.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("renewtokens")
    public void getNewTokens(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationToken = request.getHeader(SecurityUtil.AUTHORIZATION);
        if (authorizationToken != null && authorizationToken.startsWith(SecurityUtil.PREFIX)) {
            try {
                String jwt = authorizationToken.substring(7);
                Algorithm algorithm = Algorithm.HMAC256(SecurityUtil.SECRET);

                JWTVerifier jwtVerifier = JWT.require(algorithm).build();

                DecodedJWT verify = jwtVerifier.verify(jwt);

                String username = verify.getSubject();

                UserEntity user = userService.findUserByUserName(username);

                /* Access Token */
                String accesToken = JWT.create().withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * SecurityUtil.EXPIRE_ACCES_TOKEN))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles",
                                user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> idToken = new HashMap<>();
                idToken.put("accesToken", accesToken);

                response.setContentType("application/json");

                new ObjectMapper().writeValue(response.getOutputStream(), idToken);

            } catch (Exception ex) {
                throw ex;
            }
        } else {
            throw new RuntimeException("Refresh Token is required to generate an acces token valid !!");
        }
    }

}
