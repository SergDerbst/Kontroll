package com.tmt.kontroll.content.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;

public interface ScopedContentItemRepository extends JpaRepository<ScopedContentItem, Integer>, QueryDslPredicateExecutor<ScopedContentItem> {

	ScopedContentItem findByContentAndItemNumber(String content, String itemNumber);

}
