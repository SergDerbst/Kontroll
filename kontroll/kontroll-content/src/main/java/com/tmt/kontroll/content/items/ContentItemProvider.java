package com.tmt.kontroll.content.items;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.tmt.kontroll.annotations.Content;
import com.tmt.kontroll.commons.utils.AnnotationAndAssignableTypeCandidateScanner;
import com.tmt.kontroll.content.config.ContentProperties;
import com.tmt.kontroll.content.exceptions.NoContentItemFoundException;

@Component
public class ContentItemProvider {

	Map<String, Class<? extends ContentItem<?>>> contentItemMap = new HashMap<String, Class<? extends ContentItem<?>>>();

	@Autowired
	ContentProperties contentProperties;
	
	@Autowired
	AnnotationAndAssignableTypeCandidateScanner candidateScanner;

	@PostConstruct
	public void generateContentItemMap() {
		try {
			for (final BeanDefinition beanDefinition : this.candidateScanner.scan(Content.class, ContentItem.class, this.contentProperties.getContentItemBasePackages())) {
				@SuppressWarnings("unchecked")
				Class<? extends ContentItem<?>> contentItemClass = (Class<? extends ContentItem<?>>) ClassUtils.forName(beanDefinition.getBeanClassName(), ClassUtils.getDefaultClassLoader());
				Field tagField = contentItemClass.getField("tag");
				String contentItemKey = tagField.getType().getName() + "." + tagField.get(null).toString();
				contentItemMap.put(contentItemKey, contentItemClass);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <C extends ContentItem<?>, T extends Enum<T>> C provide(final T tag) throws NoContentItemFoundException {
		final String contentItemKey = tag.getClass().getName() + "." + tag.toString();
		final Class<? extends ContentItem<?>> contentItem = this.contentItemMap.get(contentItemKey);

		if (contentItem == null) {
			throw NoContentItemFoundException.prepare(tag);
		}

		try {
			return (C) contentItem.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}