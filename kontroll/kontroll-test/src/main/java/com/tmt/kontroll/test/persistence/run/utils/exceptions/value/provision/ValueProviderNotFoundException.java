package com.tmt.kontroll.test.persistence.run.utils.exceptions.value.provision;

import java.lang.reflect.Field;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

@SuppressWarnings("serial")
public class ValueProviderNotFoundException extends ContextedRuntimeException {

	private enum ContextValueLabel {
		SimpleValueProviderEntityType(2,0,"entity type"),
		SimpleValueProviderValueType(2,1,"value type"),
		CollectionValueProviderEntityType(3,0,"entity type"),
		CollectionValueProviderCollectionType(3,1,"collection type"),
		CollectionValueProviderItemType(3,2,"item type"),
		MapValueProviderEntityType(4,0,"entity type"),
		MapValueProviderMapType(4,1,"map type"),
		MapValueProviderKeyType(4,2,"key type"),
		MapValueProviderValueType(4,3,"value type");

		private final int numberOfArgs;
		private final int numberOfArg;
		private final String value;
		private ContextValueLabel(final int numberOfArgs,
		                          final int numberOfArg,
		                          final String value) {
			this.numberOfArgs = numberOfArgs;
			this.numberOfArg = numberOfArg;
			this.value = value;
		}
		public static ContextValueLabel byNumbers(final int numberOfArgs,
		                                          final int numberOfArg) {
			for (final ContextValueLabel label : ContextValueLabel.values()) {
				if (label.numberOfArg == numberOfArg &&
				label.numberOfArgs == numberOfArgs) {
					return label;
				}
			}
			throw new RuntimeException("No context value label found.");
		}
		public String value() {
			return this.value;
		}
	}

	public static ValueProviderNotFoundException prepareWithTypes(final Field field, final Class<?>... typeArgs) {
		final ContextedRuntimeException exception = new ValueProviderNotFoundException();
		if (field != null) {
			exception.addContextValue("field name", field.getName());
		}
		for (int i = 0; i < typeArgs.length; i++) {
			final String typeArg = typeArgs[i] == null ? null : typeArgs[i].getName();
			exception.addContextValue(ContextValueLabel.byNumbers(typeArgs.length, i).value(), typeArg);
		}
		return (ValueProviderNotFoundException) exception;
	}

	public static ValueProviderNotFoundException prepareWithValue(final Object entity, final Field field, final Object value) {
		return prepareWithTypes(field, entity.getClass(), value.getClass());
	}
}
