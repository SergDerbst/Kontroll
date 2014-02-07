package com.tmt.kontroll.test.persistence.run.annotations.value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.core.annotation.AnnotationUtils;

import com.tmt.kontroll.test.persistence.run.annotations.PersistenceTestConfig;
import com.tmt.kontroll.test.persistence.run.annotations.value.responsibility.ValueHandlingResponsibility;
import com.tmt.kontroll.test.persistence.run.data.preparation.TestPreparationContext;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.value.responsibility.ValueHandlingResponsibilityClaimer;

public class AnnotationBasedValueProvisionPreparer {

	private static class InstanceHolder {
		public static AnnotationBasedValueProvisionPreparer instance;
	}

	public static AnnotationBasedValueProvisionPreparer newInstance() {
		InstanceHolder.instance = new AnnotationBasedValueProvisionPreparer();
		return InstanceHolder.instance;
	}

	private AnnotationBasedValueProvisionPreparer() {}

	public <V> void prepare(final Method testMethod) {
		this.prepareAnnotationBasedValueProvision(testMethod);
		this.prepareDirectlyDeclaredValueProvision(testMethod);
	}

	private void prepareDirectlyDeclaredValueProvision(final Method testMethod) {
		try {
			final PersistenceTestConfig config = testMethod.getAnnotation(PersistenceTestConfig.class);
			for (final Class<? extends ValueProvider<?>> valueProviderClass : config.valueProviders()) {
				this.valueProvisionHandler().addValueProvider(valueProviderClass.newInstance());
			}
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private <V> void prepareAnnotationBasedValueProvision(final Method testMethod) {
		try {
			testMethod.getAnnotations();
			final ValueHandlingResponsibility responsibility = testMethod.getAnnotation(ValueHandlingResponsibility.class);
			if (responsibility != null) {
				final ValueHandlingResponsibilityClaimer responsibilityClaimer = this.prepareResponsibilityClaim(testMethod);
				final ValueInstantiator<Object> instantiator = this.prepareValueInstantiation(responsibility, testMethod.getAnnotations());
				final ValueProvisionHandler valueProvisionHandler = this.valueProvisionHandler();
				final Class<? extends ValueProvider<V>> valueProviderType = (Class<? extends ValueProvider<V>>) valueProvisionHandler.fetchValueProviderType(responsibility.fieldName(), responsibility.types());
				final ValueProvider<V> valueProvider = valueProviderType.getConstructor(ValueProvisionHandler.class).newInstance(valueProvisionHandler);
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

	private ValueProvisionHandler valueProvisionHandler() {
		return TestPreparationContext.instance().valueProvisionHandler();
	}
}
