package com.tmt.kontroll.content.persistence.services;

import java.util.Locale;

import com.tmt.kontroll.content.persistence.entities.Caption;
import com.tmt.kontroll.persistence.daos.CrudDao;

public interface CaptionDaoService extends CrudDao<Caption, Integer> {

	Caption findByIdentifierAndLocale(final String identifier, final Locale locale);
}
