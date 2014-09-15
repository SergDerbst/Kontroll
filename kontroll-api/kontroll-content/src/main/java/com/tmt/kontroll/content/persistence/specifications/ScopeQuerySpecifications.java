package com.tmt.kontroll.content.persistence.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tmt.kontroll.content.persistence.entities.Scope;

public class ScopeQuerySpecifications {

	public static Specification<Scope> byNameAndRequestContextPath(final String scopeName,
	                                                               final String requestContextPath) {
		return new Specification<Scope>() {
			@Override
			public Predicate toPredicate(final Root<Scope> root,
			                             final CriteriaQuery<?> query,
			                             final CriteriaBuilder cb) {

				throw new RuntimeException("not implemented yet!!!");
			}
		};
	}
}
