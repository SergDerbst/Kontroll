package com.tmt.kontroll.context.request.data.processing;

import java.beans.PropertyDescriptor;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.context.request.data.processing.impl.ClassPropertyProcessor;
import com.tmt.kontroll.context.request.data.processing.impl.CollectionPropertyProcessor;
import com.tmt.kontroll.context.request.data.processing.impl.MapPropertyProcessor;
import com.tmt.kontroll.context.request.data.processing.impl.TerminalPropertyProcessor;

@Component
public class PropertyProcessingChain {

	@Autowired
	ClassPropertyProcessor classPropertyProcessor;
	@Autowired
	TerminalPropertyProcessor terminalPropertyProcessor;
	@Autowired
	CollectionPropertyProcessor collectionPropertyProcessor;
	@Autowired
	MapPropertyProcessor mapPropertyProcessor;
	
	@PostConstruct
	public void setUpProcessingChain() {
		this.classPropertyProcessor.setNextProcessor(this.terminalPropertyProcessor);
		this.terminalPropertyProcessor.setNextProcessor(this.collectionPropertyProcessor);
		this.collectionPropertyProcessor.setNextProcessor(this.mapPropertyProcessor);
		this.mapPropertyProcessor.setNextProcessor(null);
	}
	
	public Set<String> process(final String path, final PropertyDescriptor propertyDescriptor, Class<?> beanClass) throws PropertyPathProcessingFailedException {
		return this.classPropertyProcessor.process(path, propertyDescriptor, beanClass);
	}
}
