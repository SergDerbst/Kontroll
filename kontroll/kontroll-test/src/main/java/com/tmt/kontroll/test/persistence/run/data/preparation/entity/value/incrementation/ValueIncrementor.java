package com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.incrementation;

public interface ValueIncrementor<V> {

	public V increment(final V value);
}
