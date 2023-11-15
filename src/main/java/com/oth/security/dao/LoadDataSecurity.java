package com.oth.security.dao;

import java.util.Arrays;

import com.oth.security.entities.RoleEntity;
import com.oth.security.entities.UserEntity;
import com.oth.security.service.RoleService;
import com.oth.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoadDataSecurity {

	@Autowired
	private PasswordEncoder encoder;

	@Bean
	CommandLineRunner load(UserService userService, RoleService roleService) {
		return args -> {
			
			Arrays.asList(new UserEntity("Admin", encoder.encode("123")), new UserEntity("User", encoder.encode("123")),
					new UserEntity("Othman", encoder.encode("123"))).forEach(u -> userService.addUser(u));
			
			Arrays.asList(new RoleEntity("ADMIN"), new RoleEntity("USER")).forEach(r -> roleService.addRole(r));
			
			userService.addRoleToUser("Admin", "ADMIN");
			userService.addRoleToUser("Admin", "USER");
			userService.addRoleToUser("User", "USER");
			
			log.info("User to use for connecte : {} with password : {} => Roles : {} ", "Admin","123", "ADMIN & USER");
			log.info("User to use for connecte : {} with password : {} => Roles : {} ", "Othman","123", "No Role");
			log.info("User to use for connecte : {} with password : {} => Roles : {} ", "User","123","USER");
		};
	}

}
