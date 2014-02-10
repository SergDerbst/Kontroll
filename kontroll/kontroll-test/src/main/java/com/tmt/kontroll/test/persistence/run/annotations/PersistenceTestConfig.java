package com.tmt.kontroll.test.persistence.run.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;
import com.tmt.kontroll.test.persistence.run.enums.TestStrategy;

/**
 * Annotation for basic test configuration of prepared persistence tests.
 * 
 * @author Serg Derbst
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface PersistenceTestConfig {

	TestStrategy testStrategy();

	int numberOfEntities() default 1;

	Class<? extends ValueProvider<?>>[] valueProviders() default {};

	boolean ignoreEntityId() default false;

	String[] ignoredFields() default {};

	boolean omitDbVerification() default true;
}