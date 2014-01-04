package com.tmt.kontroll.context.request.data.path.scanning.impl;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.path.scanning.BeanPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningHelper;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningFailedException;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanner;

@Component
public class MapPropertyPathScanner extends PropertyPathScanner {

	@Autowired
	BeanPathScanner beanProcessor;
	
	@Override
	protected boolean isResponsible(PropertyDescriptor propertyDescriptor) {
		return Map.class.isAssignableFrom(propertyDescriptor.getPropertyType());
	}

	@Override
	protected Set<String> doProcess(String path, PropertyDescriptor propertyDescriptor, Class<?> beanClass) throws PropertyPathScanningFailedException {
		final String propertyName = propertyDescriptor.getName();
		try {
			final SortedSet<String> mapPaths = new TreeSet<String>();
			String appendedPath = path + ".key";
			final SortedSet<String> keyPaths = this.beanProcessor.process(new TreeSet<String>(), appendedPath, PropertyPathScanningHelper.retrieveGenericParameterTypeOfField(beanClass, propertyName, 0));
			for (final String keyPath : keyPaths) {
				appendedPath = keyPath + ".value";
				mapPaths.addAll(this.beanProcessor.process(mapPaths, appendedPath, PropertyPathScanningHelper.retrieveGenericParameterTypeOfField(beanClass, propertyName, 1)));
			}
			return mapPaths;
		} catch (Exception e) {
			throw PropertyPathScanningFailedException.prepare(e, propertyName, path);
		}
	}
}
