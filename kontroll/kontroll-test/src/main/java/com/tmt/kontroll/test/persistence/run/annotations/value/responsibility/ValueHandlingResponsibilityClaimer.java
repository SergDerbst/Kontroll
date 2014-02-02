package com.tmt.kontroll.test.persistence.run.annotations.value.responsibility;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProvider;

/**
 * Annotation for configuring a {@link ValueProvider}, so that it will claim responsibility,
 * if the given field name and class types match the annotation's values.
 * 
 * @author Serg Derbst
 */
public @interface ValueHandlingResponsibilityClaimer {

	String fieldName() default "";

	Class<?>[] types() default {};
}
