package com.tmt.kontroll.test.persistence.run.annotations.value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.core.annotation.AnnotationUtils;

import com.tmt.kontroll.test.persistence.dao.entity.value.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.dao.entity.value.responsibility.ValueHandlingResponsibilityClaimer;
import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.annotations.value.responsibility.ValueHandlingResponsibility;

public class TestMethodAnnotationBasedValueProvisionPreparer {

	private static class InstanceHolder {
		public static TestMethodAnnotationBasedValueProvisionPreparer instance = new TestMethodAnnotationBasedValueProvisionPreparer();
	}

	public static TestMethodAnnotationBasedValueProvisionPreparer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new TestMethodAnnotationBasedValueProvisionPreparer();
		}
		return InstanceHolder.instance;
	}

	public <V> void prepare(final Method testMethod, final ValueProvisionHandler valueProvisionHandler) {
		this.prepareAnnotationBasedValueProvision(testMethod, valueProvisionHandler);
		this.prepareDirectlyDeclaredValueProvision(testMethod, valueProvisionHandler);
	}

	private void prepareDirectlyDeclaredValueProvision(final Method testMethod, final ValueProvisionHandler valueProvisionHandler) {
		try {
			final PersistenceTestConfig config = testMethod.getAnnotation(PersistenceTestConfig.class);
			for (final Class<? extends ValueProvider<?>> valueProviderClass : config.valueProviders()) {
				valueProvisionHandler.addValueProvider(valueProviderClass.newInstance());
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <V> void prepareAnnotationBasedValueProvision(final Method testMethod, final ValueProvisionHandler valueProvisionHandler) {
		try {
			testMethod.getAnnotations();
			final ValueHandlingResponsibility responsibility = testMethod.getAnnotation(ValueHandlingResponsibility.class);
			if (responsibility != null) {
				final ValueHandlingResponsibilityClaimer responsibilityClaimer = this.prepareResponsibilityClaim(testMethod);
				final ValueInstantiator<Object> instantiator = this.prepareValueInstantiation(responsibility, testMethod.getAnnotations());
				final ValueProvider<V> valueProvider = (ValueProvider<V>) valueProvisionHandler.fetchValueProviderType(responsibility.fieldName(), responsibility.types()).newInstance();
				valueProvider.setResponsibilityClaimer(responsibilityClaimer);
				valueProvider.setInstantiator((ValueInstantiator<V>) instantiator);
				valueProvisionHandler.addValueProvider(valueProvider);
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <V> ValueInstantiator<V> prepareValueInstantiation(final ValueHandlingResponsibility responsibility, final Annotation[] annotations) {
		for (final Annotation annotation : annotations) {
			if (this.isValidValueInstantiation(responsibility, annotation)) {
				return new ValueInstantiator<V>() {
					@SuppressWarnings("unchecked")
					@Override
					public V instantiate() {
						return (V) AnnotationUtils.getValue(annotation);
					}
				};
			}
		}
		return null;
	}

	private boolean isValidValueInstantiation(final ValueHandlingResponsibility responsibility, final Annotation annotation) {
		boolean isValid = false;
		final boolean isInstantiationAnnotation = annotation.annotationType().getSimpleName().endsWith("ValueInstantiation");
		if (isInstantiationAnnotation) {
			for (final Class<?> type : responsibility.types()) {
				final Object value = AnnotationUtils.getValue(annotation);
				if (value != null && type.isAssignableFrom(value.getClass())) {
					isValid = true;
				}
			}
		}
		return isValid;
	}

	private ValueHandlingResponsibilityClaimer prepareResponsibilityClaim(final Method testMethod) {
		final ValueHandlingResponsibility responsibility = testMethod.getAnnotation(ValueHandlingResponsibility.class);
		return new ValueHandlingResponsibilityClaimer() {
			@Override
			public boolean claimResponsibility(final String fieldName, final Class<?>... types) {
				return responsibility.fieldName().equals(fieldName) && Arrays.equals(responsibility.types(), types);
			}
		};
	}
}
