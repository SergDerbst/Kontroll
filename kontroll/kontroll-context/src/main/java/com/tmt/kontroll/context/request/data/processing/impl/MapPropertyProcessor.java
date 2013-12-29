package com.tmt.kontroll.context.request.data.processing.impl;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.processing.BeanProcessor;
import com.tmt.kontroll.context.request.data.processing.PropertyHelper;
import com.tmt.kontroll.context.request.data.processing.PropertyPathProcessingFailedException;
import com.tmt.kontroll.context.request.data.processing.PropertyProcessor;

@Component
public class MapPropertyProcessor extends PropertyProcessor {

	@Autowired
	BeanProcessor beanProcessor;
	
	@Override
	protected boolean isResponsible(PropertyDescriptor propertyDescriptor) {
		return Map.class.isAssignableFrom(propertyDescriptor.getPropertyType());
	}

	@Override
	protected Set<String> doProcess(String path, PropertyDescriptor propertyDescriptor, Class<?> beanClass) throws PropertyPathProcessingFailedException {
		final String propertyName = propertyDescriptor.getName();
		try {
			final SortedSet<String> mapPaths = new TreeSet<String>();
			String appendedPath = path + ".key";
			final SortedSet<String> keyPaths = this.beanProcessor.process(new TreeSet<String>(), appendedPath, PropertyHelper.retrieveGenericParameterTypeOfField(beanClass, propertyName, 0));
			for (final String keyPath : keyPaths) {
				appendedPath = keyPath + ".value";
				mapPaths.addAll(this.beanProcessor.process(mapPaths, appendedPath, PropertyHelper.retrieveGenericParameterTypeOfField(beanClass, propertyName, 1)));
			}
			return mapPaths;
		} catch (Exception e) {
			throw PropertyPathProcessingFailedException.prepare(e, propertyName, path);
		}
	}
}
