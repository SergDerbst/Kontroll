package com.tmt.kontroll.test.persistence.run;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;

import org.dbunit.database.IDatabaseConnection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.context.TestContext;

import com.tmt.kontroll.test.persistence.run.KontrollDbUnitTestExecutionListener.KontrollDbUnitTestContext;
import com.tmt.kontroll.test.persistence.run.utils.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.utils.enums.TestStrategy;

public class KontrollDbUnitTestExecutionListenerTest {

	public static class NoPersistenceTest {
		public void testMethod() {}
	}
	public static class OmitVerificationDaoServiceTest {
		@PersistenceTestConfig(testStrategy = TestStrategy.Count, omitDbVerification = true)
		public void testMethod(){}
	}
	public static class DontOmitVerificationDaoServiceTest {
		@PersistenceTestConfig(testStrategy = TestStrategy.Count, omitDbVerification = false)
		public void testMethod(){}
	}

	@Mock
	private TestContext testContext;
	@Mock
	private KontrollDbUnitRunner runner;
	@Mock
	private IDatabaseConnection connection;

	private KontrollDbUnitTestExecutionListener toTest;

	@Before
	public void setUp() throws Exception {
		initMocks(this);
		when(this.testContext.getAttribute(any(String.class))).thenReturn(this.connection);
		this.prepareClassToTest();
	}

	@Test
	public void testThatBeforeTestMethodWorks() throws Exception {
		//given
		final Method testMethod = OmitVerificationDaoServiceTest.class.getMethod("testMethod");
		when(this.testContext.getTestMethod()).thenReturn(testMethod);
		//when
		this.toTest.beforeTestMethod(this.testContext);
		//then
		verify(this.runner).beforeTestMethod(any(KontrollDbUnitTestContext.class), eq(testMethod));
	}

	@Test
	public void testThatAfterTestMethodWorksWithVerification() throws Exception {
		//given
		final Method testMethod = DontOmitVerificationDaoServiceTest.class.getMethod("testMethod");
		when(this.testContext.getTestMethod()).thenReturn(testMethod);
		//when
		this.toTest.afterTestMethod(this.testContext);
		//then
		verify(this.runner).afterTestMethod(any(KontrollDbUnitTestContext.class), eq(testMethod), eq(false));
	}

	@Test
	public void testThatAfterTestMethodWorksWithoutVerification() throws Exception {
		//given
		final Method testMethod = OmitVerificationDaoServiceTest.class.getMethod("testMethod");
		when(this.testContext.getTestMethod()).thenReturn(testMethod);
		//when
		this.toTest.afterTestMethod(this.testContext);
		//then
		verify(this.runner).afterTestMethod(any(KontrollDbUnitTestContext.class), eq(testMethod), eq(true));
	}

	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testThatParentAfterTestMethodIsCalledOnNonPersistenceTests() throws Exception {
		//given
		final Method testMethod = NoPersistenceTest.class.getMethod("testMethod");
		when(this.testContext.getTestClass()).thenReturn((Class) NoPersistenceTest.class);
		when(this.testContext.getTestMethod()).thenReturn(testMethod);
		//when
		this.toTest.afterTestMethod(this.testContext);
		//then
		verify(this.connection).close();
	}

	@Test
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void testThatParentBeforeTestMethodIsCalledOnNonPersistenceTests() throws Exception {
		//given
		final Method testMethod = NoPersistenceTest.class.getMethod("testMethod");
		when(this.testContext.getTestClass()).thenReturn((Class) NoPersistenceTest.class);
		when(this.testContext.getTestMethod()).thenReturn(testMethod);
		//when
		this.toTest.beforeTestMethod(this.testContext);
		//then
		verify(this.testContext).getAttribute(any(String.class));
	}

	private void prepareClassToTest() {
		this.toTest = new KontrollDbUnitTestExecutionListener();
		this.toTest.runner = this.runner;
	}
}
