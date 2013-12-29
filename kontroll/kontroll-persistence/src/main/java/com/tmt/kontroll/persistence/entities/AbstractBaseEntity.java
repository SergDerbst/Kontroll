package com.tmt.kontroll.persistence.entities;

import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass of all standard entities.
 * 
 * @author Serg Derbst
 */
@MappedSuperclass
public class AbstractBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer		id;

	private Timestamp	timeStamp;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
}
