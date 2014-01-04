package com.tmt.kontroll.context.request.data.path.scanning;

import java.beans.PropertyDescriptor;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.path.scanning.impl.ClassPropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.impl.CollectionPropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.impl.MapPropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.impl.TerminalPropertyPathScanner;

@Component
public class PropertyPathScanningChain {

	@Autowired
	ClassPropertyPathScanner classPropertyProcessor;
	@Autowired
	TerminalPropertyPathScanner terminalPropertyProcessor;
	@Autowired
	CollectionPropertyPathScanner collectionPropertyProcessor;
	@Autowired
	MapPropertyPathScanner mapPropertyProcessor;
	
	@PostConstruct
	public void setUpProcessingChain() {
		this.classPropertyProcessor.setNextProcessor(this.terminalPropertyProcessor);
		this.terminalPropertyProcessor.setNextProcessor(this.collectionPropertyProcessor);
		this.collectionPropertyProcessor.setNextProcessor(this.mapPropertyProcessor);
		this.mapPropertyProcessor.setNextProcessor(null);
	}
	
	public Set<String> process(final String path, final PropertyDescriptor propertyDescriptor, Class<?> beanClass) throws PropertyPathScanningFailedException {
		return this.classPropertyProcessor.process(path, propertyDescriptor, beanClass);
	}
}
