package com.oth.security.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "ROLES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idRole;

	private String roleName;
	
	public RoleEntity(String roleName) {
		this.roleName = roleName;
	}

}
