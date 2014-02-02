package com.tmt.kontroll.test.persistence.run.assertion;

import java.util.ArrayList;
import java.util.List;

public class PersistenceEntityReferenceHolder {

	private static List<PersistenceEntityReference> references = new ArrayList<>();

	public static void clearReferences() {
		references.clear();
	}

	public static void addReference(final PersistenceEntityReference reference) {
		references.add(reference);
	}

	public static List<PersistenceEntityReference> getReferences() {
		return references;
	}
}
