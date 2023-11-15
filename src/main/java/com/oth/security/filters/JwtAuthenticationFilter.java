package com.oth.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oth.security.util.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

	public static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		LOG.info("I am in attemptAuthentication ");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		LOG.info("username = {}", username);
		LOG.info("password = {}", password);

		UsernamePasswordAuthenticationToken authenticationFilter = new UsernamePasswordAuthenticationToken(username,
				password);
		LOG.debug("authenticationFilter = {}", authenticationFilter);
		return authenticationManager.authenticate(authenticationFilter);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		LOG.info("I am in successfulAuthentication ");

		User user = (User) authResult.getPrincipal();
		LOG.info("User : {}", user);

		Map<String, String> tokens = JwtHelper.getTokens(String.valueOf(request.getRequestURL()), user);

		response.setContentType("application/json");
		
		new ObjectMapper().writeValue(response.getOutputStream(),tokens);
		
		super.successfulAuthentication(request, response, chain, authResult);
	}

}
