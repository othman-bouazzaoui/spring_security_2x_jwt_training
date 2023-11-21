package com.oth.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.oth.security.entities.UserEntity;
import com.oth.security.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserService userService;

	public UserDetailsServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userService.findUserByUserName(username);
		List<GrantedAuthority> roles = new ArrayList<>();
		user.getRoles().forEach(r -> roles.add(new SimpleGrantedAuthority(r.getRoleName())));

		return new User(user.getUsername(), user.getPassword(), roles);
	}

}
