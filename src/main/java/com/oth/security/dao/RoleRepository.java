package com.oth.security.dao;

import com.oth.security.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>{

	RoleEntity findByRoleName(String roleName);
}
