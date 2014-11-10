package com.tmt.kontroll.ui.page.management.contexts;

import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import com.tmt.kontroll.ui.page.Page;
import com.tmt.kontroll.ui.page.PageSegment;
import com.tmt.kontroll.ui.page.configuration.annotations.context.Condition;

/**
 * <p>
 * The context of {@link PageSegment} objects as children of a parent page segment.
 * </p>
 * <p>
 * It implements {@link Comparable} to make sure that for preliminary {@link Page}s
 * all children of contained segments will be ordered first by their configured ordinal
 * numbers and then by their configured conditions.
 * </p>
 *
 * @author SergDerbst
 *
 */
public class PageSegmentOrdinalKey implements Comparable<PageSegmentOrdinalKey> {

	private final int									ordinal;
	private final String							scope;
	private final String							conditionsString;
	private final ConditionalOperator	operator;

	public PageSegmentOrdinalKey(	final int ordinal,
	                             	final String scope) {
		this.ordinal = ordinal;
		this.scope = scope;
		this.operator = ConditionalOperator.And;
		this.conditionsString = "";
	}

	public PageSegmentOrdinalKey(	final int ordinal,
	                             	final String scope,
																final Condition[] conditions,
																final ConditionalOperator operator) {
		this.ordinal = ordinal;
		this.scope = scope;
		this.operator = operator;
		this.conditionsString = this.createConditionsString(conditions);
	}

	private String createConditionsString(final Condition[] conditions) {
		String conditionsString = "";
		for (final Condition condition : conditions) {
			conditionsString = conditionsString + condition.path() + "." + condition.operator().name() + "." + condition.value() + "\n";
		}
		return conditionsString;
	}

	public int getOrdinal() {
		return this.ordinal;
	}

	public String getScope() {
		return this.scope;
	}

	public String getConditionsString() {
		return this.conditionsString;
	}

	public ConditionalOperator getOperator() {
		return this.operator;
	}

	@Override
	public int compareTo(final PageSegmentOrdinalKey other) {
		final int ordinalComparison = this.ordinal - other.getOrdinal();
		if (ordinalComparison != 0) {
			return ordinalComparison;
		}
		final int scopeComparison = this.scope.compareTo(other.getScope());
		if (scopeComparison != 0) {
			return scopeComparison;
		}
		final int operatorComparison = this.operator.ordinal() - other.getOperator().ordinal();
		if (operatorComparison != 0) {
			return operatorComparison;
		}
		return this.conditionsString.compareTo(other.getConditionsString());
	}

	@Override
	public String toString() {
		return this.getScope();
	}
}