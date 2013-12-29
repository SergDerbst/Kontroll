package com.tmt.kontroll.content.items.impl;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.content.items.ContentItem;

public class ImplementedContentItemsTest {

	private static final String TextContentItemSuffix = "TextContentItem";
	private static final String ContentItemSuffix = "ContentItem";	
	
	private final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
	
	@Test
	public void testThatContentItemsHaveProperHtmlTags() throws Exception {
		this.scanner.addIncludeFilter(new AssignableTypeFilter(ContentItem.class));
		for (final BeanDefinition beanDefinition : this.scanner.findCandidateComponents("com.tmt.kontroll.content.items.impl")) {
			@SuppressWarnings("unchecked")
			Class<ContentItem<? extends Enum<?>>> itemClass = (Class<ContentItem<? extends Enum<?>>>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
			ContentItem<? extends Enum<?>> item = itemClass.newInstance();
			String tagName = extractTagName(itemClass.getSimpleName());
			assertEquals(tagName, item.getTag().name());
		}
	}

	private String extractTagName(String simpleName) {
		if (simpleName.endsWith(TextContentItemSuffix)) {
			simpleName = StringUtils.remove(simpleName, TextContentItemSuffix);
		}
		if (simpleName.endsWith(ContentItemSuffix)) {
			simpleName = StringUtils.remove(simpleName, ContentItemSuffix);
		}
		return simpleName;
	}
}
