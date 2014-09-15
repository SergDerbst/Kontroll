package com.tmt.kontroll.persistence.exceptions;

import org.apache.commons.lang3.exception.ContextedException;

@SuppressWarnings("serial")
public class EntityNotFoundInDatabaseException extends ContextedException {

	public static EntityNotFoundInDatabaseException prepare(final Object entity) {
		return (EntityNotFoundInDatabaseException) new EntityNotFoundInDatabaseException().addContextValue("entity", entity);
	}
}
