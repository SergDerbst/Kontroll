package com.tmt.kontroll.test.persistence.run.utils;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.hasConstantWithName;

import java.lang.reflect.Method;

import com.tmt.kontroll.test.persistence.dao.PersistenceEntityDaoServiceTest;
import com.tmt.kontroll.test.persistence.run.exceptions.EntityClassNotFoundException;

public class PersistenceDaoEntityTestHelper {

	private static final String errorMessage = "Could not determine entity class.";
	private static final String daoServiceIdentifyingSuffix = "DaoServiceTest";
	private static final String serviceIdentifyingSuffix = "ServiceTest";
	private static final String entityClassNameConstantName = "entityClassName";
	private static final String entityClassNameConstantNameClassic = "ENTITY_CLASS_NAME";

	public static String retrieveEntityClassName(final Class<?> serviceTestClass) {
		try {
			if (serviceTestClass.getName().endsWith(daoServiceIdentifyingSuffix)) {
				return retrieveClassNameFromClassAndSuffix(serviceTestClass, daoServiceIdentifyingSuffix);
			}
			if (serviceTestClass.getName().endsWith(serviceIdentifyingSuffix)) {
				return retrieveClassNameFromClassAndSuffix(serviceTestClass, serviceIdentifyingSuffix);
			}
			if (hasConstantWithName(serviceTestClass, entityClassNameConstantName)) {
				return retrieveClassNameFromFieldName(serviceTestClass, entityClassNameConstantName);
			}
			if (hasConstantWithName(serviceTestClass, entityClassNameConstantNameClassic)) {
				return retrieveClassNameFromFieldName(serviceTestClass, entityClassNameConstantNameClassic);
			}
			throw new EntityClassNotFoundException(errorMessage);
		} catch (final EntityClassNotFoundException e) {
			throw e;
		} catch (final Exception e) {
			throw new EntityClassNotFoundException(errorMessage, e);
		}
	}

	private static String retrieveClassNameFromFieldName(final Class<?> serviceTestClass, final String fieldName) throws Exception {
		return (String) serviceTestClass.getField(fieldName).get(null);
	}

	private static String retrieveClassNameFromClassAndSuffix(final Class<?> serviceTestClass, final String suffix) {
		return serviceTestClass.getName().replace("services", "entities").replace(suffix, "");
	}

	/**
	 * Returns the method object of the currently running test method, if it is called from within that
	 * method and if the given argument is the actual test class.
	 * </p>
	 * <b>Note: </b>The <code>callsFromTestMethod</code> parameter represents the number of method calls
	 * from the test to be retrieved to this method, starting with zero. This is needed to make sure that
	 * the correct method name is found within the stack trace.
	 * </br>
	 * If this method is called directly from the test method, the number of calls must be zero in order
	 * to retrieve the correct method object. If there is a delegating helper method inbetween such as
	 * {@link PersistenceEntityDaoServiceTest#fetchReferences}, the number of calls must be one in order
	 * to retrieve the correct method object, and so son.
	 * </p>
	 * This clearly is not the most beautiful hack there is, but necessary to assure test data integrity
	 * for prepared persistence tests.
	 * </p>
	 * 
	 * @param currentTestClass
	 * @param callsFromTestMethod
	 * @return
	 * @throws Exception
	 */
	public static Method retrieveCurrentTestMethod(final Class<?> currentTestClass, final int callsFromTestMethod) throws Exception {
		final StackTraceElement stackTraceElements[] = (new Throwable()).getStackTrace();
		final String[] stackTraceElementSplit = stackTraceElements[callsFromTestMethod + 1].toString().split("\\(")[0].split("\\.");
		return currentTestClass.getMethod(stackTraceElementSplit[stackTraceElementSplit.length - 1], (Class<?>[]) null);
	}
}
