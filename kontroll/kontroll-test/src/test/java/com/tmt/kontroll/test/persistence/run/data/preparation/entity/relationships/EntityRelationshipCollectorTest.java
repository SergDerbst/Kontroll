package com.tmt.kontroll.test.persistence.run.data.preparation.entity.relationships;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tmt.kontroll.test.persistence.run.PersistenceTestContext;
import com.tmt.kontroll.test.persistence.run.data.TestDataHolder;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

/**
 * <b><i>Note:</i></b>
 * </br>
 * If you encounter a <code>java.lang.VerifyError</code> babbling about some inconsistent stackmap frames,
 * please make sure to follow these <a href="http://blog.triona.de/development/jee/how-to-use-powermock-with-java-7.html">instructions</a>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({PersistenceTestContext.class})
public class EntityRelationshipCollectorTest {

	public static class PrimaryEntity {
		public String meaninglessField;
		@OneToOne
		public OneToOneRelatingEntity oneToOneRelatingEntity;
		@OneToMany(mappedBy = "primaryEntity")
		public List<ManyToOneOwningAndSelfRelatingEntity> manyToOneOwningEntities;
	}
	public static class OneToOneRelatingEntity {
		@OneToOne(mappedBy = "oneToOneRelatingEntity")
		public PrimaryEntity primaryEntityOneToOne;
	}
	public static class ManyToOneOwningAndSelfRelatingEntity {
		@ManyToOne
		public PrimaryEntity primaryEntity;
		@ManyToMany
		public List<ManyToOneOwningAndSelfRelatingEntity> parentRelaters;
		@ManyToMany(mappedBy = "parentRelaters")
		public List<ManyToOneOwningAndSelfRelatingEntity> childRelaters;
	}

	@Mock
	private PersistenceTestContext persistenceContext;

	private TestDataHolder testDataHolder;
	private EntityRelationshipPool entityRelationshipPool;
	private EntityRelationshipCollector toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		mockStatic(PersistenceTestContext.class);
		this.entityRelationshipPool = EntityRelationshipPool.newInstance();
		this.testDataHolder = TestDataHolder.newInstance();
		when(PersistenceTestContext.newInstance()).thenReturn(this.persistenceContext);
		when(PersistenceTestContext.instance()).thenReturn(this.persistenceContext);
		when(this.persistenceContext.entityRelationshipPool()).thenReturn(this.entityRelationshipPool);
		when(this.persistenceContext.testDataHolder()).thenReturn(this.testDataHolder);
		this.toTest = EntityRelationshipCollector.newInstance();
	}

	@Test
	public void testThatCollectWorksForTestStrategyCreate() throws Exception {
		//when
		this.toTest.collect(PrimaryEntity.class, TestStrategy.Create);
		//then
		assertEquals(new Integer(7), (Integer) this.testDataHolder.allReferences().size());
		assertEquals(new Integer(5), (Integer) this.entityRelationshipPool.size());
	}

	@Test
	public void testThatCollectWorksForTestStrategyDelete() throws Exception {
		//when
		this.toTest.collect(PrimaryEntity.class, TestStrategy.Delete);
		//then
		assertEquals(new Integer(7), (Integer) this.testDataHolder.allReferences().size());
		assertEquals(new Integer(5), (Integer) this.entityRelationshipPool.size());
	}

	@Test
	public void testThatCollectWorks() throws Exception {
		//when
		this.toTest.collect(PrimaryEntity.class, TestStrategy.None);
		//then
		assertEquals(new Integer(6), (Integer) this.testDataHolder.allReferences().size());
		assertEquals(new Integer(5), (Integer) this.entityRelationshipPool.size());
	}
}
