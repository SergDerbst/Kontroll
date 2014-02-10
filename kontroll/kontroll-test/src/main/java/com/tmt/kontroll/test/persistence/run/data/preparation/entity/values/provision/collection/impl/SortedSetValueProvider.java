package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.lang.reflect.Field;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProvider;

public class SortedSetValueProvider extends CollectionValueProvider<Object, SortedSet<Object>> {

	public SortedSetValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final Field field,
	                                                     final Class<?> collectionType,
	                                                     final Class<?> itemType) {
		return
		SortedSet.class.isAssignableFrom(collectionType) &&
		field.getAnnotation(ManyToMany.class) == null &&
		field.getAnnotation(OneToMany.class) == null &&
		field.getAnnotation(ManyToOne.class) == null;
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected SortedSet instantiateEmptyCollection() {
		return new TreeSet();
	}
}