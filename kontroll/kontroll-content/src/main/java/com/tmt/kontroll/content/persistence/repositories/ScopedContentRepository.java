package com.tmt.kontroll.content.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;

public interface ScopedContentRepository extends JpaRepository<ScopedContent, Integer>, JpaSpecificationExecutor<ScopedContent> {

	List<ScopedContent> findByScopeAndName(	Scope scope,
																					String name);
}
