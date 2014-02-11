package com.tmt.kontroll.test.persistence.run.utils.annotations.value.responsibility;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvider;

/**
 * Annotation for configuring a {@link ValueProvider}, so that it will claim responsibility,
 * if the given field name and class types match the annotation's values.
 * 
 * @author Serg Derbst
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ValueHandlingResponsibility {

	String fieldName() default "";

	Class<?>[] types() default {};

	int cardinality() default 0;
}
