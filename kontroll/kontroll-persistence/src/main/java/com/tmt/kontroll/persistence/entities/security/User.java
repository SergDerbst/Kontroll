package com.tmt.kontroll.persistence.entities.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.entities.BaseEntity;

/**
 * Entity representing a user who can login and use the application.
 * 
 * @author Serg Derbst
 *
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_user",
																							columnNames = {"name"})})
public class User extends BaseEntity {

	@Column(nullable = false)
	private String					name;

	@Column(nullable = false)
	private String					password;

	@ManyToMany
	@JoinTable(name = "Users_Authorities",
							joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
							inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority>	authorities;

	private boolean					enabled;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
