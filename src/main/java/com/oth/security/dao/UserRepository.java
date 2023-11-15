package com.oth.security.dao;

import com.oth.security.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	UserEntity findByUsername(String u);
}
