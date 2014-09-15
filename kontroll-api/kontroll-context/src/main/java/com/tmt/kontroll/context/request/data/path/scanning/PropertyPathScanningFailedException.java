package com.tmt.kontroll.context.request.data.path.scanning;

import org.apache.commons.lang3.exception.ContextedException;

public class PropertyPathScanningFailedException extends ContextedException {

	private static final long serialVersionUID = -1757449404882070150L;
	
	public PropertyPathScanningFailedException(Throwable cause) {
		super(cause);
	}
	
	public static PropertyPathScanningFailedException prepare(final Throwable cause,
	                                                            final String propertyName,
	                                                            final String path) {
		return (PropertyPathScanningFailedException) 
			new PropertyPathScanningFailedException(cause)
				.addContextValue("propertyName", propertyName)
				.addContextValue("path", path);
	}
}
