package com.tmt.kontroll.context.request.data.path.scanning.impl;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.path.scanning.BeanPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningFailedException;
import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningHelper;

@Component
public class CollectionPropertyPathScanner extends PropertyPathScanner {

	@Autowired
	BeanPathScanner	beanProcessor;

	@Override
	protected boolean isResponsible(final PropertyDescriptor propertyDescriptor) {
		return Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType());
	}

	@Override
	protected Set<String> doProcess(final String path, final PropertyDescriptor propertyDescriptor, final Class<?> beanClass) throws PropertyPathScanningFailedException {
		try {
			final Class<?> genericParameterTypeOfField = PropertyPathScanningHelper.retrieveGenericParameterTypeOfField(beanClass, propertyDescriptor.getName(), 0);
			final TreeSet<String> set = new TreeSet<String>();
			if (!beanClass.equals(beanClass)) {
				final String appendedPath = path + ".collection";
				set.add(appendedPath);
				return this.beanProcessor.process(set, appendedPath, genericParameterTypeOfField);
			}
			return set;
		} catch (final Exception e) {
			throw PropertyPathScanningFailedException.prepare(e, propertyDescriptor.getName(), path);
		}
	}
}
