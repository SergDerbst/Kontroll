package com.tmt.kontroll.context.request.data.processing.impl;

import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.processing.PropertyHelper;
import com.tmt.kontroll.context.request.data.processing.PropertyProcessor;

@Component
public class TerminalPropertyProcessor extends PropertyProcessor {

	@Override
	protected boolean isResponsible(PropertyDescriptor propertyDescriptor) {
		return PropertyHelper.isTerminalProperty(propertyDescriptor.getPropertyType());
	}

	@Override
	@SuppressWarnings("serial")
	protected Set<String> doProcess(final String path, 
	                           final PropertyDescriptor propertyDescriptor, 
	                           final Class<?> beanProcess) {
		return new TreeSet<String>() {{
			add(path + "." + propertyDescriptor.getName());
		}};
	}
}
