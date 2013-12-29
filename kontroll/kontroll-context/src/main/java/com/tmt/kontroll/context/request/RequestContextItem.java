package com.tmt.kontroll.context.request;

import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmt.kontroll.context.global.GlobalContextDto;

/**
 * A request context consists of:
 * 
 * <ul>
 * <li>a url path pattern string identifying the request context</li> 
 * <li>data represented by a set of services to fetch and manipulate the data</li> 
 * <li>a {@link PageLayout} object for holding and organizing components and content.</li> 
 * </ul>
 * @author Serg Derbst
 *
 */
public class RequestContextItem {

	private final Set<String> dtoPaths;
	private final RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto> service;
	
	public RequestContextItem(final RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto> service,
	                          final Set<String> dtoPaths) {
		this.service = service;
		this.dtoPaths = dtoPaths;
	}

	public Set<String> getDtoPaths() {
		return dtoPaths;
	}

	public RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto> getService() {
		return service;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.dtoPaths.hashCode()).append(this.service.hashCode()).toHashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof RequestContextItem) {
			final RequestContextItem other = (RequestContextItem) o;
			return new EqualsBuilder().append(this.dtoPaths, other.dtoPaths).append(this.service, other.service).isEquals();
		}
		return false;
	}
}
