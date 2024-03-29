package com.oth.security.service.impl;

import com.oth.security.entities.RoleEntity;
import com.oth.security.dao.RoleRepository;
import com.oth.security.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;
	
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleEntity> findAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public RoleEntity findRoleById(Long id) {
		return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role Id '" + id + "'Not Found !! "));
	}

	@Override
	public RoleEntity addRole(RoleEntity role) {
		System.out.println(role);
		return roleRepository.save(role);
	}

	@Override
	public RoleEntity updateRole(RoleEntity role) {
		findRoleById(role.getIdRole());
		return roleRepository.save(role);
	}

	@Override
	public void deleteRoleById(Long id) {
		findRoleById(id);
		roleRepository.deleteById(id);
	}

	@Override
	public RoleEntity findByRoleName(String name) {
		return roleRepository.findByRoleName(name);
	}

}
