package com.tmt.kontroll.business;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveField;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.updateField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.business.exceptions.BusinessEntityMappingNotFoundException;
import com.tmt.kontroll.business.exceptions.EntityAnnotationMissing;
import com.tmt.kontroll.business.exceptions.EntityMappingFailedException;
import com.tmt.kontroll.business.preparation.BusinessEntityScanner;

/**
 * <p>
 * The persistence business entity mapper maps persistence entity classes to business entity classes
 * and vice versa.
 * </p>
 * <p>
 * A business entity must be annotated with {@link BusinessEntity} with its value containing the related
 * persistence entity class. On preparation time, the {@link BusinessEntityScanner} will then scan for all
 * business entity classes and add them and their persistence counterparts to the mapper. The basic
 * requirement of a business entity is that it has the at least the same set of property fields as the
 * persistence entity. It will then be instantiated with the proper values.
 * </p>
 * <p>
 * <i>Note: </i>The wrapper works both ways and will always return the opposite entity.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
public class BusinessPersistenceEntityMapper {

	@Autowired
	BusinessPersistenceEntityValueConverter	entityConverter;

	private final Map<Class<?>, Class<?>>		businessEntityMap			= new HashMap<>();
	private final Map<Class<?>, Class<?>>		persistenceEntityMap	= new HashMap<>();

	public void addMapping(final Class<?> persistenceType, final Class<?> businessType) {
		this.businessEntityMap.put(persistenceType, businessType);
		this.persistenceEntityMap.put(businessType, persistenceType);
	}

	public <SOURCE_ENTITY, TARGET_ENTITY> TARGET_ENTITY fetchConvertedEntity(final SOURCE_ENTITY sourceEntity) {
		final Class<?> targetEntityClass = this.fetchTargetEntityClass(sourceEntity);
		if (targetEntityClass == null) {
			throw BusinessEntityMappingNotFoundException.prepare(sourceEntity.getClass());
		}
		return this.createTargetEntity(targetEntityClass, sourceEntity);
	}

	@SuppressWarnings("unchecked")
	private <SOURCE_ENTITY, TARGET_ENTITY> TARGET_ENTITY createTargetEntity(final Class<?> targetEntityClass, final SOURCE_ENTITY sourceEntity) {
		try {
			final TARGET_ENTITY businessEntity = (TARGET_ENTITY) targetEntityClass.newInstance();
			for (final Field field : retrievePropertyFields(sourceEntity.getClass())) {
				updateField(businessEntity, this.entityConverter.convert(retrieveFieldValue(field, sourceEntity)), retrieveField(field.getName(), businessEntity));
			}
			return businessEntity;
		} catch (final Exception e) {
			throw EntityMappingFailedException.prepare(e);
		}
	}

	private Class<?> fetchTargetEntityClass(final Object sourceEntity) {
		if (sourceEntity.getClass().isAnnotationPresent(Entity.class)) {
			return this.businessEntityMap.get(sourceEntity.getClass());
		}
		if (sourceEntity.getClass().isAnnotationPresent(BusinessEntity.class)) {
			return this.persistenceEntityMap.get(sourceEntity.getClass());
		}
		throw EntityAnnotationMissing.prepare(sourceEntity);
	}
}
