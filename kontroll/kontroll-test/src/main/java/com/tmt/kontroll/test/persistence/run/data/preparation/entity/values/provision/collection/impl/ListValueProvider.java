package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProvider;

public class ListValueProvider extends CollectionValueProvider<Object, List<Object>> {

	public ListValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final Field field,
	                                                     final Class<?> collectionType,
	                                                     final Class<?> itemType) {
		return field == null ? List.class.isAssignableFrom(collectionType) :
			List.class.isAssignableFrom(collectionType) &&
			field.getAnnotation(ManyToMany.class) == null &&
			field.getAnnotation(OneToMany.class) == null &&
			field.getAnnotation(ManyToOne.class) == null;
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected List instantiateEmptyCollection() {
		return new ArrayList();
	}
}