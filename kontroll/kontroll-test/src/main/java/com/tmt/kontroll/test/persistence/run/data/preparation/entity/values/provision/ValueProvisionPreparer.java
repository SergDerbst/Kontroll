package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.componentOrKeyType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.fieldType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.valueType;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import javax.persistence.Id;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.array.ArrayValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.map.impl.EnumMapValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.enumeration.EnumValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.id.IdValueProviderFactory;

/**
 * The value provision preparer checks if the {@link ValueProvisionHandler} can provide a value for
 * the given field and if not, adds a value provider to the value provision chain. This preparation is
 * necessary for types that cannot be created on the fly, such as arrays or enums. Their value providers
 * need to have knowledge of the exact type they are working for. Therefore they are created using factories
 * that get the types passed to their creation method, which is what the value provision preparer does.
 * </p>
 * It also checks if the component types of collections and the key and value types of maps can be provided
 * and if not, prepares the provision chain accordingly.
 * </p>
 * For fields that are annotated as id fields, it will add an {@link IdValueProvider}.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
public class ValueProvisionPreparer {

	private static class InstanceHolder {
		public static ValueProvisionPreparer instance;
	}

	public static ValueProvisionPreparer newInstance() {
		InstanceHolder.instance = new ValueProvisionPreparer();
		return InstanceHolder.instance;
	}

	private ValueProvisionPreparer() {}

	public <E> void prepare(final ValueProvisionHandler valueProvisionHandler,
	                        final Field field,
	                        final E entity,
	                        final Class<?>... types) throws Exception {
		if (!valueProvisionHandler.canProvideValue(field, types)) {
			this.prepareArrayValueProvision(valueProvisionHandler, field, types);
			this.prepareCollectionValueProvision(valueProvisionHandler, field, entity, types);
			this.prepareIdValueProvision(valueProvisionHandler, field, types);
			this.prepareEnumValueProvision(valueProvisionHandler, field, types);
			this.prepareEnumMapValueProvision(valueProvisionHandler, field, types);
			this.prepareMapValueProvision(valueProvisionHandler, field, entity, types);
		}
	}

	private void prepareArrayValueProvision(final ValueProvisionHandler valueProvisionHandler, final Field field, final Class<?>[] types) {
		if (this.arrayValueNeedsToBeProvided(field, types)) {
			valueProvisionHandler.addValueProvider(ArrayValueProviderFactory.instance().create(valueProvisionHandler, types[componentOrKeyType]));
		}
	}

	private boolean arrayValueNeedsToBeProvided(final Field field, final Class<?>[] types) {
		return
		(field == null && types.length == 3 && types[fieldType].isArray()) ||
		(field != null && field.getType().isArray());
	}

	private <E> void prepareCollectionValueProvision(final ValueProvisionHandler valueProvisionHandler, final Field field, final E entity, final Class<?>[] types) throws Exception {
		if (this.collectionValueNeedsToBeProvided(field, types)) {
			this.prepare(valueProvisionHandler, null, entity, types[entityType], types[componentOrKeyType]);
		}
	}

	private boolean collectionValueNeedsToBeProvided(final Field field, final Class<?>[] types) {
		return
		(field == null && types.length == 3 && Collection.class.isAssignableFrom(types[fieldType])) ||
		(field != null && Collection.class.isAssignableFrom(field.getType()));
	}

	private void prepareIdValueProvision(final ValueProvisionHandler valueProvisionHandler, final Field field, final Class<?>... types) {
		if (field != null && field.isAnnotationPresent(Id.class)) {
			valueProvisionHandler.addValueProvider(IdValueProviderFactory.instance().create(valueProvisionHandler, types[entityType], field.getType()));
		}
	}

	@SuppressWarnings("unchecked")
	private void prepareEnumValueProvision(final ValueProvisionHandler valueProvisionHandler, final Field field, final Class<?>[] types) {
		if (this.enumValueNeedsToBeProvided(field, types)) {
			valueProvisionHandler.addValueProvider(EnumValueProviderFactory.instance().create(valueProvisionHandler, (Class<? extends Enum<?>>) types[fieldType]));
		}
	}

	private boolean enumValueNeedsToBeProvided(final Field field, final Class<?>[] types) {
		return
		(field == null && types.length == 2 && types[fieldType].isEnum()) ||
		(field != null && field.getType().isEnum());
	}

	private void prepareEnumMapValueProvision(final ValueProvisionHandler valueProvisionHandler, final Field field, final Class<?>[] types) {
		if (this.enumMapValueNeedsToBeProvided(field, types)) {
			valueProvisionHandler.addValueProvider(EnumMapValueProviderFactory.instance().create(valueProvisionHandler, types[componentOrKeyType]));
		}
	}

	private boolean enumMapValueNeedsToBeProvided(final Field field, final Class<?>[] types) {
		return
		(field == null && types.length == 4 && EnumMap.class.isAssignableFrom(types[componentOrKeyType])) ||
		(field != null && EnumMap.class.isAssignableFrom(field.getType()));
	}

	private <E> void prepareMapValueProvision(final ValueProvisionHandler valueProvisionHandler, final Field field, final E entity, final Class<?>[] types) throws Exception {
		if (this.mapValueNeedsToBeProvided(field, types)) {
			this.prepare(valueProvisionHandler, null, entity, types[entityType], types[componentOrKeyType]);
			this.prepare(valueProvisionHandler, null, entity, types[entityType], types[valueType]);
		}
	}

	private boolean mapValueNeedsToBeProvided(final Field field, final Class<?>[] types) {
		return
		(field == null && types.length == 4 && Map.class.isAssignableFrom(types[fieldType]) && !EnumMap.class.isAssignableFrom(types[fieldType])) ||
		(field != null && Map.class.isAssignableFrom(field.getType()) && !EnumMap.class.isAssignableFrom(field.getType()));
	}
}
