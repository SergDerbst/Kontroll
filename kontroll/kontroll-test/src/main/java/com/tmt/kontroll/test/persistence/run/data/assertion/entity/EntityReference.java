package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EntityReference {

	private final Map<String, Object> referenceValueMap = new HashMap<>();
	private final Object reference;
	private final boolean isPrimary;

	public EntityReference(final Object reference,
	                       final boolean isPrimary) {
		this.reference = reference;
		this.isPrimary = isPrimary;
		this.createReferenceMap();
	}

	public void updateReferenceValueMap() {
		this.referenceValueMap.clear();
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

	public boolean isPrimary() {
		return this.isPrimary;
	}

	private void createReferenceMap() {
		for (final Field field : retrievePropertyFields(this.reference.getClass())) {
			this.referenceValueMap.put(field.getName(), retrieveFieldValue(field.getName(), this.reference));
		}
	}
}