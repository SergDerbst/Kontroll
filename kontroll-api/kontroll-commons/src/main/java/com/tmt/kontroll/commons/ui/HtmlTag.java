package com.tmt.kontroll.commons.ui;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum representing HTML tags. The actual tag name as used in HTML will be the JSON representation
 * when serialized with Jackson.
 *
 * @author SergDerbst
 *
 */
public enum HtmlTag {

	Anchor("a"),
	Audio("audio"),
	Bold("b"),
	Div("div"),
	Form("form"),
	Header1("h1"),
	Header2("h2"),
	Header3("h3"),
	Header4("h4"),
	Header5("h5"),
	Header6("h6"),
	Image("img"),
	Input("input"),
	Italic("i"),
	Paragraph("p"),
	Span("span"),
	Video("video");

	private String	tagName;

	private HtmlTag(final String tagName) {
		this.tagName = tagName;
	}

	@JsonValue
	public String getTagName() {
		return this.tagName;
	}
}
