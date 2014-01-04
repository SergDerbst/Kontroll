package com.tmt.kontroll.context.request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.context.request.data.path.scanning.BeanPathScanner;


/**
 * Based on a given class of {@link RequestContextService} the scanner will recursively scan
 * all properties of the service's according {@link RequestContextDto} and return a sorted
 * set of all paths to any contained property. If a property itself is a DTO, their type will
 * be scanned as well, so that in the end each path will point to a <i>terminal</i> property, 
 * meaning that the property is not another DTO.
 * </br> 
 * A property is considered terminal when its type is a member of 
 * {@link RequestContextDtoPathScanner#assignablyTerminalTypes} (e.g. Collections or Maps) or
 * {@link RequestContextDtoPathScanner#directlyTerminalTypes} or it is primitive, an enum, an
 * array or an annotation. 
 * </p>
 * The paths are returned as strings of property names separated by dots, so that they can 
 * easily be evaluated like Java Unified Expression Language or any of its derivatives.
 * 
 * @author Serg Derbst
 *
 */
@Component
public class RequestContextDtoPathScanner {
	
	@Autowired
	BeanPathScanner processor;
	
	@SuppressWarnings("unchecked")
	public SortedSet<String> scan(final RequestContextService<? extends RequestContextDto, ? extends GlobalContextDto> service) {
			try {
				SortedSet<String> set = new TreeSet<String>();
				for (Type type : ((ParameterizedType) service.getClass().getGenericSuperclass()).getActualTypeArguments()) {
					if (type instanceof Class<?> && RequestContextDto.class.isAssignableFrom((Class<?>) type)) {
						Class<?> beanClass = (Class<? extends RequestContextDto>) type;
						set = processor.process(set, StringUtils.uncapitalize(beanClass.getSimpleName()), beanClass);
					}
				}
				return set;
			} catch (Exception e) {
				throw new ScanFailedException(e);
			}
	}
}