package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.componentOrKeyType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.entityType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.fieldType;
import static com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionTypeConstants.valueType;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

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
	                        final ValueProvisionKind kind,
	                        final Object entity,
	                        final Class<?>... types) throws Exception {
		if (!valueProvisionHandler.canProvideValue(kind, types)) {
			this.prepareArrayValueProvision(valueProvisionHandler, kind, types);
			this.prepareCollectionValueProvision(valueProvisionHandler, kind, entity, types);
			this.prepareIdValueProvision(valueProvisionHandler, kind, types);
			this.prepareEnumValueProvision(valueProvisionHandler, kind, types);
			this.prepareEnumMapValueProvision(valueProvisionHandler, kind, types);
			this.prepareMapValueProvision(valueProvisionHandler, kind, entity, types);
		}
	}

	private void prepareArrayValueProvision(final ValueProvisionHandler valueProvisionHandler, final ValueProvisionKind kind, final Class<?>[] types) {
		if (ValueProvisionKind.OneDimensional == kind && types[fieldType].isArray()) {
			valueProvisionHandler.addValueProvider(ArrayValueProviderFactory.instance().create(valueProvisionHandler, types[componentOrKeyType]));
		}
	}

	private <E> void prepareCollectionValueProvision(final ValueProvisionHandler valueProvisionHandler, final ValueProvisionKind kind, final Object entity, final Class<?>[] types) throws Exception {
		if (ValueProvisionKind.OneDimensional == kind && Collection.class.isAssignableFrom(types[fieldType])) {
			this.prepare(valueProvisionHandler, null, entity, types[entityType], types[componentOrKeyType]);
		}
	}

	private void prepareIdValueProvision(final ValueProvisionHandler valueProvisionHandler, final ValueProvisionKind kind, final Class<?>... types) {
		if (ValueProvisionKind.Id == kind) {
			valueProvisionHandler.addValueProvider(IdValueProviderFactory.instance().create(valueProvisionHandler, types[entityType], types[fieldType]));
		}
	}

	@SuppressWarnings("unchecked")
	private void prepareEnumValueProvision(final ValueProvisionHandler valueProvisionHandler, final ValueProvisionKind kind, final Class<?>[] types) {
		if (ValueProvisionKind.ZeroDimensional == kind && types[fieldType].isEnum()) {
			valueProvisionHandler.addValueProvider(EnumValueProviderFactory.instance().create(valueProvisionHandler, (Class<? extends Enum<?>>) types[fieldType]));
		}
	}

	private void prepareEnumMapValueProvision(final ValueProvisionHandler valueProvisionHandler, final ValueProvisionKind kind, final Class<?>[] types) {
		if (ValueProvisionKind.TwoDimensional == kind && EnumMap.class.isAssignableFrom(types[componentOrKeyType])) {
			valueProvisionHandler.addValueProvider(EnumMapValueProviderFactory.instance().create(valueProvisionHandler, types[componentOrKeyType]));
		}
	}

	private <E> void prepareMapValueProvision(final ValueProvisionHandler valueProvisionHandler, final ValueProvisionKind kind, final E entity, final Class<?>[] types) throws Exception {
		if (ValueProvisionKind.TwoDimensional == kind && Map.class.isAssignableFrom(types[fieldType]) && !EnumMap.class.isAssignableFrom(types[fieldType])) {
			this.prepare(valueProvisionHandler, null, entity, types[entityType], types[componentOrKeyType]);
			this.prepare(valueProvisionHandler, null, entity, types[entityType], types[valueType]);
		}
	}
}
