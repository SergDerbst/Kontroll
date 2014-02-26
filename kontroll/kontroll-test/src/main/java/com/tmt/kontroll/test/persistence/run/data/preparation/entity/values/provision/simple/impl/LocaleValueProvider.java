package com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionHandler;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.ValueProvisionKind;
import com.tmt.kontroll.test.persistence.run.data.preparation.entity.values.provision.simple.SimpleValueProvider;

public class LocaleValueProvider extends SimpleValueProvider<Locale> {

	private static final SortedSet<Locale> localeValueSet = setUpLocaleValueSet();

	public LocaleValueProvider(final ValueProvisionHandler provisionHandler) {
		super(provisionHandler);
	}

	@Override
	protected Locale instantiateDefaultValue(final Object entity, final ValueProvisionKind kind, final Class<?>... types) {
		return localeValueSet.first();
	}

	@Override
	protected boolean claimSimpleValueResponsibility(final ValueProvisionKind kind, final Class<?> valueType) {
		return Locale.class.equals(valueType);
	}

	@Override
	public Locale makeNextDefaultValue(final Object entity, final ValueProvisionKind kind, final Locale value, final Class<?>... types) {
		return this.getLocaleByIndex(this.getIndexOfLocale(value) + 1);
	}

	private Locale getLocaleByIndex(final int index) {
		int currentIndex = 0;
		final int lastIndex = localeValueSet.size() - 1;
		final int targetIndex = index > lastIndex ? index - lastIndex : index;
		final Iterator<Locale> iterator = localeValueSet.iterator();
		while(iterator.hasNext()) {
			final Locale locale = iterator.next();
			if (currentIndex == targetIndex) {
				return locale;
			}
			currentIndex++;
		}
		return null;
	}

	private int getIndexOfLocale(final Locale locale) {
		int index = 0;
		final Iterator<Locale> iterator = localeValueSet.iterator();
		while(iterator.hasNext()) {
			if (iterator.next().equals(locale)) {
				return index;
			}
			index++;
		}
		return index;
	}

	private static SortedSet<Locale> setUpLocaleValueSet() {
		try {
			final SortedSet<Locale> valueSet = new TreeSet<>(new Comparator<Locale>() {
				@Override
				public int compare(final Locale localeOne, final Locale localeTwo) {
					return localeOne.toString().compareTo(localeTwo.toString());
				}
			});
			for (final Field field : Locale.class.getDeclaredFields()) {
				if (isLocaleConstantField(field)) {
					field.setAccessible(true);
					valueSet.add((Locale) field.get(null));
				}
			}
			return valueSet;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean isLocaleConstantField(final Field field) {
		return !field.getName().equals("ROOT") && Locale.class.equals(field.getType()) && Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers());
	}
}
