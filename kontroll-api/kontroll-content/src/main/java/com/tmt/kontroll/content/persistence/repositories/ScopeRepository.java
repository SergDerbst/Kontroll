package com.tmt.kontroll.content.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tmt.kontroll.content.persistence.entities.Scope;

public interface ScopeRepository extends JpaRepository<Scope, Integer>, JpaSpecificationExecutor<Scope> {

	Scope findByName(String name);
}
