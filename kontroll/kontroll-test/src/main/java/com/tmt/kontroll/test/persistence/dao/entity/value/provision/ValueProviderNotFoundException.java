package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import org.apache.commons.lang3.exception.ContextedRuntimeException;

@SuppressWarnings("serial")
public class ValueProviderNotFoundException extends ContextedRuntimeException {

	private enum ContextValueLabel {
		SimpleValueProviderValueType(1,0,"value type"),
		CollectionValueProviderCollectionType(2,0,"collection type"),
		CollectionValueProviderItemType(2,1,"item type"),
		MapValueProviderMapType(3,0,"map type"),
		MapValueProviderKeyType(3,1,"key type"),
		MapValueProviderValueType(3,2,"value type");

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

	public static ValueProviderNotFoundException prepare(final Class<?>... typeArgs) {
		return prepare(null, typeArgs);
	}

	public static ValueProviderNotFoundException prepare(final String fieldName, final Class<?>... typeArgs) {
		final ContextedRuntimeException exception = new ValueProviderNotFoundException();
		if (fieldName != null && !fieldName.isEmpty()) {
			exception.addContextValue("field name", fieldName);
		}
		for (int i = 0; i < typeArgs.length; i++) {
			final String typeArg = typeArgs[i] == null ? null : typeArgs[i].getName();
			exception.addContextValue(ContextValueLabel.byNumbers(typeArgs.length, i).value(), typeArg);
		}
		return (ValueProviderNotFoundException) exception;
	}

	public static ValueProviderNotFoundException prepare(final Object value) {
		return prepare(value.getClass());
	}

	public static ValueProviderNotFoundException prepare(final String fieldName, final Object value) {
		return prepare(fieldName, value.getClass());
	}
}
