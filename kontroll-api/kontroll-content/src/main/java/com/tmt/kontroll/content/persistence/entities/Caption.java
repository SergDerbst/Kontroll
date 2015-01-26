package com.tmt.kontroll.content.persistence.entities;

import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmt.kontroll.persistence.BaseEntity;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_locale", columnNames = {"identifier", "locale"})})
public class Caption extends BaseEntity {

	private String	identifier;

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

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (!this.getClass().equals(o.getClass())) {
			return false;
		}
		final Caption other = (Caption) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.identifier, other.identifier);
		equals.append(this.locale, other.locale);
		equals.append(this.text, other.text);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.identifier);
		hashCode.append(this.locale);
		hashCode.append(this.text);
		return hashCode.build();
	}
}