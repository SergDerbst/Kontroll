package com.tmt.kontroll.content.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.tmt.kontroll.content.persistence.entities.Scope;

public interface ScopeRepository extends JpaRepository<Scope, Integer>, QueryDslPredicateExecutor<Scope> {

	List<Scope> findAllByName(String name);
}
