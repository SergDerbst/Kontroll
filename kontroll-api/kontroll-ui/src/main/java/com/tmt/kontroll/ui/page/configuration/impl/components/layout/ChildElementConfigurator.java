package com.tmt.kontroll.ui.page.configuration.impl.components.layout;

import java.lang.annotation.Annotation;

import org.springframework.beans.factory.annotation.Autowired;

import com.tmt.kontroll.ui.exceptions.PageConfigurationFailedException;
import com.tmt.kontroll.ui.page.configuration.PageSegmentConfigurator;
import com.tmt.kontroll.ui.page.configuration.annotations.layout.ChildElement;
import com.tmt.kontroll.ui.page.configuration.enums.components.ChildPosition;
import com.tmt.kontroll.ui.page.configuration.helpers.handlers.CssConfigurationHandler;
import com.tmt.kontroll.ui.page.segments.PageSegment;
import com.tmt.kontroll.ui.page.segments.PageSegmentChildrenAndContentAccessor;
import com.tmt.kontroll.ui.page.segments.PageSegmentHolder;

/**
 * Abstract base class for all page segment configurators for annotations with
 * {@link ChildElement} meta-annotation.
 *
 * @author SergDerbst
 *
 */
public abstract class ChildElementConfigurator extends PageSegmentConfigurator {

	@Autowired
	CssConfigurationHandler																cssHandler;

	@Autowired
	PageSegmentHolder													pageSegmentHolder;

	@Autowired
	PageSegmentChildrenAndContentAccessor			childrenAndContentAccessor;

	private final Class<? extends Annotation>	annotationType;

	protected ChildElementConfigurator(final Class<? extends Annotation> annotationType) {
		this.annotationType = annotationType;
	}

	@Override
	protected Class<? extends Annotation> getAnnotationType() {
		return this.annotationType;
	}

	protected void addChild(final ChildPosition position, final PageSegment parent, final PageSegment child) {
		this.cssHandler.handle(child);
		this.childrenAndContentAccessor.addChild(position, parent, child);
	}

	protected <A extends Annotation> boolean isNotAddedYet(final PageSegment segment, final A config, final String methodNameToRetrieveScopeFromConfig) {
		for (final PageSegment child : this.childrenAndContentAccessor.fetchTopChildren(segment)) {
			if (child.getScope().equals(this.retrieveConfiguratedScope(config, methodNameToRetrieveScopeFromConfig))) {
				return false;
			}
		}
		for (final PageSegment child : this.childrenAndContentAccessor.fetchMainChildren(segment).values()) {
			if (child.getScope().equals(this.retrieveConfiguratedScope(config, methodNameToRetrieveScopeFromConfig))) {
				return false;
			}
		}
		for (final PageSegment child : this.childrenAndContentAccessor.fetchBottomChildren(segment)) {
			if (child.getScope().equals(this.retrieveConfiguratedScope(config, methodNameToRetrieveScopeFromConfig))) {
				return false;
			}
		}
		return true;
	}

	private <A extends Annotation> String retrieveConfiguratedScope(final A config, final String methodName) {
		try {
			return (String) config.annotationType().getMethod(methodName).invoke(config);
		} catch (final Exception e) {
			throw new PageConfigurationFailedException(e);
		}
	}
}
