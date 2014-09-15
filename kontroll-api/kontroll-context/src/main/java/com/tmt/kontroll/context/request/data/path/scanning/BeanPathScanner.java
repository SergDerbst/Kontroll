package com.tmt.kontroll.context.request.data.path.scanning;

import java.beans.PropertyDescriptor;
import java.util.SortedSet;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;

@Component
public class BeanPathScanner {
	
	@Autowired
	PropertyPathScanningChain processingChain;

	public SortedSet<String> process(final SortedSet<String> set, final String path, final Class<?> beanClass) {
		try {
			final PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(beanClass);
			for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
				set.addAll(this.processingChain.process(path, propertyDescriptor, beanClass));
			}
			return set;
		} catch (Exception e) {
			throw new ScanFailedException(e);
		}
	}
}
