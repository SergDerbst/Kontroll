package com.tmt.kontroll.business.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * <p>
 * Indicates that the annotated class is a specialization of a Spring {@Component} and
 * a special Kontroll dao service, which will be handled by the {BusinessServiceAspect},
 * so that all entities returned from or passed to any public method of that service will
 * be converted to or from business entities to persistence entities and vice versa. This is
 * done in order to achieve a full separation between business and persistence layer.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface BusinessService {

	/**
	 * The value may indicate a suggestion for a logical component name,
	 * to be turned into a Spring bean in case of an autodetected component.
	 * @return the suggested component name, if any
	 */
	String value() default "";
}
