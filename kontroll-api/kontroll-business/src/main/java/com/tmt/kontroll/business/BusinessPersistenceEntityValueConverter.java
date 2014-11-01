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
 * The entity value converter checks if a value has to be converted at all and
 * handles the possible occurence of entity objects in collections and maps. A
 * value has to be converted, if it is not null and if one of the following conditions
 * is true.
 * <ul>
 * <li>it is an entity itself (is annotated with either {@link BusinessEntity} or {@link Entity}</li>
 * <li>it is a non-empty collection of entity objects</li>
 * <li>it is a non-empty map of either keys or values or both being entity objects</li>
 * </ul>
 * If a value is not to be converted, it will just be returned as it is.
 * </p>
 * <p>
 * The converter will thus recognize the occurence of entities in collections and maps
 * and, if needed, instantiate the proper collection or map instances before calling the
 * {@link BusinessPersistenceEntityMapper} to fetch the proper opposite entities.
 * </p>
 * <p>
 * <i>Note: </i>The converter is being called recursively by the {@link BusinessPersistenceEntityMapper}
 * to assure that if any field refers to other entity objects, they are being properly handled
 * as well.
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
			if (this.isToBeConverted(value)) {
				if (this.isCollectionValue(value)) {
					return this.convertCollection((COLLECTION) value);
				}
				if (this.isMapValue(value)) {
					return this.convertMap((MAP) value);
				}
				return this.convert(value);
			}
			return value;
		} catch (final Exception e) {
			throw EntityValueConversionFailedException.prepare(value, e);
		}
	}

	private <MAP extends Map<?, ?>> Object convertMap(final MAP value) throws Exception {
		final Map<Object, Object> convertedMap = value.getClass().newInstance();
		for (final Entry<? extends Object, ? extends Object> entry : value.entrySet()) {
			convertedMap.put(this.convertValue(entry.getKey()), this.convertValue(entry.getValue()));
		}
		return convertedMap;
	}

	private <COLLECTION extends Collection<?>> Collection<?> convertCollection(final COLLECTION collection) throws Exception {
		final Collection<Object> convertedCollection = collection.getClass().newInstance();
		for (final Object item : collection) {
			convertedCollection.add(this.convertValue(item));
		}
		return convertedCollection;
	}

	private Object convertValue(final Object value) {
		return this.persistenceBusinessEntityMapper.fetchConvertedEntity(value);
	}

	private boolean isToBeConverted(final Object value) {
		return value != null && (this.isEntityValue(value) || this.isEntityCollectionValue(value) || this.isEntityMapValue(value));
	}

	private boolean isEntityValue(final Object value) {
		return value.getClass().isAnnotationPresent(BusinessEntity.class) || value.getClass().isAnnotationPresent(Entity.class);
	}

	private boolean isEntityCollectionValue(final Object value) {
		return this.isCollectionValue(value) && !((Collection<?>) value).isEmpty() && this.isEntityValue(((Collection<?>) value).iterator().next());
	}

	private boolean isEntityMapValue(final Object value) {
		return this.isMapValue(value) && !((Map<?, ?>) value).isEmpty() && (this.isEntityMapValue(((Map<?, ?>) value).entrySet().iterator().next().getKey()) || this.isEntityMapValue(((Map<?, ?>) value).entrySet().iterator().next().getValue()));
	}

	private boolean isCollectionValue(final Object value) {
		return value.getClass().isAssignableFrom(Collection.class);
	}

	private boolean isMapValue(final Object value) {
		return value.getClass().isAssignableFrom(Map.class);
	}
}
