package com.tmt.kontroll.test.persistence.run.data.reference;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldValue;
import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Reference {

	private final Map<String, Object> referenceValueMap = new HashMap<>();
	private final Object reference;

	public Reference(final Object reference) {
		this.reference = reference;
		this.createReferenceMap();
	}

	public Object getReference() {
		return this.reference;
	}

	public Class<?> getReferenceType() {
		return this.reference.getClass();
	}

	public Object getReferenceValue(final String valueName) {
		return this.referenceValueMap.get(valueName);
	}

	public Set<Entry<String,Object>> getReferenceEntrySet() {
		return this.referenceValueMap.entrySet();
	}

	private void createReferenceMap() {
		for (final Field field : retrieveFieldsForValueProvision(this.reference.getClass())) {
			final String fieldName = field.getName();
			this.referenceValueMap.put(fieldName, retrieveFieldValue(fieldName, this.reference.getClass(), this.reference));
		}
	}
}