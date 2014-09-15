package com.tmt.kontroll.content.persistence.conversion;

public abstract class ValueStringConverter<V> {

	private static final String nullString = "null";
	
	private ValueStringConverter<?> nextConverter;

	protected abstract boolean isResponsible(final Class<?> valueType);
	
	protected abstract String doConvertToString(final V value);
	
	protected abstract V doConvertToValue(final String string);
	
	@SuppressWarnings("unchecked")
	public String convertToString(final Object value) {
		if (value == null) {
			return nullString;
		}
		final Class<? extends Object> valueType = value.getClass();
		if (this.isResponsible(valueType)) {
			return this.doConvertToString((V) value);
		}
		this.checkNextConverter(valueType);
		return this.getNextConverter().convertToString(value);
	}
	
	public Object convertToValue(final String string, final Class<?> valueType) {
		if (this.isResponsible(valueType)) {
			if (string == null || string.isEmpty() || string.equals(nullString)) {
				return null;
			}
			return this.doConvertToValue(string);
		}
		this.checkNextConverter(valueType);
		return this.getNextConverter().convertToValue(string, valueType);
	}

	protected ValueStringConverter<?> getNextConverter() {
		return nextConverter;
	}

	public void setNextConverter(ValueStringConverter<?> nextConverter) {
		this.nextConverter = nextConverter;
	}
	
	private void checkNextConverter(final Class<?> valueType) {
		if (this.getNextConverter() == null) {
			throw new RuntimeException("Couldn't find converter for " + valueType.getName());
		}
	}
}
