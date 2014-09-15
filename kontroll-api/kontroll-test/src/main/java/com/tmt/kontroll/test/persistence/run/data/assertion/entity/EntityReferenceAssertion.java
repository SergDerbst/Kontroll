package com.tmt.kontroll.test.persistence.run.data.assertion.entity;

public enum EntityReferenceAssertion {
	EntityType("Entity type."),
	NumberOfEntities("Number of entities."),
	FieldValueOfEntity("Field value of entity.");

	private final String message;

	private EntityReferenceAssertion(final String message) {
		this.message = message;
	}

	public String message() {
		return this.message;
	}
}
