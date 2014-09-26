package com.tmt.kontroll.ui.page;

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

import com.tmt.kontroll.annotations.Layout;
import com.tmt.kontroll.commons.exceptions.ScanFailedException;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.ui.config.UiProperties;
import com.tmt.kontroll.ui.page.layout.PageLayout;
import com.tmt.kontroll.ui.page.layout.PageLayoutBody;
import com.tmt.kontroll.ui.page.layout.PageLayoutFooter;
import com.tmt.kontroll.ui.page.layout.PageLayoutHeader;
import com.tmt.kontroll.ui.page.layout.PageLayoutSegment;

public class PageLayoutScannerTest {
	
	@Layout(patterns = {pattern})
	public static class TestHeader extends PageLayoutHeader {
		@Override
		public List<PageLayoutSegment> getChildren() {
			return null;
		}
	}
	@Layout(patterns = {pattern})
	public static class TestBody extends PageLayoutBody {
		@Override
		public List<PageLayoutSegment> getChildren() {
			return null;
		}
	}
	@Layout(patterns = {pattern})
	public static class TestFooter extends PageLayoutFooter {
		@Override
		public List<PageLayoutSegment> getChildren() {
			return null;
		}
	}
	
	private static final String pattern = "pattern";
	
	@Mock
	private AnnotationAndAssignableTypeCandidateScanner candidateScanner;
	@Mock
	private UiProperties contentProperties;
	@Mock
	private PageContentManager pageContentManager;
	@Mock
	private PageLayout pageLayout;
	@Mock
	private BeanDefinition beanDefinition;

	private PageLayoutScanner toTest;

	@Before
	@SuppressWarnings({"serial"})
	public void setUp() throws Exception {
		initMocks(this);
		this.toTest = new PageLayoutScanner();
		this.toTest.candidateScanner = this.candidateScanner;
		this.toTest.contentProperties = this.contentProperties;
		this.toTest.pageContentManager = this.pageContentManager;
		this.toTest.pageLayout = this.pageLayout;
		when(this.contentProperties.layoutBasePackages()).thenReturn(new ArrayList<String>() {{
			add("basePackages");
		}});
	}

	@Test
	@SuppressWarnings({"unchecked", "serial"})
	public void testThatHeadersAreScannedFor() throws Exception {
		//given
		when(this.candidateScanner.scan(eq(Layout.class), eq(PageLayoutHeader.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>(){{
			add(beanDefinition);
		}});
		when(this.beanDefinition.getBeanClassName()).thenReturn(TestHeader.class.getName());
		
		//when
		this.toTest.scan();
		
		//then
		verify(this.pageLayout).addHeader(eq(pattern), any(TestHeader.class));
	}
	
	@Test
	@SuppressWarnings({"unchecked", "serial"})
	public void testThatBodiesAreScannedFor() throws Exception {
		//given
		when(this.candidateScanner.scan(eq(Layout.class), eq(PageLayoutBody.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>(){{
			add(beanDefinition);
		}});
		when(this.beanDefinition.getBeanClassName()).thenReturn(TestBody.class.getName());
		
		//when
		this.toTest.scan();
		
		//then
		verify(this.pageLayout).addBody(eq(pattern), any(TestBody.class));
	}
	
	@Test
	@SuppressWarnings({"serial", "unchecked"})
	public void testThatFootersAreScannedFor() throws Exception {
		//given
		when(this.candidateScanner.scan(eq(Layout.class), eq(PageLayoutFooter.class), any(List.class))).thenReturn(new HashSet<BeanDefinition>(){{
			add(beanDefinition);
		}});
		when(this.beanDefinition.getBeanClassName()).thenReturn(TestFooter.class.getName());
		
		//when
		this.toTest.scan();
		
		//then
		verify(this.pageLayout).addFooter(eq(pattern), any(TestFooter.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected = ScanFailedException.class)
	public void testThatScanThrowsScanFailedException() {
		//given
		when(this.candidateScanner.scan(eq(Layout.class), eq(PageLayoutHeader.class), any(List.class))).thenThrow(new RuntimeException());
		//when
		this.toTest.scan();
	}
}
