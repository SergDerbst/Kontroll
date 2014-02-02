package com.tmt.kontroll.test.persistence.run.utils;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.hasConstantWithName;

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
}
