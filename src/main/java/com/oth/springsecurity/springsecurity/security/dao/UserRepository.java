package com.oth.springsecurity.springsecurity.security.dao;

import com.oth.springsecurity.springsecurity.security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findByUsername(String u);
}
