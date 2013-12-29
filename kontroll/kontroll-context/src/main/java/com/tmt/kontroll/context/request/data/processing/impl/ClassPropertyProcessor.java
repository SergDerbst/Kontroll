package com.tmt.kontroll.context.request.data.processing.impl;

import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.processing.PropertyProcessor;

@Component
public class ClassPropertyProcessor extends PropertyProcessor {

	@Override
	protected boolean isResponsible(PropertyDescriptor propertyDescriptor) {
		return propertyDescriptor.getName().equals("class");
	}

	@Override
	protected Set<String> doProcess(final String path, 
	                                final PropertyDescriptor propertyDescriptor, 
	                                final Class<?> beanProcess) {
		return new TreeSet<String>();
	}
}
