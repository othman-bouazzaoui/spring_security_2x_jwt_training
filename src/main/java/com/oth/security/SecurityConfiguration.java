package com.oth.security;

import com.oth.security.filters.JwtAuthenticationFilter;
import com.oth.security.filters.JwtAuthorizationFilter;
import com.oth.security.service.UserDetailsServiceSpec;
import com.oth.security.util.SecurityConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final UserDetailsServiceSpec userDetails;

	public SecurityConfiguration(UserDetailsServiceSpec userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetails);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "PUT", "DELETE"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));

		http.csrf().disable().cors().configurationSource(request -> corsConfiguration).and()
			.authorizeRequests().antMatchers(SecurityConstants.getAuthorizedPaths()).permitAll()
			//.antMatchers(HttpMethod.GET,"/api/v1/users/**").hasAuthority("ADMIN")
			.anyRequest().permitAll()
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		/*
		http.formLogin();
		*/
		http.headers().frameOptions().disable();
		http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));
		
		//It's some mecanisme of midlware, il s'execute avant tous les autres filters pour interceper la requets du client et voir s'il est authentifié( valid acces-token)
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();	
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

}
