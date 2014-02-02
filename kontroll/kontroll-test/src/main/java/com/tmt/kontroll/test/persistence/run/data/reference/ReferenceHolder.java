package com.tmt.kontroll.test.persistence.run.data.reference;

import java.util.ArrayList;
import java.util.List;

public class ReferenceHolder {

	private static class InstanceHolder {
		public static ReferenceHolder instance = new ReferenceHolder();
	}

	public static ReferenceHolder instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ReferenceHolder();
		}
		return InstanceHolder.instance;
	}

	private final List<Reference> references = new ArrayList<>();

	public void clearReferences() {
		this.references.clear();
	}

	public void addReference(final Reference reference) {
		this.references.add(reference);
	}

	public List<Reference> getReferences() {
		return this.references;
	}
}
