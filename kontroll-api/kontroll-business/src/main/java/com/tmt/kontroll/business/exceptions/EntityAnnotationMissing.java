package com.tmt.kontroll.business.exceptions;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

public class EntityAnnotationMissing extends ContextedRuntimeException {

	private static final long		serialVersionUID	= -524798841166058938L;

	private static final String	Message						= "Either @BusinessEntity or @Entity annotation must be present on entity object to be converted.";

	private EntityAnnotationMissing() {
		super(Message);
	}

	public static EntityAnnotationMissing prepare(final Object entity) {
		return (EntityAnnotationMissing) new EntityAnnotationMissing().setContextValue("entity", entity.getClass());
	}
}
