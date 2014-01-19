package com.tmt.kontroll.test.persistence.dao.entity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tmt.kontroll.test.config.TestConfig;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.array.ArrayValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.CollectionValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class EntityInstanceProviderTest {

	public static class TestEntitySimpleFields {
		public boolean booleanField;
		public byte byteField;
		public char characterField;
		public double doubleField;
		public float floatField;
		public int integerField;
		public long longField;
		public Locale localeField;
		public short shortField;
		public String stringField;
		public Timestamp timestampField;
	}

	public static class TestEntityStaticFinalFields {
		public static final String staticFinalField = "blubb";
		public static String staticField;
		public final String finalField = "blah";
	}

	public static class TestEntityInherited extends TestEntitySimpleFields{}

	public static class TestEntityCollectionFields {
		public List<String> listStringField;
		public Set<Locale> setLocaleField;
		public SortedSet<Integer> sortedSetIntegerField;
	}

	@Autowired
	ArrayValueProvisionHandler arrayValueProvisionHandler;
	@Autowired
	CollectionValueProvisionHandler collectionValueProvisionHandler;
	@Autowired
	private SimpleValueProvisionHandler simpleValueProvisionHandler;
	@Autowired
	private EntityInstanceProvider toTest;

	private long referenceTimestampValue;

	@Before
	public void setUp() {
		this.arrayValueProvisionHandler.reset();
		this.collectionValueProvisionHandler.reset();
		this.simpleValueProvisionHandler.reset();
		this.referenceTimestampValue = ((Timestamp) this.simpleValueProvisionHandler.provide(Timestamp.class)).getTime();
	}

	@Test
	public void testThatEntityIsProvidedWithSimpleValues() {
		//when
		final TestEntitySimpleFields provided = (TestEntitySimpleFields) this.toTest.provide(TestEntitySimpleFields.class);
		//then
		assertNotNull(provided);
		assertEquals(false, provided.booleanField);
		assertEquals(Byte.parseByte("0"), provided.byteField);
		assertEquals("0".charAt(0), provided.characterField);
		assertEquals(new  Double(0.0), (Double) provided.doubleField);
		assertEquals(new Float((float) 0.0), (Float) provided.floatField);
		assertEquals(0, provided.integerField);
		assertEquals(0, provided.longField);
		assertEquals(Locale.GERMAN, provided.localeField);
		assertEquals((short) 0, provided.shortField);
		assertEquals("0", provided.stringField);
		assertEquals(this.referenceTimestampValue + 1, provided.timestampField.getTime());
	}

	@Test
	public void testThatFieldsOfSuperClassAreHandledProperly() {
		//when
		final TestEntitySimpleFields provided = (TestEntitySimpleFields) this.toTest.provide(TestEntityInherited.class);
		//then
		assertNotNull(provided);
		assertEquals(false, provided.booleanField);
		assertEquals(Byte.parseByte("0"), provided.byteField);
		assertEquals("0".charAt(0), provided.characterField);
		assertEquals(new  Double(0.0), (Double) provided.doubleField);
		assertEquals(new Float((float) 0.0), (Float) provided.floatField);
		assertEquals(0, provided.integerField);
		assertEquals(0, provided.longField);
		assertEquals(Locale.GERMAN, provided.localeField);
		assertEquals((short) 0, provided.shortField);
		assertEquals("0", provided.stringField);
		assertEquals(this.referenceTimestampValue + 1, provided.timestampField.getTime());
	}

	@Test
	public void testThatStaticAndFinalFieldsAreHandledProperly() {
		//when
		final TestEntityStaticFinalFields provided = (TestEntityStaticFinalFields) this.toTest.provide(TestEntityStaticFinalFields.class);
		//then
		assertNotNull(provided);
		assertEquals("blubb", TestEntityStaticFinalFields.staticFinalField);
		assertEquals("0", TestEntityStaticFinalFields.staticField);
		assertEquals("blah", provided.finalField);
	}

	@Test
	public void testThatEntityIsProvidedWithCollectionValues() {
		//when
		final TestEntityCollectionFields provided = (TestEntityCollectionFields) this.toTest.provide(TestEntityCollectionFields.class);
		assertNotNull(provided);

		assertNotNull(provided.listStringField);
		assertFalse(provided.listStringField.isEmpty());
		assertEquals(1, provided.listStringField.size());
		assertEquals("0", provided.listStringField.get(0));

		assertNotNull(provided.setLocaleField);
		assertFalse(provided.setLocaleField.isEmpty());
		assertEquals(1, provided.setLocaleField.size());
		assertTrue(provided.setLocaleField.contains(Locale.GERMAN));

		assertNotNull(provided.sortedSetIntegerField);
		assertFalse(provided.sortedSetIntegerField.isEmpty());
		assertEquals(1, provided.sortedSetIntegerField.size());
		assertEquals(new Integer(0), provided.sortedSetIntegerField.first());
	}
}
