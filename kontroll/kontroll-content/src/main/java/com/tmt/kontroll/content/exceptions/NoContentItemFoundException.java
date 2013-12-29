package com.tmt.kontroll.content.exceptions;


public class NoContentItemFoundException extends ContentException {

	private static final long serialVersionUID = -4850286757271324563L;

	public static <T extends Enum<T>> NoContentItemFoundException prepare(final T tag) {
		return (NoContentItemFoundException) new NoContentItemFoundException()
				.addContextValue("tag", tag.toString())
				.addContextValue("tagType", tag.getClass().getName());
	}
}
