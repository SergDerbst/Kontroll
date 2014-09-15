package com.tmt.kontroll.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

/**
 * General JUnit test to help achieve 100% code coverage by testing exceptions.
 * It scans the classpath for exception classes and tests the initialization via 
 * their standard constructors. Unless there is some other code within the exceptions,
 * one test case per module should suffice.
 * 
 * @author Sergio Weigel
 *
 */
public abstract class ExceptionTest {

	private static final RuntimeException cause = new RuntimeException();
	private static final String message = "dumb exception message";
	private static final Boolean enableSuppression = false;
	private static final Boolean writableStackTrace = false;
	
	private static final boolean useDefaultFilters = false;

	private final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(useDefaultFilters);
	final List<String> basePackages;
	
	protected ExceptionTest(final List<String> basePackages) {
		this.basePackages = basePackages;
	}
	
	@Before
	public void setUp() {
		this.scanner.resetFilters(useDefaultFilters);
		this.scanner.addIncludeFilter(new AssignableTypeFilter(Exception.class));
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testExceptionInitialization() throws Exception {
		for (final Class<? extends Exception> exceptionClass : this.fetchExceptionsToTest()) {
			for (final Constructor<?> constructor : exceptionClass.getConstructors()) {
				this.initializeAndCheckException((Constructor<? extends Exception>) constructor);
			}
		}
	}
	
	protected void initializeAndCheckException(final Constructor<? extends Exception> constructor) throws Exception {
		final Class<?>[] parameterTypes = constructor.getParameterTypes();
		switch(parameterTypes.length) {
			case 0:
				Exception exception = constructor.getDeclaringClass().newInstance();
				assertNotNull(this.constructNullFailureMessage(constructor.getDeclaringClass()), exception);
				break;
			case 1:
				if (String.class.equals(parameterTypes[0].getClass())) {
					exception = constructor.newInstance(message);
					assertNotNull(this.constructNullFailureMessage(constructor.getDeclaringClass()), exception);
					assertEquals(this.constructParameterFailureMessage(exception, "message"), message, exception.getMessage());
				}
				if (Throwable.class.equals(parameterTypes[0].getClass())) {
					exception = constructor.newInstance(cause);
					assertNotNull(this.constructNullFailureMessage(constructor.getDeclaringClass()), exception);
					assertEquals(this.constructParameterFailureMessage(exception, "cause"), cause, exception.getCause());
				}
				break;
			case 2:
				exception = constructor.newInstance(message, cause);
				assertNotNull(this.constructNullFailureMessage(constructor.getDeclaringClass()), exception);
				assertEquals(this.constructParameterFailureMessage(exception, "message"), message, exception.getMessage());
				assertEquals(this.constructParameterFailureMessage(exception, "cause"), cause, exception.getCause());
				break;
			case 4:
				exception = constructor.newInstance(message, cause, enableSuppression, writableStackTrace);
				assertNotNull(this.constructNullFailureMessage(constructor.getDeclaringClass()), exception);
				assertEquals(this.constructParameterFailureMessage(exception, "message"), message, exception.getMessage());
				assertEquals(this.constructParameterFailureMessage(exception, "cause"), cause, exception.getCause());
				break;
			default:
				fail("Unkown number of parameters for exception: " + constructor.getDeclaringClass().getName());
		}
	}
	
	private String constructNullFailureMessage(final Class<? extends Exception> exceptionClass) {
		return "Failed to initialize " + exceptionClass.getName();
	}
	
	private String constructParameterFailureMessage(final Exception exception, final String parameter) {
		return "Failed to initialize " + exception.getClass().getName() + " with parameter " + parameter;
	}

	@SuppressWarnings("unchecked")
	private Set<Class<? extends Exception>> fetchExceptionsToTest() throws Exception {
		final Set<Class<? extends Exception>> set = new HashSet<Class<? extends Exception>>();
		for (final String basePackage : this.basePackages) {
			for (BeanDefinition beanDefinition : this.scanner.findCandidateComponents(basePackage)) {
				set.add((Class<? extends Exception>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader()));
			}
		}
		return set;
	}
}
