package com.tmt.kontroll.test.persistence.run.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.test.persistence.run.utils.TestStrategy;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DbSetup {

	TestStrategy strategy() default TestStrategy.Provided;

	int numberOfEntities() default 1;
}