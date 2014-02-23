package com.tmt.kontroll.test.persistence.run.utils;

import static com.tmt.kontroll.commons.utils.reflection.ClassReflectionUtils.hasConstantWithName;

import com.tmt.kontroll.test.persistence.run.utils.exceptions.EntityClassNotFoundException;

/**
 * Helper class for persistence tests in order to identify the class name of the
 * entity, for which the dao service is to be tested.
 * </p>
 * Generally it is assumed that entities, repositories and services are within the same
 * package, except for the last, which should be named <i>*.entities.*</i>, <i>*.repositories.*</i>,
 * or <i>*.services.*</i> depending on the respective class. It is further assumed that service
 * test classes are put under the same package structure as well, with their simple class name
 * being the same as the service's name followed by the suffix <i>Test</i> and that the service
 * class's simple name is the same as the entity's with either the suffix <i>DaoService</i> or
 * <i>Service</i> (although we'd like to encourage to use the former in order to distinguish it
 * from services on the business layer).
 * </p>
 * If you do not want to follow that convention, you can make entity classes identifiable by
 * setting them as constants in your services class. You may then either follow the pesky old
 * Java standard and name your constant field <i>ENTITY_CLASS_NAME</i> or you can avoid your code
 * yelling at you by naming it <i>entityClassName</i>.
 * </p>
 * 
 * @author Sergio Weigel
 *
 */
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
}
