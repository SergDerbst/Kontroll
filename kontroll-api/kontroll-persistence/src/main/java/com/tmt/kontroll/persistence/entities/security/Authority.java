package com.tmt.kontroll.persistence.entities.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.entities.BaseEntity;
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
	private List<User>	users;

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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
