package com.tmt.kontroll.test.persistence.run.data.reference;

import java.util.ArrayList;
import java.util.List;

public class ReferenceAssertionFailure {

	private final ReferenceAssertion assertion;
	private final Object expected;
	private final Object actual;

	private final List<String> additionalMessageElements = new ArrayList<String>();

	public ReferenceAssertionFailure(final ReferenceAssertion assertion,
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