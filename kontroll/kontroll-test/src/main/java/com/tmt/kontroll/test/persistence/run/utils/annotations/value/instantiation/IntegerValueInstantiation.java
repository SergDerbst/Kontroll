package com.tmt.kontroll.test.persistence.run.utils.annotations.value.instantiation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IntegerValueInstantiation {

	int value();

	int cardinality() default 0;
}
