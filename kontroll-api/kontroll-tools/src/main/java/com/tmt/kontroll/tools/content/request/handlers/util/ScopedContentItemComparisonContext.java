package com.tmt.kontroll.tools.content.request.handlers.util;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

/**
 * Dumb little context dto to bring {@link ScopedContentItem}s in a proper logical order.
 *
 * @author SergDerbst
 *
 */
public class ScopedContentItemComparisonContext implements Comparable<ScopedContentItemComparisonContext> {

	private final String												itemNumber;
	private final List<ScopedContentCondition>	conditions;

	public ScopedContentItemComparisonContext(final String itemNumber,
																						final List<ScopedContentCondition> conditions) {
		this.itemNumber = itemNumber;
		this.conditions = conditions;
	}

	public List<ScopedContentCondition> getConditions() {
		return this.conditions;
	}

	public String getItemNumber() {
		return this.itemNumber;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null) {
			return false;
		}
		if (this == o) {
			return true;
		}
		if (!this.getClass().equals(o.getClass())) {
			return false;
		}
		final ScopedContentItemComparisonContext other = (ScopedContentItemComparisonContext) o;
		final EqualsBuilder equals = new EqualsBuilder();
		equals.append(this.itemNumber, other.itemNumber);
		equals.append(this.conditions, other.conditions);
		return equals.build();
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder hashCode = new HashCodeBuilder(17, 37);
		hashCode.append(this.itemNumber);
		hashCode.append(this.conditions);
		return hashCode.build();
	}

	@Override
	public int compareTo(final ScopedContentItemComparisonContext other) {
		if (this.equals(other)) {
			return 0;
		}
		final int itemNumberComparison = this.itemNumber.compareTo(other.itemNumber);
		if (itemNumberComparison != 0) {
			return itemNumberComparison;
		}
		final int numberOfConditionsComparison = this.conditions.size() - other.conditions.size();
		if (numberOfConditionsComparison != 0) {
			return numberOfConditionsComparison;
		}
		return this.hashCode() - other.hashCode();
	}
}
