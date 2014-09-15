package com.tmt.kontroll.context.request.data.path.scanning;

import java.beans.PropertyDescriptor;
import java.util.Set;

public abstract class PropertyPathScanner {
	
	private PropertyPathScanner nextProcessor;

	protected abstract boolean isResponsible(final PropertyDescriptor propertyDescriptor);
	
	protected abstract Set<String> doProcess(final String path, 
	                                         final PropertyDescriptor propertyDescriptor, 
	                                         final Class<?> beanClass) throws PropertyPathScanningFailedException;
	
	public Set<String> process(final String path, 
	                      final PropertyDescriptor propertyDescriptor, 
	                      final Class<?> beanClass) throws PropertyPathScanningFailedException {
		if (this.isResponsible(propertyDescriptor)) {
			return this.doProcess(path, propertyDescriptor, beanClass);
		}
		if (this.nextProcessor == null) {
			throw PropertyPathScanningFailedException.prepare(null, propertyDescriptor.getName(), path);
		}
		return this.nextProcessor.process(path, propertyDescriptor, beanClass);
	}
	
	public void setNextProcessor(PropertyPathScanner nextProcessor) {
		this.nextProcessor = nextProcessor;
	}
}
