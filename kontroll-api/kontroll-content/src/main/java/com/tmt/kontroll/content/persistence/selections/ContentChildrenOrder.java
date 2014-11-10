package com.tmt.kontroll.content.persistence.selections;

public enum ContentChildrenOrder {

	ContentBeforeChildren,
	ChildrenBeforeContent;

	public static final String[] valuesAsStrings() {
		final String[] values = new String[ContentChildrenOrder.values().length];
		for (int i = 0; i < ContentChildrenOrder.values().length; i++) {
			values[i] = ContentChildrenOrder.values()[i].name();
		}
		return values;
	}
}
