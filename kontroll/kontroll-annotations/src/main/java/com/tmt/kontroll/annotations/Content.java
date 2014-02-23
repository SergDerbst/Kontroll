package com.tmt.kontroll.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation annotating classes that represent content items.
 * 
 * @author Sergio Weigel
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface Content {

	/**
	 * The enum class that contains the tag representing this content item.
	 * 
	 * @return
	 */
	Class<? extends Enum<?>> tagClass();

	/**
	 * The name of the enum value representing the tag for rendering this content
	 * item.
	 * 
	 * @return
	 */
	String tagValue();
}
