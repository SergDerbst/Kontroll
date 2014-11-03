package com.tmt.kontroll.persistence;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.persistence.utils.JpaEntityUtils.isRelationshipField;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass of all standard entities.
 * 
 * @author Sergio Weigel
 */
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer		id;

	private Timestamp	timeStamp;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(final Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		final StringBuilder sB = new StringBuilder();
		sB.append("Entity: " + this.getClass().getName() + "@" + System.identityHashCode(this));
		for (final Field field : retrievePropertyFields(this.getClass())) {
			field.setAccessible(true);
			sB.append("\n");
			sB.append("field: " + field.getName());
			sB.append(", type: " + field.getType().getName());
			try {
				if (isRelationshipField(field)) {
					sB.append(", value: " + field.getType().getName() + "@" + System.identityHashCode(field.get(this)));
				} else {
					sB.append(", value: " + field.get(this));
				}
			} catch (final Exception e) {
				/*
				 * we usually don't swallow exceptions (we spit), but here we make an
				 * exception (pun unintended) as not to kill the string representation
				 */
				sB.append("Failed to fetch value of field! " + e.getClass().getName());
			}
		}
		return sB.toString();
	}
}
