package com.oth.security.service;

import com.oth.security.entities.UserEntity;

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
