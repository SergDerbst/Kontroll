package com.tmt.kontroll.persistence.entities.security;

import java.util.List;
import java.util.Locale;

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
 * @author Sergio Weigel
 *
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_user", columnNames = {"name"})})
public class User extends BaseEntity {

	@Column(nullable = false)
	private String					name;

	@Column(nullable = false)
	private String					password;

	@Column(nullable = false, columnDefinition = "character varying (4) default 'en'")
	private Locale					locale;

	@ManyToMany
	@JoinTable(name = "Users_Authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority>	authorities;

	private boolean					enabled;

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public List<Authority> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(final List<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}
}
