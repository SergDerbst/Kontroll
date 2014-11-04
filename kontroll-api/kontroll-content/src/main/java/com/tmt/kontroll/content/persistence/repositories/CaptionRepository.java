package com.tmt.kontroll.content.persistence.repositories;

import java.util.Locale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.tmt.kontroll.content.persistence.entities.Caption;

public interface CaptionRepository extends JpaRepository<Caption, Integer>, QueryDslPredicateExecutor<Caption> {

	Caption findByIdentifierAndLocale(final String identifier, final Locale locale);
}
