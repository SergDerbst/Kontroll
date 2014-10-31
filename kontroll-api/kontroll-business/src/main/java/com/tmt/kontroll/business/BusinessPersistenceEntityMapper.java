package com.tmt.kontroll.business;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveField;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrieveFieldValue;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.retrievePropertyFields;
import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.updateField;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.business.exceptions.BusinessEntityMappingNotFoundException;
import com.tmt.kontroll.business.exceptions.EntityMappingFailedException;
import com.tmt.kontroll.business.preparation.BusinessEntityScanner;

/**
 * <p>
 * The persistence business entity mapper maps persistence entity classes to business entity classes.
 * </p>
 * <p>
 * A business entity must be annotated with {@link BusinessEntity} with its value containing the related
 * persistence entity class. The {@link BusinessEntityScanner} will then scan for all business entities
 * and add them to the mapper. The basic requirement of a business entity is that it has the exact same set
 * of fields as properties as the persistence entity. It will then be instantiated with the proper values
 * during {@link #fetchBusinessEntity}.
 * </p>
 *
 *
 * @author SergDerbst
 *
 */
@Component
public class BusinessPersistenceEntityMapper {

	private final Map<Class<?>, Class<?>>	businessEntityMap			= new HashMap<>();
	private final Map<Class<?>, Class<?>>	persistenceEntityMap	= new HashMap<>();

	public void addMapping(final Class<?> persistenceType, final Class<?> businessType) {
		this.businessEntityMap.put(persistenceType, businessType);
		this.persistenceEntityMap.put(businessType, persistenceType);
	}

	public <PERSISTENCE_ENTITY, BUSINESS_ENTITY> BUSINESS_ENTITY fetchBusinessEntity(final PERSISTENCE_ENTITY persistenceEntity) {
		final Class<?> businessEntityClass = this.businessEntityMap.get(persistenceEntity.getClass());
		if (businessEntityClass == null) {
			throw BusinessEntityMappingNotFoundException.prepare(persistenceEntity.getClass());
		}
		/*
		 * TODO: make creation recursively to assure that entity values for fields are converted as well
		 */
		return this.createEntity(businessEntityClass, persistenceEntity);
	}

	public <PERSISTENCE_ENTITY, BUSINESS_ENTITY> BUSINESS_ENTITY fetchPersistenceEntity(final BUSINESS_ENTITY businessEntity) {
		final Class<?> persistenceEntityClass = this.businessEntityMap.get(businessEntity.getClass());
		if (persistenceEntityClass == null) {
			throw BusinessEntityMappingNotFoundException.prepare(businessEntity.getClass());
		}
		return this.createEntity(persistenceEntityClass, businessEntity);
	}

	@SuppressWarnings("unchecked")
	private <REFERENCE_ENTITY, CREATED_ENTITY> CREATED_ENTITY createEntity(final Class<?> createEntityClass, final REFERENCE_ENTITY referenceEntity) {
		try {
			final CREATED_ENTITY businessEntity = (CREATED_ENTITY) createEntityClass.newInstance();
			for (final Field field : retrievePropertyFields(referenceEntity.getClass())) {
				updateField(businessEntity, retrieveFieldValue(field, referenceEntity), retrieveField(field.getName(), businessEntity));
			}
			return businessEntity;
		} catch (final Exception e) {
			throw EntityMappingFailedException.prepare(e);
		}
	}
}
