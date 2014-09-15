package com.tmt.kontroll.test.persistence.run.data.building.compliance;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.dbunit.dataset.builder.DataSetBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils;

public class TestDataComplianceAssurerTest {

	@Table
	public static class TableDummy {}
	@Table(name = "blablaTable")
	public static class TableNameDummy {
		public String stupidField;
	}
	public static class JoinTableDummy {
		@JoinTable(name = "blubberTable")
		public List<TableDummy> dummies;
		@JoinTable
		public List<TableDummy> moreDummies;
	}

	@Mock
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;
	@Mock
	private LocalContainerEntityManagerFactoryBean entityManager;
	@Mock
	private EntityManagerFactory entityManagerFactory;
	@Mock
	private Metamodel metamodel;
	@Mock
	private EntityType<?> entityType;
	@Mock
	private DataSetBuilder builder;

	private TestDataComplianceAssurer toTest;

	@Before
	@SuppressWarnings({"serial"})
	public void setUp() throws Exception {
		initMocks(this);
		when(this.entityManagerFactoryBean.getNativeEntityManagerFactory()).thenReturn(this.entityManagerFactory);
		when(this.entityManagerFactory.getMetamodel()).thenReturn(this.metamodel);
		when(this.metamodel.getEntities()).thenReturn(new HashSet<EntityType<?>>() {{
			this.add(TestDataComplianceAssurerTest.this.entityType);
		}});
		this.prepareEntityManager();
		this.toTest = TestDataComplianceAssurer.newInstance();
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatAssureTableComplianceWorksForNonNonNamedTableAnnotatedType() throws Exception {
		//given
		when(this.entityType.getBindableJavaType()).thenReturn((Class) TableDummy.class);
		//when
		this.toTest.assureTableCompliance(this.builder);
		//then
		verify(this.builder).ensureTableIsPresent("TableDummy");
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatAssureTableComplianceWorksForNamedTableAnnotatedType() throws Exception {
		//given
		when(this.entityType.getBindableJavaType()).thenReturn((Class) TableNameDummy.class);
		//when
		this.toTest.assureTableCompliance(this.builder);
		//then
		verify(this.builder).ensureTableIsPresent("blablaTable");
	}

	@Test
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void testThatAssureTableComplianceWorksForNotTableButJoinTableAnnotatedType() throws Exception {
		//given
		when(this.entityType.getBindableJavaType()).thenReturn((Class) JoinTableDummy.class);
		//when
		this.toTest.assureTableCompliance(this.builder);
		//then
		verify(this.builder).ensureTableIsPresent("JoinTableDummy");
		verify(this.builder).ensureTableIsPresent("blubberTable");
		verify(this.builder).ensureTableIsPresent("JoinTableDummy_TableDummy");
	}

	@Test
	public void testThatPostConstructionWorks() throws Exception {
		//given
		this.toTest.entityManagerFactoryBean = this.entityManager;
		//when
		this.toTest.setUpTableComplianceAssurance();
		//then
		final Field field = TestDataComplianceAssurer.class.getDeclaredField("entityManager");
		field.setAccessible(true);
		assertEquals(this.entityManager, field.get(this.toTest));
	}

	private void prepareEntityManager() throws Exception {
		final Field field = TestDataComplianceAssurer.class.getDeclaredField("entityManager");
		field.setAccessible(true);
		ClassReflectionUtils.removeFinalModifier(field);
		field.set(null, this.entityManagerFactoryBean);
	}
}
