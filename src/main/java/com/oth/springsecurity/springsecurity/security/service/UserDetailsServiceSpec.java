package com.oth.springsecurity.springsecurity.security.service;

import java.util.ArrayList;
import java.util.List;

import com.oth.springsecurity.springsecurity.security.entities.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceSpec implements UserDetailsService {

	private UserService userService;

	public UserDetailsServiceSpec(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userService.findUserByUserName(username);
		List<GrantedAuthority> roles = new ArrayList<>();
		user.getRoles().forEach(r -> {
			roles.add(new SimpleGrantedAuthority(r.getRoleName()));
		});

		return new User(user.getUsername(), user.getPassword(), roles);
	}

}
