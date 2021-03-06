package com.tmt.kontroll.security.persistence.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.BaseEntity;
import com.tmt.kontroll.persistence.utils.DatabaseDefinitions;

/**
 * Entity class representing an authority a user can have using this application.
 *  
 * @author Sergio Weigel
 *
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_authority",
																							columnNames = {"name"})})
public class Authority extends BaseEntity {

	@Column(length = DatabaseDefinitions.String_MediumSmall, nullable = false)
	private String			name;

	@Column(length = DatabaseDefinitions.String_Large)
	private String			comment;

	@ManyToMany(mappedBy = "authorities")
	private List<UserAccount>	users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<UserAccount> getUsers() {
		return users;
	}

	public void setUsers(List<UserAccount> users) {
		this.users = users;
	}
}
