package com.oth.springsecurity.springsecurity.security.service;

import com.oth.springsecurity.springsecurity.security.entities.UserEntity;

import java.util.List;

public interface UserService {
	
	List<UserEntity> findAllUsers();
	
	UserEntity findUserById(Long id);
	
	UserEntity findUserByUserName(String u);
		
	UserEntity addUser(UserEntity u);
	
	UserEntity updateUser(UserEntity u);
	
	void deleteUserById(Long id);
	
	UserEntity addRoleToUser(String username, String roleName);

}
