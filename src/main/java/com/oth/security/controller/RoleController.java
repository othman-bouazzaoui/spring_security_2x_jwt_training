package com.oth.security.controller;

import java.io.IOException;
import java.util.List;

import com.oth.security.entities.RoleEntity;
import com.oth.security.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/roles")
public class RoleController {
	
	private RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping
	@PostAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<List<RoleEntity>> findAllRoles(){
		return ResponseEntity.ok(roleService.findAllRoles());
	}
	
	@PostMapping("add")
	@PostAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<RoleEntity> addRole(@RequestBody RoleEntity role) throws IOException{
		return ResponseEntity.status(HttpStatus.CREATED).body(roleService.addRole(role));
	}
	
	@PutMapping("update")
	@PostAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<RoleEntity> updateRole(@RequestBody RoleEntity role){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(roleService.updateRole(role));
	}
	
	@DeleteMapping("{id}")
	@PostAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteRoleById(@PathVariable Long id){
		roleService.deleteRoleById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("{id}")
	@PostAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<RoleEntity> findRoleById(@PathVariable Long id){
		return ResponseEntity.ok(roleService.findRoleById(id));
	}
	

}
