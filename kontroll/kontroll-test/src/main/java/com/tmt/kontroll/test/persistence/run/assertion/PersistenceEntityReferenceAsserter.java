package com.tmt.kontroll.test.persistence.run.assertion;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldValue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class PersistenceEntityReferenceAsserter {

	private static class InstanceHolder {
		public static PersistenceEntityReferenceAsserter instance = new PersistenceEntityReferenceAsserter();
	}

	public static PersistenceEntityReferenceAsserter instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new PersistenceEntityReferenceAsserter();
		}
		return InstanceHolder.instance;
	}

	private final List<PersistenceEntityReferenceAssertionFailure> failures = new ArrayList<>();

	public void assertReferences(final List<PersistenceEntityReference> references, final List<Object> actuals) {
		this.failures.clear();
		boolean successful = false;
		if (this.assertNumberOfEntities(references, actuals)) {
			for (final PersistenceEntityReference reference : references) {
				if (this.assertReference(reference, actuals)) {
					successful = true;
				}
			}
		}
		if (!successful) {
			fail(this.buildFailureMessage());
		}
	}

	private String buildFailureMessage() {
		final StringBuilder sB = new StringBuilder("Persistence entity reference assertion failed. Failures:");
		for (final PersistenceEntityReferenceAssertionFailure failure : this.failures) {
			sB.append("\n");
			sB.append(failure.message());
		}
		return sB.toString();
	}

	private boolean assertReference(final PersistenceEntityReference reference, final List<Object> actuals) {
		for (final Object actual : actuals) {
			if (this.assertReference(reference, actual)) {
				return true;
			}
		}
		return false;
	}

	private boolean assertReference(final PersistenceEntityReference reference, final Object actual) {
		if (this.assertReferenceType(reference, actual)) {
			return this.assertReferenceProperties(reference, actual);
		}
		return false;
	}

	private boolean assertNumberOfEntities(final List<PersistenceEntityReference> references, final List<Object> actuals) {
		if (!this.isEqual(references.size(), actuals.size())) {
			this.failures.add(new PersistenceEntityReferenceAssertionFailure(PersistenceEntityReferenceAssertion.NumberOfEntities, references.size(), actuals.size()));
			return false;
		}
		return true;
	}

	private boolean assertReferenceProperties(final PersistenceEntityReference reference, final Object actual) {
		boolean successful = true;
		for (final Entry<String, Object> entry : reference.getReferenceEntrySet()) {
			final String fieldName = entry.getKey();
			final Object expectedValue = entry.getValue();
			final Object actualValue = retrieveFieldValue(fieldName, actual.getClass(), actual);
			if (!this.isEqual(expectedValue, actualValue)) {
				successful = false;
				final PersistenceEntityReferenceAssertionFailure failure = new PersistenceEntityReferenceAssertionFailure(PersistenceEntityReferenceAssertion.FieldValueOfEntity, expectedValue, actualValue);
				failure.addAdditionalMessageElement(fieldName);
				this.failures.add(failure);
			}
		}
		return successful;
	}

	private boolean assertReferenceType(final PersistenceEntityReference reference,
	                                    final Object actual) {
		final Class<?> expectedClass = reference.getReferenceType();
		final Class<?> actualClass = actual.getClass();
		if (expectedClass.isAssignableFrom(actualClass)) {
			return true;
		}
		this.failures.add(new PersistenceEntityReferenceAssertionFailure(PersistenceEntityReferenceAssertion.EntityType, expectedClass.getName(), actualClass.getName()));
		return false;
	}

	public boolean isEqual(final Object expected, final Object actual) {
		if (expected == null && actual == null) {
			return true;
		}
		return expected != null && expected.equals(actual);
	}
}
