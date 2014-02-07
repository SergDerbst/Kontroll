package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionHelper.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EntityReference {

	private final Map<String, Object> referenceValueMap = new HashMap<>();
	private final Object reference;

	public EntityReference(final Object reference) {
		this.reference = reference;
		this.createReferenceMap();
	}

	public Object getEntity() {
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
		for (final Field field : retrievePropertyFields(this.reference.getClass())) {
			this.referenceValueMap.put(field.getName(), retrieveFieldValue(field.getName(), this.reference));
		}
	}
}