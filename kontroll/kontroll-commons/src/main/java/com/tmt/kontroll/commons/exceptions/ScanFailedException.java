package com.tmt.kontroll.commons.exceptions;

public class ScanFailedException extends RuntimeException {

	private static final long	serialVersionUID	= 3366880505263737419L;

	public ScanFailedException(Throwable cause) {
		super(cause);
	}

	public ScanFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}
