package com.tmt.kontroll.content.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tmt.kontroll.content.persistence.entities.RequestContext;

public interface RequestContextRepository extends JpaRepository<RequestContext, Integer> {

}
