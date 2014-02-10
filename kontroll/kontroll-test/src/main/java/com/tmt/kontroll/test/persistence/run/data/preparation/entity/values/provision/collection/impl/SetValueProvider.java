package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.impl;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.collection.CollectionValueProvider;

public class SetValueProvider extends CollectionValueProvider<Object, Set<Object>> {

	public SetValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected boolean claimCollectionValueResponsibility(final Field field,
	                                                     final Class<?> collectionType,
	                                                     final Class<?> itemType) {
		return
		Set.class.isAssignableFrom(collectionType) &&
		!SortedSet.class.isAssignableFrom(collectionType) &&
		field.getAnnotation(ManyToMany.class) == null &&
		field.getAnnotation(OneToMany.class) == null &&
		field.getAnnotation(ManyToOne.class) == null;
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	protected Set instantiateEmptyCollection() {
		return new HashSet();
	}
}
