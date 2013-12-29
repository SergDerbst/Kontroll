package com.tmt.kontroll.persistence.repositories.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tmt.kontroll.persistence.entities.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>, JpaSpecificationExecutor<Authority> {

}
