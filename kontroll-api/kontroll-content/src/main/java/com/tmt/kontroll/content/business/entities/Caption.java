package com.tmt.kontroll.content.business.entities;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.tmt.kontroll.business.annotations.BusinessEntity;
import com.tmt.kontroll.context.ui.DomElement;
import com.tmt.kontroll.context.ui.HtmlTag;

@BusinessEntity(com.tmt.kontroll.content.persistence.entities.Caption.class)
public class Caption implements DomElement {

	private Integer							id;
	private Timestamp						timeStamp;
	private String							identifier;
	private Locale							locale;
	private String							text;

	private Map<String, String>	attributes	= new HashMap<>();

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}

	@Override
	public String getDomId() {
		return this.identifier + ".caption";
	}

	@Override
	public HtmlTag getTag() {
		return HtmlTag.Div;
	}

	@Override
	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(final Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(final Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
}
