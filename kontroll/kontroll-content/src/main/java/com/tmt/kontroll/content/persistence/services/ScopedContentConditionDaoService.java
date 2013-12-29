package com.tmt.kontroll.content.persistence.services;

import java.util.List;

import com.tmt.kontroll.content.persistence.entities.ScopedContentCondition;
import com.tmt.kontroll.persistence.daos.CrudDao;

public interface ScopedContentConditionDaoService extends CrudDao<ScopedContentCondition, Integer> {

	public List<ScopedContentCondition> findConditionsByScopeName(final String scopeName);
}
