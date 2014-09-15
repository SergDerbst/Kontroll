package com.tmt.kontroll.test.matchers;

import java.util.Collection;

import org.mockito.ArgumentMatcher;

public class EmptyCollectionMatcher extends ArgumentMatcher<Collection<?>> {

	@Override
	public boolean matches(final Object argument) {
		if (Collection.class.isAssignableFrom(argument.getClass())) {
			return ((Collection<?>) argument).isEmpty();
		}
		return false;
	}
}