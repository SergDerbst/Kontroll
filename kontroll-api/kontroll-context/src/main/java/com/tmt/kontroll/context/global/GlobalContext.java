package com.tmt.kontroll.context.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.RequestContextDto;
import com.tmt.kontroll.context.request.RequestContextHolder;
import com.tmt.kontroll.context.request.RequestContextService;

/**
 * This class represents the global context of the application, which means it gives access to
 * {@link GlobalContextDto}, {@link SessionContextHolder}, and {@link RequestContextHolder}.
 *
 * @author Sergio Weigel
 *
 */
@Component
public class GlobalContext {

	/**
	 * The global data object. It holds all data that has to be available globally in order
	 * to retrieve {@link RequestContextDto}s using the proper {@link RequestContextService}s.
	 */
	GlobalContextDto			globalData;

	@Autowired
	RequestContextHolder	requestContextHolder;

	public RequestContextHolder requestContextHolder() {
		return this.requestContextHolder;
	}

	public GlobalContextDto globalContext() {
		return this.globalData;
	}

	public void globalContext(final GlobalContextDto globalData) {
		this.globalData = globalData;
	}

}