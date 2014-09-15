package com.tmt.kontroll.content.persistence.selections;

public enum HtmlTag {

	Anchor("a"),
	Audio("audio"),
	Bold("b"),
	Div("div"),
	Header1("h1"),
	Header2("h2"),
	Header3("h3"),
	Header4("h4"),
	Header5("h5"),
	Header6("h6"),
	Image("img"),
	Italic("i"),
	Paragraph("p"),
	Span("span"),
	Video("video");

	private String tagName;

	private HtmlTag(final String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return this.tagName;
	}
}
