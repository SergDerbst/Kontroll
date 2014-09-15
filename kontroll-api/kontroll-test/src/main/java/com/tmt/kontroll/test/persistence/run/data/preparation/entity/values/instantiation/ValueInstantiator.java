package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.instantiation;

public interface ValueInstantiator<V> {

	/**
	 * Instantiates a value.
	 * 
	 * @param entity - the entity object to instantiate the value for
	 * @return
	 */
	public V instantiate(Object entity);
}
