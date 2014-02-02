package com.tmt.kontroll.test.persistence.run.assertion;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldValue;
import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PersistenceEntityReference {

	private final Class<?> referenceType;
	private final Map<String, Object> referenceValueMap = new HashMap<>();

	public PersistenceEntityReference(final Object reference) {
		this.referenceType = reference.getClass();
		this.createReferenceMap(reference);
	}

	public Class<?> getReferenceType() {
		return this.referenceType;
	}

	public Object getReferenceValue(final String valueName) {
		return this.referenceValueMap.get(valueName);
	}

	public Set<Entry<String,Object>> getReferenceEntrySet() {
		return this.referenceValueMap.entrySet();
	}

	private void createReferenceMap(final Object reference) {
		for (final Field field : retrieveFieldsForValueProvision(reference.getClass())) {
			final String fieldName = field.getName();
			this.referenceValueMap.put(fieldName, retrieveFieldValue(fieldName, reference.getClass(), reference));
		}
	}
}