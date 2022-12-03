package com.oth.springsecurity.springsecurity.security.service;

import com.oth.springsecurity.springsecurity.security.dao.UserRepository;
import com.oth.springsecurity.springsecurity.security.entities.RoleEntity;
import com.oth.springsecurity.springsecurity.security.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleService roleService;

	public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
		this.userRepository = userRepository;
		this.roleService = roleService;
	}

	@Override
	public List<UserEntity> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity findUserById(Long id) {
		return userRepository.findById(id).orElseThrow(()->new RuntimeException("User Id '" + id + "' Not Found "));
	}

	@Override
	public UserEntity addUser(UserEntity u) {
		return userRepository.save(u);
	}

	@Override
	public UserEntity updateUser(UserEntity u) {
		findUserById(u.getIdUser());
		return userRepository.save(u);
	}

	@Override
	public void deleteUserById(Long id) {
		findUserById(id);
		userRepository.deleteById(id);
	}

	@Override
	public UserEntity findUserByUserName(String u) {
		return userRepository.findByUsername(u);
	}

	@Transactional
	@Override
	public UserEntity addRoleToUser(String username, String roleName) {
		UserEntity user = userRepository.findByUsername(username);
		RoleEntity role = roleService.findByRoleName(roleName);
		
		user.getRoles().add(role);
		
		return userRepository.save(user);
	}

}
