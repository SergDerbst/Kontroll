package com.tmt.kontroll.content.persistence.services;

import com.tmt.kontroll.content.persistence.entities.ScopedContentItem;
import com.tmt.kontroll.persistence.daos.CrudDao;

public interface ScopedContentItemDaoService extends CrudDao<ScopedContentItem, Integer> {

	ScopedContentItem findByContentAndItemNumber(final String content, final String itemNumber);

}
