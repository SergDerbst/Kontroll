package com.tmt.kontroll.test.dao.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvisionChain;

@Component
public class EntityInstanceProvider {

	@Autowired
	ValueFieldProvisionChain valueProvisionChain;

	public Object provide(final Class<?> entityClass) {
		try {
			final Object entity = entityClass.newInstance();
			for (final Field field : this.retrieveFields(entityClass)) {
				field.setAccessible(true);
				field.set(entity, this.valueProvisionChain.provide(field.getName(), field.getType()));
			}
			return entity;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ValueFieldProvisionChain getValueFieldProvisionChain() {
		return this.valueProvisionChain;
	}

	private List<Field> retrieveFields(final Class<?> entityClass) {
		final List<Field> fields = new ArrayList<Field>();
		Class<?> currentClass = entityClass;
		while(!Object.class.equals(currentClass)) {
			for (final Field field : currentClass.getDeclaredFields()) {
				if (this.isFieldToBeProvided(field)) {
					fields.add(field);
				}
			}
			currentClass = currentClass.getSuperclass();
		}
		return fields;
	}

	private boolean isFieldToBeProvided(final Field field) {
		return !field.getName().startsWith("$") && !(Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()));
	}
}
