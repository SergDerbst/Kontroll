package com.tmt.kontroll.context.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.RequestContextDto;
import com.tmt.kontroll.context.request.data.json.DataTransferElement;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

/**
 * Configuration annotation for {@link RequestHandlingService} objects indicating that the
 * annotated service handles data for the request. The value of the annotation contains the
 * class of the DTO object.
 *
 * @author SergDerbst
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RequestData {

	/**
	 * The {@link RequestContextDto} handled by the annotated service.
	 *
	 * @return
	 */
	Class<? extends DataTransferElement> value();
}
