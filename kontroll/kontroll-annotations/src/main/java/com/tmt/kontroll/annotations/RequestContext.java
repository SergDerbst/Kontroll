package com.tmt.kontroll.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestContext {

	String[] patterns() default {"/"};
}
