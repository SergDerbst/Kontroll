package com.tmt.kontroll.content.persistence.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;

public class ConditionQuerySpecifications {

	public static Specification<ScopedContentCondition> specificationToFindConditionByScopeName(final String scopeName) {
		return new Specification<ScopedContentCondition>() {
			@Override
			public Predicate toPredicate(Root<ScopedContentCondition> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				return null;
			}
		};
	}
}
