package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;

/**
 * Each entity reference used for prepared persistence tests holds both the entity
 * instance and a reference value map containing the field values mapped by the entity's
 * field names, in order to verify the entity's state after operations are performed
 * during a persistence test, e.g. after reading the entity from the database. This
 * should be done at the end of the test and in addition to DBUnit's database verification
 * after it, allowing for a more detailed verification, if wanted.
 * </p>
 * Any entity reference also knows, if its referenced entity is primary, meaning that
 * it is an actual entity used for the test and not just one that is needed to setup
 * the database meeting requirements as a result of entity constraints and relationships.
 * </p>
 * <i>Note:</i> On construction of an entity reference, it is automatically added to the
 * set of all references of the {@link TestDataHolder} and thus to the
 * {@link PersistenceTestContext}. This can be omitted by setting the flag
 * <code>addToPersistenceTestContext</code> that is passed to the constructor to
 * <code>false</code>.
 * </br>
 * Admittedly, this is a very weird design, but it was necessary in order to handle
 * relationships between entities without any prior knowledge of what they will be or how
 * many there will be etc., which after all is the key point of running prepared persistence
 * tests.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
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

	public Object entity() {
		return this.entity;
	}

	public Class<?> referenceType() {
		return this.entity.getClass();
	}

	public Object referenceValue(final String valueName) {
		return this.referenceValueMap.get(valueName);
	}

	public Set<Entry<String,Object>> referenceEntrySet() {
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