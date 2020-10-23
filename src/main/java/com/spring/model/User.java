package com.spring.model;

import java.util.*;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel {
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	private String phoneNumber;
	private String password;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User(String username, String email, String phoneNumber, String password) {
		this.username = username;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

}