package com.tmt.kontroll.test.persistence.dao.entity.value.incrementation;

public interface ValueIncrementor<V> {

	public V increment(final V value);
}
