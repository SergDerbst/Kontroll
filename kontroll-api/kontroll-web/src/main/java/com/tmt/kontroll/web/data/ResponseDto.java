package com.tmt.kontroll.web.data;

import com.tmt.kontroll.context.global.GlobalContextDto;
import com.tmt.kontroll.ui.page.PageSegment;

/**
 * The global response dto.
 *
 * @author SergDerbst
 *
 */
public class ResponseDto {

	private GlobalContextDto	globalContextDto;
	private PageSegment				rootSegment;

	public GlobalContextDto getGlobalContextDto() {
		return this.globalContextDto;
	}

	public void setGlobalContextDto(final GlobalContextDto globalContextDto) {
		this.globalContextDto = globalContextDto;
	}

	public PageSegment getRootSegment() {
		return this.rootSegment;
	}

	public void setRootSegment(final PageSegment rootSegment) {
		this.rootSegment = rootSegment;
	}
}
