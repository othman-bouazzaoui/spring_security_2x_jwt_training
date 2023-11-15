package com.oth.security.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonProperty.*;

@Entity(name = "USER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idUser;

	private String username;

	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String firstName;

	private String lastName;

	private Date dateOfBirth;

	private Gender gender;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "idUser"), inverseJoinColumns = @JoinColumn(name = "idRole"))
	private List<RoleEntity> roles;

	public UserEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public UserEntity(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
}
