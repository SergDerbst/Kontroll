package com.tmt.kontroll.business.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class BusinessEntityMappingNotFoundException extends ContextedRuntimeException {

	private static final long	serialVersionUID	= -8765413627737195371L;

	public static BusinessEntityMappingNotFoundException prepare(final Class<?> persistenceType) {
		return (BusinessEntityMappingNotFoundException) new BusinessEntityMappingNotFoundException().addContextValue("persistence type", persistenceType.getName());
	}
}
