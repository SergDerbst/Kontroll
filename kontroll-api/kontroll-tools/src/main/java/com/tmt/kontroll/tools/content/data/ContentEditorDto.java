package com.tmt.kontroll.tools.content.data;

import com.tmt.kontroll.content.persistence.selections.ContentChildrenOrder;
import com.tmt.kontroll.context.request.data.json.DataTransferElement;
import com.tmt.kontroll.context.ui.HtmlTag;
import com.tmt.kontroll.tools.content.ui.elements.editor.ContentEditorForm;

/**
 * Request param DTO for submission of the {@link ContentEditorForm}.
 *
 * @author SergDerbst
 *
 */
public class ContentEditorDto implements DataTransferElement {

	private String								requestPatterns;
	private HtmlTag								htmlTag;
	private String								cssClass;
	private ContentChildrenOrder	contentChildrenOrder;
	private String								content;
	private String								targetScope;

	public String getRequestPatterns() {
		return requestPatterns;
	}

	public void setRequestPatterns(String requestPatterns) {
		this.requestPatterns = requestPatterns;
	}

	public HtmlTag getHtmlTag() {
		return this.htmlTag;
	}

	public void setHtmlTag(final HtmlTag htmlTag) {
		this.htmlTag = htmlTag;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(final String cssClass) {
		this.cssClass = cssClass;
	}

	public ContentChildrenOrder getContentChildrenOrder() {
		return this.contentChildrenOrder;
	}

	public void setContentChildrenOrder(final ContentChildrenOrder contentChildrenOrder) {
		this.contentChildrenOrder = contentChildrenOrder;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public String getTargetScope() {
		return this.targetScope;
	}

	public void setTargetScope(final String targetScope) {
		this.targetScope = targetScope;
	}

	@Override
	public String getDtoClass() {
		return this.getClass().getName();
	}
}
