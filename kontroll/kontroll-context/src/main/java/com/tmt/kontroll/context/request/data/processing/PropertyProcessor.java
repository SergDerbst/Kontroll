package com.tmt.kontroll.context.request.data.processing;

import java.beans.PropertyDescriptor;
import java.util.Set;

public abstract class PropertyProcessor {
	
	private PropertyProcessor nextProcessor;

	protected abstract boolean isResponsible(final PropertyDescriptor propertyDescriptor);
	
	protected abstract Set<String> doProcess(final String path, 
	                                         final PropertyDescriptor propertyDescriptor, 
	                                         final Class<?> beanClass) throws PropertyPathProcessingFailedException;
	
	public Set<String> process(final String path, 
	                      final PropertyDescriptor propertyDescriptor, 
	                      final Class<?> beanClass) throws PropertyPathProcessingFailedException {
		if (this.isResponsible(propertyDescriptor)) {
			return this.doProcess(path, propertyDescriptor, beanClass);
		}
		if (this.nextProcessor == null) {
			throw PropertyPathProcessingFailedException.prepare(null, propertyDescriptor.getName(), path);
		}
		return this.nextProcessor.process(path, propertyDescriptor, beanClass);
	}
	
	public void setNextProcessor(PropertyProcessor nextProcessor) {
		this.nextProcessor = nextProcessor;
	}
}
