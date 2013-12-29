package com.tmt.kontroll.context.request;

import com.tmt.kontroll.context.global.GlobalContextDto;


/**
 * Abstract base class for all request context services. Request context services
 * are business level services that are responsible for retrieving and manipulating
 * data of given request contexts. 
 * 
 * @author Serg Derbst
 *
 * @param <DTO>
 * @param <GLOBAL>
 */
public abstract class RequestContextService<DTO extends RequestContextDto, GLOBAL extends GlobalContextDto> {

	public abstract DTO find(GLOBAL data);
}
