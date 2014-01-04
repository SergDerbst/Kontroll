package com.tmt.kontroll.context.request.data.path.scanning;

import static org.mockito.Mockito.verify;

import java.beans.PropertyDescriptor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tmt.kontroll.context.request.data.path.scanning.PropertyPathScanningChain;
import com.tmt.kontroll.context.request.data.path.scanning.impl.ClassPropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.impl.CollectionPropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.impl.MapPropertyPathScanner;
import com.tmt.kontroll.context.request.data.path.scanning.impl.TerminalPropertyPathScanner;

@RunWith(MockitoJUnitRunner.class)
public class PropertyPathScanningChainTest {
	
	@Mock
	private ClassPropertyPathScanner classPropertyProcessor;
	@Mock
	private CollectionPropertyPathScanner collectionPropertyProcessor;
	@Mock
	private MapPropertyPathScanner mapPropertyProcessor;
	@Mock
	private TerminalPropertyPathScanner terminalPropertyProcessor;
	@Mock
	private PropertyDescriptor propertyDescriptor;
	
	private PropertyPathScanningChain toTest;

	@Before
	public void setUp() throws Exception {
		this.toTest = new PropertyPathScanningChain();
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
