package com.tmt.kontroll.context.ui;

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
	Button("button"),
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
	Label("label"),
	ListItem("li"),
	Option("option"),
	OrderedList("ol"),
	Paragraph("p"),
	Span("span"),
	Select("select"),
	Textarea("textarea"),
	UnorderedList("ul"),
	Video("video");

	private String	tagName;

	private HtmlTag(final String tagName) {
		this.tagName = tagName;
	}

	@JsonValue
	public String getTagName() {
		return this.tagName;
	}

	/**
	 * Returns the proper tag for the given tag name or <code>null</code>, if no tag
	 * with this name exists.
	 *
	 * @param tagName
	 * @return
	 */
	public static HtmlTag forTagName(final String tagName) {
		for (final HtmlTag tag : values()) {
			if (tag.getTagName().equals(tagName)) {
				return tag;
			}
		}
		return null;
	}
}
