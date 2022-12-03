package com.oth.springsecurity.springsecurity.security.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oth.springsecurity.springsecurity.security.entities.UserEntity;
import com.oth.springsecurity.springsecurity.security.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@PostAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<List<UserEntity>> allUsers(){
		return ResponseEntity.ok(userService.findAllUsers());
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@PostMapping("add")
	public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(user));
	}
	
	@PostAuthorize("hasAuthority('ADMIN')")
	@PutMapping("update")
	public ResponseEntity<UserEntity> updateUser(@RequestBody UserEntity user){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(user));
	}
	
	@DeleteMapping("/{id}")
	@PostAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteUserById(@PathVariable Long id){
		userService.deleteUserById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	@PostAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserEntity> findUserById(@PathVariable Long id){
		return ResponseEntity.ok(userService.findUserById(id));
	}
	
	@GetMapping("/refreshToken")
	public void rehreshToken(HttpServletRequest request,HttpServletResponse response) throws IOException {

		String authorizationToken = request.getHeader("Authorization");
		if(authorizationToken != null && authorizationToken.startsWith("Bearer ")) {
			try {
			String jwt = authorizationToken.substring(7);
			Algorithm algorithm = Algorithm.HMAC256("Othman1995");
			
			JWTVerifier jwtVerifier = JWT.require(algorithm).build();
			
			DecodedJWT verify = jwtVerifier.verify(jwt);
			
			String username = verify.getSubject();

			UserEntity user = userService.findUserByUserName(username);
			
			/* Access Token */
			String accesToken = JWT.create().withSubject(user.getUsername())
					.withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60))
					.withIssuer(request.getRequestURL().toString())
					.withClaim("roles",
							user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList()))
					.sign(algorithm);
			
			Map<String, String> idToken = new HashMap<>();
			idToken.put("accesToken", accesToken);
			
			response.setContentType("application/json");
			
			new ObjectMapper().writeValue(response.getOutputStream(),idToken);

			} catch(Exception ex) {
				throw ex;				
			}
		}else {
			throw new RuntimeException("Refresh Token is required to generate an acces token valid !!");
		}
	}
}
