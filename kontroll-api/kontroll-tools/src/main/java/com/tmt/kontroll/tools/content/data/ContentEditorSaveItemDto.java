package com.tmt.kontroll.tools.content.data;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.context.ui.HtmlTag;

public class ContentEditorSaveItemDto extends ContentEditorDataLoadingDto {

	private ContentType	contentType;
	private HtmlTag			contentTag;
	private String			css;
	private String			content;

	public ContentType getContentType() {
		return this.contentType;
	}

	public void setContentType(final ContentType contentType) {
		this.contentType = contentType;
	}

	public HtmlTag getContentTag() {
		return this.contentTag;
	}

	public void setContentTag(final HtmlTag contentTag) {
		this.contentTag = contentTag;
	}

	public String getCss() {
		return this.css;
	}

	public void setCss(final String css) {
		this.css = css;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}
}
