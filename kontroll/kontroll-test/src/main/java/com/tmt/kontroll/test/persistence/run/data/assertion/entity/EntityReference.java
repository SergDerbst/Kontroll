package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;

public class EntityReference {

	private final Map<String, Object> referenceValueMap = new HashMap<>();
	private final Object entity;
	private final boolean isPrimary;

	public EntityReference(final Object entity,
	                       final boolean isPrimary,
	                       final boolean addToPersistenceTestContext) {
		this.entity = entity;
		this.isPrimary = isPrimary;
		this.createReferenceMap();
		if (addToPersistenceTestContext) {
			PersistenceTestContext.instance().testDataHolder().allReferences().add(this);
		}
	}

	public void updateReferenceValueMap() {
		this.referenceValueMap.clear();
		this.createReferenceMap();
	}

	public Object getEntity() {
		return this.entity;
	}

	public Class<?> getReferenceType() {
		return this.entity.getClass();
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
		for (final Field field : retrievePropertyFields(this.entity.getClass())) {
			this.referenceValueMap.put(field.getName(), retrieveFieldValue(field.getName(), this.entity));
		}
	}

	@Override
	public String toString() {
		final StringBuilder sB = new StringBuilder(this.getClass().getName());
		sB.append(" - ");
		if (!this.isPrimary) {
			sB.append("non ");
		}
		sB.append("primary \n");
		sB.append("\n");
		sB.append(this.entity);
		return sB.toString();
	}
}