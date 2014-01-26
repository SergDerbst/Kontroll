package com.tmt.kontroll.test.persistence.dao.entity;

import static com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueTypeHelper.retrieveFields;
import static com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueTypeHelper.retrieveTypeArgumentsOfField;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;

@Component
public class EntityInstanceProvider {

	@Autowired
	ValueProvisionHandler valueProvisionHandler;

	public Object provide(final Class<?> entityClass) {
		try {
			final Object entity = entityClass.newInstance();
			for (final Field field : retrieveFields(entityClass)) {
				this.setFieldValue(entity, field);
			}
			return entity;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void setFieldValue(final Object entity, final Field field) throws Exception {
		field.setAccessible(true);
		final String fieldName = field.getName();
		final Class<?> fieldType = field.getType();
		if (Collection.class.isAssignableFrom(fieldType)) {
			field.set(entity, this.valueProvisionHandler.provide(fieldName, fieldType, retrieveTypeArgumentsOfField(field, 0)));
		} else if (Map.class.isAssignableFrom(fieldType)) {
			field.set(entity, this.valueProvisionHandler.provide(fieldName, fieldType, retrieveTypeArgumentsOfField(field, 0), retrieveTypeArgumentsOfField(field, 1)));
		} else if (fieldType.isArray()) {
			field.set(entity, this.valueProvisionHandler.provide(fieldName, fieldType, fieldType.getComponentType()));
		}	else {
			field.set(entity, this.valueProvisionHandler.provide(fieldName, fieldType));
		}
	}
}
