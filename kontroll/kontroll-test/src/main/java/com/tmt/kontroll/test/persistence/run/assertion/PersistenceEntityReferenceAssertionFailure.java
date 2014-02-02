package com.tmt.kontroll.test.persistence.run.assertion;

import java.util.ArrayList;
import java.util.List;

public class PersistenceEntityReferenceAssertionFailure {

	private final PersistenceEntityReferenceAssertion assertion;
	private final Object expected;
	private final Object actual;

	private final List<String> additionalMessageElements = new ArrayList<String>();

	public PersistenceEntityReferenceAssertionFailure(final PersistenceEntityReferenceAssertion assertion,
	                                                  final Object expected,
	                                                  final Object actual) {
		this.assertion = assertion;
		this.actual = actual;
		this.expected = expected;
	}

	public void addAdditionalMessageElement(final String additionalMessageElement) {
		this.additionalMessageElements.add(additionalMessageElement);
	}

	public String message() {
		final StringBuilder sB = new StringBuilder();
		sB.append(this.assertion.message());
		for (final String additionalMessageElement : this.additionalMessageElements) {
			sB.append(", ");
			sB.append(additionalMessageElement);
		}
		sB.append(", expected: ");
		sB.append(this.expected.toString());
		sB.append(", actual: ");
		sB.append(this.actual.toString());
		return sB.toString();
	}
}