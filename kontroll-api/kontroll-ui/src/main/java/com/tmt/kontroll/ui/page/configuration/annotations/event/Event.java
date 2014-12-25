package com.tmt.kontroll.ui.page.configuration.annotations.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tmt.kontroll.context.request.data.json.DataTransferElement;
import com.tmt.kontroll.ui.page.configuration.annotations.ConfigurationAnnotation;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageConfig;
import com.tmt.kontroll.ui.page.configuration.annotations.context.PageContext;
import com.tmt.kontroll.ui.page.events.EventType;
import com.tmt.kontroll.ui.page.segments.PageSegment;

/**
 * <p>
 * Configures an event that can occur on an annotated {@link PageSegment}.
 * </p>
 * <p>
 * This annotation is part of the {@link PageConfig} and {@link PageContext} annotations.
 * </p>
 *
 * @author SergDerbst
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConfigurationAnnotation
public @interface Event {

	/**
	 * Specifies the type of the event to handled.
	 *
	 * @return
	 */
	EventType type();

	/**
	 * Specifies the name of the handler for the event. A JavaScript function must be present
	 * under this name in the event handlers map of the Kontroll Events module of the client.
	 *
	 * @return
	 */
	String[] handlers();

	/**
	 * Specifies the target scopes of the event. This has to be given only if the event handler
	 * causes changes on different scopes than the annotated page segment's. The event handler
	 * defined in the client must be capable to handle the given order of this array.
	 *
	 * @return
	 */
	String[] targetScopes() default "";

	/**
	 * Specifies arguments passed to the even handler.
	 *
	 * @return
	 */
	HandlerArgument[] arguments() default {};

	/**
	 * <p>
	 * If the event handler sends data to the server, the dto determines which implementation of
	 * {@link DataTransferElement} is used. The class name of that class is then added to the
	 * event args as <code>dtoClass</code>.
	 * </p>
	 * <p>
	 * The default value is the class object of the interface
	 * itself, in which case it is assumed that no data is being transferred handling the event.
	 * </p>
	 *
	 * @return
	 */
	Class<? extends DataTransferElement> dto() default DataTransferElement.class;
}
