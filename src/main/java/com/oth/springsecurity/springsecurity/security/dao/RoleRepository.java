package com.oth.springsecurity.springsecurity.security.dao;

import com.oth.springsecurity.springsecurity.security.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

	RoleEntity findByRoleName(String roleName);
}
