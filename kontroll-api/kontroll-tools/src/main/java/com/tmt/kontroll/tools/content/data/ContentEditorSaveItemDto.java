package com.tmt.kontroll.tools.content.data;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import com.tmt.kontroll.context.ui.HtmlTag;

public class ContentEditorSaveItemDto extends ContentEditorDataLoadingDto {

	private String			content;
	private ContentType	contentType;
	private HtmlTag			contentTag;
	private String			css;
	private String			editScopePattern;
	private String			itemNumber;

	public String getEditScopePattern() {
		return this.editScopePattern;
	}

	public void setEditScopePattern(final String editScopePattern) {
		this.editScopePattern = editScopePattern;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	public void setItemNumber(final String itemNumber) {
		this.itemNumber = itemNumber;
	}

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
