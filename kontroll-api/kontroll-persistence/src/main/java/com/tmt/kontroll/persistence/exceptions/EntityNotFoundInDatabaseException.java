package com.tmt.kontroll.persistence.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

@SuppressWarnings("serial")
public class EntityNotFoundInDatabaseException extends ContextedRuntimeException {

	public static EntityNotFoundInDatabaseException prepare(final Object entity) {
		return (EntityNotFoundInDatabaseException) new EntityNotFoundInDatabaseException().addContextValue("entity", entity);
	}
}
