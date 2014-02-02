package com.tmt.kontroll.test.persistence.run.assertion;

public enum PersistenceEntityReferenceAssertion {
	EntityType("Entity type."),
	NumberOfEntities("Number of entities."),
	FieldValueOfEntity("Field value of entity.");
	private final String message;
	private PersistenceEntityReferenceAssertion(final String message) {
		this.message = message;
	}
	public String message() {
		return this.message;
	}
}
