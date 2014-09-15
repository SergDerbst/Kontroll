package com.tmt.kontroll.content.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.tmt.kontroll.content.items.ContentItem;

/**
 * A DTO holding property values for configuring the application content. Its values 
 * shall be filled by the application's {@link Configuration} classes. 
 * 
 * @author Sergio Weigel
 *
 */
public class ContentProperties {

	private final List<String> contentItemBasePackages = new ArrayList<String>();

	/**
	 * Returns a list of all the base packages containing {@link ContentItem}s.
	 * @return
	 */
	public List<String> getContentItemBasePackages() {
		return contentItemBasePackages;
	}
}