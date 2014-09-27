package com.tmt.kontroll.content.persistence.services;

import java.util.List;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.entities.ScopedContent;
import com.tmt.kontroll.persistence.daos.CrudDao;

public interface ScopedContentDaoService extends CrudDao<ScopedContent, Integer> {

	List<ScopedContent> findByScope(final Scope scope);
}
