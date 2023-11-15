package com.oth.security.service;

import com.oth.security.entities.RoleEntity;

import java.util.List;

public interface RoleService {
	
	List<RoleEntity> findAllRoles();
	
	RoleEntity findRoleById(Long id);
	
	RoleEntity findByRoleName(String name);
	
	RoleEntity addRole(RoleEntity role);
	
	RoleEntity updateRole(RoleEntity role);
	
	void deleteRoleById(Long id);

}
