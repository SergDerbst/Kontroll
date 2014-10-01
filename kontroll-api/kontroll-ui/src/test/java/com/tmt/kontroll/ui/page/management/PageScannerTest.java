package com.tmt.kontroll.ui.page.management;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.config.BeanDefinition;

import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.ui.config.UiProperties;
import com.tmt.kontroll.ui.page.layout.PageSegment;
import com.tmt.kontroll.ui.page.management.annotations.PageConfig;
import com.tmt.kontroll.ui.page.management.annotations.PageContext;

public class PageScannerTest {

	@PageConfig(contexts = {@PageContext(scope = "dummy")})
	public static class DummyPageSegment extends PageSegment {
		@Override
		public String getScopeName() {
			return "dummy";
		}
	}

	@Mock
	UiProperties																uiProperties;
	@Mock
	AnnotationAndAssignableTypeCandidateScanner	candidateScanner;
	@Mock
	BeanDefinition															beanDefinition;
	@Mock
	PageSegmentHolder														pageSegmentHolder;
	@Mock
	PagePreparator															pagePreparator;

	private PageScanner													toTest;

	@Before
	@SuppressWarnings({"unchecked", "serial"})
	public void setUp() {
		initMocks(this);
		when(this.uiProperties.layoutBasePackages()).thenReturn(new ArrayList<String>() {
			{
				this.add(this.getClass().getPackage().getName());
			}
		});
		when(this.beanDefinition.getBeanClassName()).thenReturn(DummyPageSegment.class.getName());
		when(this.candidateScanner.scan(any(Class.class), eq(PageSegment.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>() {
			{
				this.add(PageScannerTest.this.beanDefinition);
			}
		});
		this.toTest = new PageScanner();
		this.toTest.uiProperties = this.uiProperties;
		this.toTest.candidateScanner = this.candidateScanner;
		this.toTest.pagePreparator = this.pagePreparator;
		this.toTest.pageSegmentHolder = this.pageSegmentHolder;
	}

	@Test
	public void testThatScanWorks() throws Exception {
		//when
		this.toTest.scan();
		//then
		verify(this.pageSegmentHolder).addPageSegment(any(DummyPageSegment.class));
		verify(this.pagePreparator).prepare();
	}

	@Test(expected = ScanFailedException.class)
	public void testThatScanThrowsException() throws Exception {
		//given
		when(this.beanDefinition.getBeanClassName()).thenThrow(new RuntimeException());
		//when
		this.toTest.scan();
	}
}
