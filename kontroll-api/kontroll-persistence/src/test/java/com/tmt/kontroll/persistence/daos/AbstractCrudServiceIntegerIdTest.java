package com.tmt.kontroll.persistence.daos;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Abstract base implementation of {@link AbstractCrudServiceTest} defining basic values 
 * for entity ids and the number of entities needed for testing dao services of entities with an 
 * id of type (@link Integer}.
 * <p/>
 * The getter methods implemented here may be overriden in case a test needs
 * different values.
 *
 * @param <ENTITY> - the entity this service is for.
 * @param <REPO> - the repository of this entity.
 * @param <S> - the dao service type to test.
 *
 * @author Sergio Weigel
 */
public abstract class AbstractCrudServiceIntegerIdTest<ENTITY extends Object, REPO extends JpaRepository<ENTITY, Integer>, S extends CrudDao<ENTITY, Integer>> extends AbstractCrudServiceTest<ENTITY, REPO, Integer, S> {

	protected static final int	Id_1st				= 777;

	protected static final int	Id_2nd				= 778;

	private static final int		Id_Of_Entity	= 1;

	private final int						numberOfInitialEntities;

	protected AbstractCrudServiceIntegerIdTest() {
		super();
		this.numberOfInitialEntities = 2;
	}

	protected AbstractCrudServiceIntegerIdTest(final int numberOfExtraInitialEntities) {
		super(numberOfExtraInitialEntities);
		this.numberOfInitialEntities = 2;
	}

	protected AbstractCrudServiceIntegerIdTest(	final int numberOfInitialEntities,
																							final int numberOfExtraInitialEntities) {
		super(numberOfExtraInitialEntities);
		this.numberOfInitialEntities = numberOfInitialEntities;
	}

	@Override
	protected Integer getDeleteId() {
		return new Integer(Id_Of_Entity);
	}

	@Override
	protected Integer getExistsId() {
		return new Integer(Id_Of_Entity);
	}

	@Override
	protected Integer getFindId() {
		return new Integer(Id_Of_Entity);
	}

	@Override
	protected Long getNumberOfInitialEntities() {
		return new Long(numberOfInitialEntities);
	}
}
