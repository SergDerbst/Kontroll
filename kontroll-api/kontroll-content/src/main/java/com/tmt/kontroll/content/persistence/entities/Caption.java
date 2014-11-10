package com.tmt.kontroll.content.persistence.entities;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.tmt.kontroll.persistence.BaseEntity;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_locale", columnNames = {	"identifier",
																																											"locale"})})
public class Caption extends BaseEntity {

	private String	identifier;

	@Type(type = "org.hibernate.type.LocaleType")
	private Locale	locale;

	private String	text;

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

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final String identifier) {
		this.identifier = identifier;
	}
}