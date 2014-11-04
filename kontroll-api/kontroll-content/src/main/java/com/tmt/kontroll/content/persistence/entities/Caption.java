package com.tmt.kontroll.content.persistence.entities;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.tmt.kontroll.persistence.BaseEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "unique_locale", columnNames = { "identifier", "locale" }) })
public class Caption extends BaseEntity {

	private String identifier;
	
	private Locale locale;
	
	private String text;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}