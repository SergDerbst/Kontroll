package com.tmt.kontroll.context.request.data.path.scanning.impl;

import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningHelper;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanner;

@Component
public class TerminalPropertyPathScanner extends PropertyPathScanner {

	@Override
	protected boolean isResponsible(PropertyDescriptor propertyDescriptor) {
		return PropertyPathScanningHelper.isTerminalProperty(propertyDescriptor.getPropertyType());
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
