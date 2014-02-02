package com.tmt.kontroll.test.persistence.run.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.test.persistence.dao.entity.value.incrementation.ValueIncrementor;
import com.tmt.kontroll.test.persistence.dao.entity.value.instantiation.ValueInstantiator;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderFactory;
import com.tmt.kontroll.test.persistence.run.annotations.value.responsibility.ValueHandlingResponsibilityClaimer;
import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

/**
 * An annotation configuring persistence tests. In most cases defining a {@link TestStrategy} and the
 * number of entities to be provided is enough. For specific cases, however, the test may be configured,
 * so that specific {@link ValueProvider}s are created for the test. It should be noted that the value
 * providers are constructed by the {@link ValueProviderFactory} according to the given
 * {@link ValueIncrementor}s, {@link ValueInstantiator}s and {@link ValueHandlingResponsibilityClaimer}s.
 * In doing so, a value provider will consist of these three according to their respective positions within
 * the arrays.
 * </p>
 * For example, the first value provider to be added to the value provision chain will consist of
 * <code>valueIncrementors[0]</code>, <code>valueInstantiators[0]</code> and
 * <code>valueHandlingResponsibilityClaimers[0]</code>. The second will consist of
 * <code>valueIncrementors[1]</code>, <code>valueInstantiators[1]</code> and
 * <code>valueHandlingResponsibilityClaimers[1]</code> and so on.
 * </p>
 * Additionally, straight {@link ValueProvider} implementations can be given as well. These directly
 * implemented value providers will be added to the value provider chain last and, thus, have precedence
 * over those constructed in the way described above.
 * 
 * @author Serg Derbst
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PersistenceTestConfig {

	TestStrategy testStrategy();

	int numberOfEntities() default 1;

	Class<? extends ValueProvider<?>>[] valueProviders() default {};

	Class<? extends ValueIncrementor<?>>[] valueIncrementors() default {};

	Class<? extends ValueInstantiator<?>>[] valueInstantiators() default {};

	ValueHandlingResponsibilityClaimer[] valueHandlingResponsibilityClaimers() default {};
}