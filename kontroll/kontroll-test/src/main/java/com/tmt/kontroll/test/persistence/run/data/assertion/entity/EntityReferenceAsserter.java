package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveAnnotatedFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveFieldValue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;

public class EntityReferenceAsserter {

	private static class InstanceHolder {
		public static EntityReferenceAsserter instance;
	}

	public static EntityReferenceAsserter newInstance() {
		InstanceHolder.instance = new EntityReferenceAsserter();
		return InstanceHolder.instance;
	}

	private final List<EntityReferenceAssertionFailure> failures = new ArrayList<>();
	private final Set<String> ignoredFieldNames = new HashSet<>();

	private EntityReferenceAsserter() {}

	public void configureForEntityId(final PersistenceTestConfig config, final Class<?> entityClass) {
		if (config.ignoreEntityId()) {
			for (final Field field : retrieveAnnotatedFields(entityClass, Id.class)) {
				this.ignoredFieldNames.add(field.getName());
			}
		}
	}

	public void configureForIgnoredFields(final PersistenceTestConfig config) {
		for (final String fieldName : config.ignoredFields()) {
			this.ignoredFieldNames.add(fieldName);
		}
	}

	public void assertReferences(final List<EntityReference> references, final List<Object> actuals) {
		this.failures.clear();
		boolean successful = false;
		if (this.assertNumberOfEntities(references, actuals)) {
			for (final EntityReference reference : references) {
				if (this.assertReference(reference, actuals)) {
					successful = true;
				}
			}
		}
		if (!successful) {
			fail(this.failureMessage());
		}
	}

	private String failureMessage() {
		final StringBuilder sB = new StringBuilder("Persistence entity reference assertion failed. Failures:");
		for (final EntityReferenceAssertionFailure failure : this.failures) {
			sB.append("\n");
			sB.append(failure.message());
		}
		return sB.toString();
	}

	private boolean assertReference(final EntityReference reference, final List<Object> actuals) {
		for (final Object actual : actuals) {
			if (this.assertReference(reference, actual)) {
				return true;
			}
		}
		return false;
	}

	private boolean assertReference(final EntityReference reference, final Object actual) {
		if (this.assertReferenceType(reference, actual)) {
			return this.assertReferenceProperties(reference, actual);
		}
		return false;
	}

	private boolean assertNumberOfEntities(final List<EntityReference> references, final List<Object> actuals) {
		if (!this.isEqual(references.size(), actuals.size())) {
			this.failures.add(new EntityReferenceAssertionFailure(EntityReferenceAssertion.NumberOfEntities, references.size(), actuals.size()));
			return false;
		}
		return true;
	}

	private boolean assertReferenceProperties(final EntityReference reference, final Object actual) {
		boolean successful = true;
		for (final Entry<String, Object> entry : reference.getReferenceEntrySet()) {
			final String fieldName = entry.getKey();
			if (this.ignoredFieldNames.contains(fieldName)) {
				continue;
			}
			final Object expectedValue = entry.getValue();
			final Object actualValue = retrieveFieldValue(fieldName, actual.getClass(), actual);
			if (!this.isEqual(expectedValue, actualValue)) {
				successful = false;
				final EntityReferenceAssertionFailure failure = new EntityReferenceAssertionFailure(EntityReferenceAssertion.FieldValueOfEntity, expectedValue, actualValue);
				failure.addAdditionalMessageElement(fieldName);
				this.failures.add(failure);
			}
		}
		return successful;
	}

	private boolean assertReferenceType(final EntityReference reference,
	                                    final Object actual) {
		final Class<?> expectedClass = reference.getReferenceType();
		final Class<?> actualClass = actual.getClass();
		if (expectedClass.isAssignableFrom(actualClass)) {
			return true;
		}
		this.failures.add(new EntityReferenceAssertionFailure(EntityReferenceAssertion.EntityType, expectedClass.getName(), actualClass.getName()));
		return false;
	}

	private boolean isEqual(final Object expected, final Object actual) {
		if (expected == null && actual == null) {
			return true;
		}
		return expected != null && expected.equals(actual);
	}


	public Set<String> getIgnoredFieldNames() {
		return this.ignoredFieldNames;
	}
}
