package com.tmt.kontroll.context.request.data.processing.impl;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.processing.BeanProcessor;
import com.tmt.kontroll.context.request.data.processing.PropertyHelper;
import com.tmt.kontroll.context.request.data.processing.PropertyPathProcessingFailedException;
import com.tmt.kontroll.context.request.data.processing.PropertyProcessor;

@Component
public class CollectionPropertyProcessor extends PropertyProcessor {

	@Autowired
	BeanProcessor beanProcessor;
	
	@Override
	protected boolean isResponsible(PropertyDescriptor propertyDescriptor) {
		return Collection.class.isAssignableFrom(propertyDescriptor.getPropertyType());
	}

	@Override
	protected Set<String> doProcess(final String path, 
                                  final PropertyDescriptor propertyDescriptor, 
                                  final Class<?> beanClass) throws PropertyPathProcessingFailedException {
		try {
			final TreeSet<String> set = new TreeSet<String>();
			final String appendedPath = path + ".collection";
			set.add(appendedPath);
			return this.beanProcessor.process(set, appendedPath, PropertyHelper.retrieveGenericParameterTypeOfField(beanClass, propertyDescriptor.getName(), 0));
		} catch (Exception e) {
			throw PropertyPathProcessingFailedException.prepare(e, propertyDescriptor.getName(), path);
		}
	}
}
