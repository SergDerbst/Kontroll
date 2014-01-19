package com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.persistence.dao.entity.value.provision.ValueProviderNotFoundException;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl.ListValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl.SetValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.collection.impl.SortedSetValueProvider;
import com.tmt.kontroll.test.persistence.dao.entity.value.provision.simple.SimpleValueProvisionHandler;

@Component
public class CollectionValueProviderFactory {

	@Autowired
	SimpleValueProvisionHandler simpleValueProvisionHandler;

	@SuppressWarnings({"rawtypes", "unchecked"})
	public <V> CollectionValueProvider<V, Collection<V>> create(final Class<?> collectionType, final Class<?> itemType) {
		if (List.class.isAssignableFrom(collectionType)) {
			return new ListValueProvider(itemType, this.simpleValueProvisionHandler);
		}
		if (SortedSet.class.isAssignableFrom(collectionType)) {
			return new SortedSetValueProvider(itemType, this.simpleValueProvisionHandler);
		}
		if (Set.class.isAssignableFrom(collectionType)) {
			return new SetValueProvider(itemType, this.simpleValueProvisionHandler);
		}
		throw ValueProviderNotFoundException.prepare(collectionType, itemType);
	}
}
