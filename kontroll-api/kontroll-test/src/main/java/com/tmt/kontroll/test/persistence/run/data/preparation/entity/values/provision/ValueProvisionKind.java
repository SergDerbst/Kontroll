package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision;

/**
 * Represents the kind of value that is to be provided.
 * 
 * @author Sergio Weigel
 *
 */
public enum ValueProvisionKind {
	/**
	 * A value is to be provided as entity id.
	 */
	Id,
	/**
	 * A simple value of any type is to be provided.
	 */
	ZeroDimensional,
	/**
	 * A one dimensional value is to be provided. This can be an array or a collection.
	 */
	OneDimensional,
	/**
	 * A two dimensional value is to be provided. This is usually a map.
	 */
	TwoDimensional;
}
