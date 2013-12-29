package com.tmt.kontroll.context.request.data.processing;

import static org.mockito.Mockito.verify;

import java.beans.PropertyDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.context.request.data.processing.impl.ClassPropertyProcessor;
import com.tmt.kontroll.context.request.data.processing.impl.CollectionPropertyProcessor;
import com.tmt.kontroll.context.request.data.processing.impl.MapPropertyProcessor;
import com.tmt.kontroll.context.request.data.processing.impl.TerminalPropertyProcessor;

@RunWith(MockitoJUnitRunner.class)
public class PropertyProcessingChainTest {
	
	@Mock
	private ClassPropertyProcessor classPropertyProcessor;
	@Mock
	private CollectionPropertyProcessor collectionPropertyProcessor;
	@Mock
	private MapPropertyProcessor mapPropertyProcessor;
	@Mock
	private TerminalPropertyProcessor terminalPropertyProcessor;
	@Mock
	private PropertyDescriptor propertyDescriptor;
	
	private PropertyProcessingChain toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new PropertyProcessingChain();
		this.toTest.classPropertyProcessor = this.classPropertyProcessor;
		this.toTest.collectionPropertyProcessor = this.collectionPropertyProcessor;
		this.toTest.mapPropertyProcessor = this.mapPropertyProcessor;
		this.toTest.terminalPropertyProcessor = this.terminalPropertyProcessor;
	}

	@Test
	public void testThatSetupProcessingChainWorks() {
		//when
		this.toTest.setUpProcessingChain();
		
		//then
		verify(this.classPropertyProcessor).setNextProcessor(this.terminalPropertyProcessor);
		verify(this.terminalPropertyProcessor).setNextProcessor(this.collectionPropertyProcessor);
		verify(this.collectionPropertyProcessor).setNextProcessor(this.mapPropertyProcessor);
		verify(this.mapPropertyProcessor).setNextProcessor(null);
	}
	
	@Test
	public void thatProcessWorks() throws Exception {
		//when
		this.toTest.process("path", this.propertyDescriptor, this.getClass());
		
		//then
		verify(this.classPropertyProcessor).process("path", this.propertyDescriptor, this.getClass());
	}
}
