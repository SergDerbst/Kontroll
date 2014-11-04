package com.tmt.kontroll.content.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.tmt.kontroll.content.persistence.entities.ScopedContentConditionAttribute;

public interface ScopedContentConditionAttributeRepository extends JpaRepository<ScopedContentConditionAttribute, Integer>, QueryDslPredicateExecutor<ScopedContentConditionAttribute> {

}
