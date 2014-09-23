package com.tmt.kontroll.content.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tmt.kontroll.content.persistence.entities.Caption;

public interface CaptionRepository extends JpaRepository<Caption, Integer>, JpaSpecificationExecutor<Caption> {

}