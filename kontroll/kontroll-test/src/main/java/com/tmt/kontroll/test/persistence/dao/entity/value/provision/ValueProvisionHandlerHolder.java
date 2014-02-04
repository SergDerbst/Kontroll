package com.tmt.kontroll.test.persistence.dao.entity.value.provision;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds {@link ValueProvisionHandler} instances for persistence tests in a map, identified
 * by string keys (e.g. usually the test method name). If a chain is requested with an
 * identifier key that does not exist yet, a new instance will be added to the internal map
 * and returned. The internal map can (and should) be cleared before running a test class.
 * 
 * @author Serg Derbst
 */
public class ValueProvisionHandlerHolder {

	private static class InstanceHolder {
		public static ValueProvisionHandlerHolder instance = new ValueProvisionHandlerHolder();
	}

	public static ValueProvisionHandlerHolder instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new ValueProvisionHandlerHolder();
		}
		return InstanceHolder.instance;
	}

	private static final Map<String, ValueProvisionHandler> valueProvisionHandlerMap = new HashMap<>();

	/**
	 * Returns a {@link ValueProvisionHandler} instance identified by the given class and method.
	 * 
	 * @param testMethod
	 * @return
	 */
	public ValueProvisionHandler fetchValueProvisionHandler(final Class<?> testClass, final Method testMethod) {
		return this.fetchValueProvisionChain(testClass.getName() + "." + testMethod.getName());
	}

	/**
	 * Returns a {@link ValueProvisionHandler} instance identified by the given string value.
	 * 
	 * @param valueProvisionIdentifier
	 * @return
	 */
	public ValueProvisionHandler fetchValueProvisionChain(final String valueProvisionIdentifier) {
		if (!valueProvisionHandlerMap.containsKey(valueProvisionIdentifier)) {
			valueProvisionHandlerMap.put(valueProvisionIdentifier, new ValueProvisionHandler());
		}
		return valueProvisionHandlerMap.get(valueProvisionIdentifier);
	}

	/**
	 * Clears the internal map.
	 */
	public void clear() {
		valueProvisionHandlerMap.clear();
	}
}
