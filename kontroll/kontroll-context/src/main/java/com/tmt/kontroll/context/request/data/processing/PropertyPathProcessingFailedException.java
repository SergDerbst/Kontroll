package com.tmt.kontroll.context.request.data.processing;

import org.apache.commons.lang3.exception.ContextedException;

public class PropertyPathProcessingFailedException extends ContextedException {

	private static final long serialVersionUID = -1757449404882070150L;
	
	public PropertyPathProcessingFailedException(Throwable cause) {
		super(cause);
	}
	
	public static PropertyPathProcessingFailedException prepare(final Throwable cause,
	                                                            final String propertyName,
	                                                            final String path) {
		return (PropertyPathProcessingFailedException) 
			new PropertyPathProcessingFailedException(cause)
				.addContextValue("propertyName", propertyName)
				.addContextValue("path", path);
	}
}
