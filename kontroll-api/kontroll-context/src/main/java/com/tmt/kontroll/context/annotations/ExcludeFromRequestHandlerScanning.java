package com.tmt.kontroll.context.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.context.request.data.RequestContextDtoPathScanner;

/**
 * Indicates that the annotated class shall be excluded from property path scanning by the {@link RequestContextDtoPathScanner}.
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcludeFromRequestHandlerScanning {

}
