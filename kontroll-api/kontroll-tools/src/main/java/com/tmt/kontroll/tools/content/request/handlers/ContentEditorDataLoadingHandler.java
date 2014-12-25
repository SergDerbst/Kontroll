package com.tmt.kontroll.tools.content.request.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.content.ContentItem;
import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.context.annotations.RequestData;
import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.request.handling.RequestHandling;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;
import com.tmt.kontroll.tools.content.data.ContentEditorDataLoadingDto;
import com.tmt.kontroll.ui.page.content.ContentLoader;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

@RequestHandler(patterns = {"/content/editor"}, handling = RequestHandling.Always)
@RequestData(ContentEditorDataLoadingDto.class)
public class ContentEditorDataLoadingHandler implements RequestHandlingService {

	private static final String						currentPatternKey		= "currentPattern";
	private static final String						requestPatternsKey	= "requestPatterns";

	@Autowired
	ScopeDaoService												scopeDaoService;

	@Autowired
	ContentLoader													contentLoader;

	@Autowired
	PageSegmentHolder											segmentHolder;

	@Autowired
	PageSegmentChildrenAndContentAccessor	childrenAndContentAccessor;

	@Override
	public void handle(final RequestHandlingParam param) {
		final List<Scope> scopes = this.scopeDaoService.findAllByName(((ContentEditorDataLoadingDto) param.getPayload().getRequestData()).getEditScope());
		this.loadRequestPatterns(param, scopes);
		this.loadContents(param, scopes);
	}

	private void loadContents(final RequestHandlingParam param, final List<Scope> scopes) {
		final Map<String, List<ContentItem>> content = new HashMap<>();
		final Map<String, Set<String>> contentItemNumbersMap = new HashMap<>();
		for (final Scope scope : scopes) {
			this.handleContent(scope, param, content, contentItemNumbersMap);
		}
		param.getDataResponse().put("currentContent", content);
		param.getDataResponse().put("currentContentItemNumbers", contentItemNumbersMap);
	}

	private void handleContent(final Scope scope, final RequestHandlingParam param, final Map<String, List<ContentItem>> contentMap, final Map<String, Set<String>> contentItemNumbersMap) {
		final PageSegment segment = this.segmentHolder.fetchMatchingPageSegment(scope.getName(), scope.getRequestContextPath());
		this.contentLoader.load(segment, scope.getRequestContextPath(), scope.getName(), param.getSession().getId());
		final List<ContentItem> content = this.childrenAndContentAccessor.fetchContent(segment);
		contentMap.put(scope.getRequestContextPath(), content);
		this.handleContentItemNumbers(scope, content, contentItemNumbersMap);
	}

	private void handleContentItemNumbers(final Scope scope, final List<ContentItem> content, final Map<String, Set<String>> contentItemNumbersMap) {
		final Set<String> contentItemNumbers = new TreeSet<>();
		for (final ContentItem item : content) {
			contentItemNumbers.add(item.getDomId().replace(scope.getName() + ".", ""));
		}
		contentItemNumbersMap.put(scope.getRequestContextPath(), contentItemNumbers);
	}

	private void loadRequestPatterns(final RequestHandlingParam param, final List<Scope> scopes) {
		final List<String> requestPatterns = new ArrayList<>();
		for (final Scope scope : scopes) {
			requestPatterns.add(scope.getRequestContextPath());
		}
		param.getDataResponse().put(requestPatternsKey, requestPatterns);
		param.getDataResponse().put(currentPatternKey, this.retrieveMatchingPattern(param));
	}

	@SuppressWarnings("unchecked")
	private Object retrieveMatchingPattern(final RequestHandlingParam param) {
		for (final String pattern : (List<String>) param.getDataResponse().get(requestPatternsKey)) {
			if (Pattern.compile(pattern).matcher(((ContentEditorDataLoadingDto) param.getPayload().getRequestData()).getCurrentPath()).find()) {
				return pattern;
			}
		}
		return null;
	}
}