package com.tmt.kontroll.business;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.business.exceptions.EntityValueConversionFailedException;

/**
 * <p>
 * The entity value converter works as a wrapper around the {@link BusinessPersistenceEntityMapper} and
 * handles the possible occurence of entity objects in collections and maps.
 * </p>
 * <p>
 * It will recognize the occurence and, if needed, instantiate the proper collection or map instances
 * and call the {@link BusinessPersistenceEntityMapper} to fetch business or persistence entities.
 * </p>
 * <p>
 * <i>Note: </i>If the entity to be converted has neither {@link BusinessEntity} nor {@link Entity} annotations
 * present, conversion between business and persistence will be omitted and just the value returned or added
 * the proper collection or map, respectively.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Component
@SuppressWarnings("unchecked")
public class BusinessPersistenceEntityValueConverter {

	@Autowired
	BusinessPersistenceEntityMapper	persistenceBusinessEntityMapper;

	public <COLLECTION extends Collection<?>, MAP extends Map<?, ?>> Object convert(final Object value) {
		try {
			if (value == null) {
				return value;
			}
			if (this.isCollectionValue(value)) {
				return this.convertCollection((COLLECTION) value);
			}
			if (this.isMapValue(value)) {
				return this.convertMap((MAP) value);
			}
			return this.convertValue(value);
		} catch (final Exception e) {
			throw EntityValueConversionFailedException.prepare(value, e);
		}
	}

	private <COLLECTION extends Collection<?>> Collection<?> convertCollection(final COLLECTION collection) throws Exception {
		final Collection<Object> convertedCollection = collection.getClass().newInstance();
		for (final Object item : collection) {
			convertedCollection.add(this.convertValue(item));
		}
		return convertedCollection;
	}

	private <MAP extends Map<?, ?>> Object convertMap(final MAP value) throws Exception {
		final Map<Object, Object> convertedMap = value.getClass().newInstance();
		for (final Entry<? extends Object, ? extends Object> entry : value.entrySet()) {
			convertedMap.put(this.convertValue(entry.getKey()), this.convertValue(entry.getValue()));
		}
		return convertedMap;
	}

	private Object convertValue(final Object value) {
		if (this.isBusinessEntity(value)) {
			this.persistenceBusinessEntityMapper.fetchPersistenceEntity(value);
		}
		if (this.isPersistenceEntity(value)) {
			this.persistenceBusinessEntityMapper.fetchBusinessEntity(value);
		}
		return value;
	}

	private boolean isBusinessEntity(final Object value) {
		return value.getClass().isAnnotationPresent(BusinessEntity.class);
	}

	private boolean isPersistenceEntity(final Object value) {
		return value.getClass().isAnnotationPresent(Entity.class);
	}

	private boolean isCollectionValue(final Object value) {
		return value.getClass().isAssignableFrom(Collection.class);
	}

	private boolean isMapValue(final Object value) {
		return value.getClass().isAssignableFrom(Map.class);
	}
}
