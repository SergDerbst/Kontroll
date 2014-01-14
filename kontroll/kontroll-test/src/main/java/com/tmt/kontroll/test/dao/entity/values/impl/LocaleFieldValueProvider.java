package com.tmt.kontroll.test.dao.entity.values.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.tmt.kontroll.test.dao.entity.values.ValueFieldProvider;

@Component
public class LocaleFieldValueProvider extends ValueFieldProvider<Locale> {

	private static final SortedSet<Locale> localeValueSet = setUpLocaleValueSet();

	public LocaleFieldValueProvider() {
		super(localeValueSet.first());
	}

	@Override
	protected boolean isResponsible(final String fieldName, final Class<?> valueClass) {
		return Locale.class.equals(valueClass);
	}

	@Override
	protected Locale combineValue(final Locale valueOne, final Locale valueTwo) {
		final int firstIndex = this.getIndexOfLocale(valueOne);
		final int secondIndex = this.getIndexOfLocale(valueTwo);
		return this.getLocaleByIndex(firstIndex + secondIndex);
	}

	@Override
	protected void increase() {
		super.setCurrentValue(this.getLocaleByIndex(this.getIndexOfLocale(super.getCurrentValue()) + 1));
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
