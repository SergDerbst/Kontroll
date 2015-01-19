package com.tmt.kontroll.tools.content.request.handlers;

import java.util.ArrayList;
import java.util.List;

import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

@RequestHandler(patterns = {"/content/editor/adddropdown/items"}, handling = RequestHandling.Always)
public class ContentEditorAddDropdownItemsHandler implements RequestHandlingService {

	@Override
	public void handle(final RequestHandlingParam param) {
		final List<String> items = new ArrayList<>();
		items.add("After");
		items.add("Before");
		items.add("Child");
		param.getDataResponse().put("items", items);
	}
}
