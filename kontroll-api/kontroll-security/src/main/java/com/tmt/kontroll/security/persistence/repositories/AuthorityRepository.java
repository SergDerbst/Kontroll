package com.tmt.kontroll.security.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tmt.kontroll.security.persistence.entities.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>, JpaSpecificationExecutor<Authority> {

	Authority findByName(String name);

}
