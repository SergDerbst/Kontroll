package com.tmt.kontroll.test.persistence.run.utils.enums;


/**
 * Represents the strategy of persistence tests. See the documentation
 * of the single enum values for more information on test strategies.
 * 
 * @author Serg Derbst
 *
 */
public enum TestStrategy {
	/**
	 * Test strategy to test whether the read and read all operations work.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity exists in the data base.</li>
	 * <li><b>Test: </b>That all existing entities can be read.</li>
	 * <li><b>Db verification: </b>That data remains unchanged.</li>
	 * </ul>
	 */
	Read,
	/**
	 * Test strategy to test whether the create and create all operations work for new entities.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>No entity has to exist in the data base.</li>
	 * <li><b>Test: </b>That at least one new entity is being created correctly.</li>
	 * <li><b>Db verification: </b>The new entities exist in the database.</li>
	 * </ul>
	 */
	Create,
	/**
	 * Test strategy to test whether the save and save all operations work for existing entities.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity has to exist in the databse.</li>
	 * <li><b>Test: </b>That an existing entity can be found, changed and saved correctly.</li>
	 * <li><b>Db verification: </b>The updated entity exists in the database.</li>
	 * </ul>
	 */
	Update,
	/**
	 * Test strategy to test whether the delete operation works.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity exists in the data base.</li>
	 * <li><b>Test: </b>That the first existing entity is being deleted.</li>
	 * <li><b>Db verification: </b>The first of the initial entities has been removed.</li>
	 * </ul>
	 */
	Delete,
	/**
	 * Test strategy to test whether the count operation works.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity exists in the data base.</li>
	 * <li><b>Test: </b>That count returns the correct number of entities.</li>
	 * <li><b>Db verification: </b>That data remains unchanged.</li>
	 * </ul>
	 */
	Count,
	/**
	 * Test strategy to test whether the exists operation works.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity exists in the data base.</li>
	 * <li><b>Test: </b>That the first entity exists.</li>
	 * <li><b>Db verification: </b>That data remains unchanged.</li>
	 * </ul>
	 */
	Exists,
	/**
	 * Test strategy to test whether violations of unique constraints defined in an entity's
	 * {@link Table} annotation cause the proper errors to be thrown.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity has to exist in the databse.</li>
	 * <li><b>Test: </b>That an entity cannot be saved when it violates said constraints.</li>
	 * <li><b>Db verification: </b>That the database did not change.</li>
	 * </ul>
	 */
	UniqueConstraintsOnTable,
	/**
	 * Test strategy to test whether violations of unique constraints defined in an entity field's
	 * {@link Column} annotation cause the proper errors to be thrown.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity has to exist in the databse.</li>
	 * <li><b>Test: </b>That an entity cannot be saved when it violates said constraints.</li>
	 * <li><b>Db verification: </b>That the database did not change.</li>
	 * </ul>
	 */
	UniqueConstraintsOnColumn,
	/**
	 * Test strategy to test whether violations of nullable constraints defined on an entity field's
	 * {@link Column} annotation cause the proper errors to be thrown.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity has to exist in the databse.</li>
	 * <li><b>Test: </b>That an entity cannot be saved when it violates said constraints.</li>
	 * <li><b>Db verification: </b>That the database did not change.</li>
	 * </ul>
	 */
	NullableConstraint,
	/**
	 * Test strategy to test whether violations of length constraints defined on an entity field's
	 * {@link Column} annotation cause the proper errors to be thrown.
	 * </p>
	 * <ul>
	 * <li><b>Db setup: </b>At least one entity has to exist in the databse.</li>
	 * <li><b>Test: </b>That an entity cannot be saved when it violates said constraints.</li>
	 * <li><b>Db verification: </b>That the database did not change.</li>
	 * </ul>
	 */
	LengthConstraint;
}