package com.tmt.kontroll.test.matchers;

import java.util.List;

import org.mockito.ArgumentMatcher;

public class EmptyListMatcher<T> extends ArgumentMatcher<List<T>> {

	@Override
	public boolean matches(final Object argument) {
		if (List.class.isAssignableFrom(argument.getClass())) {
			return ((List<?>) argument).isEmpty();
		}
		return false;
	}
}
