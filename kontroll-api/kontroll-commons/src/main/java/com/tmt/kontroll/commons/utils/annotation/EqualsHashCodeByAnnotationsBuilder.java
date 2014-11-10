package com.tmt.kontroll.commons.utils.annotation;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * <p>
 * Helper class offering two static methods {@link #equals} and {@link #hashCode} which will
 * compare two objects for equality or calculate an object's hash code based solely on the annotations
 * present on the object's classes.
 * </p>
 * <p>
 * <i>Note: </i> Equality comparison based on annotations will only take place if both objects given
 * are not null, of the same type and <code>o1 != o2</code>.
 * </p>
 *
 * @author SergDerbst
 *
 */
public class EqualsHashCodeByAnnotationsBuilder {

	public static boolean equals(final Object o1, final Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null) {
			if (o2 == null) {
				return true;
			}
		}
		if (o2 == null) {
			if (o1 == null) {
				return true;
			}
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.getClass() != o2.getClass()) {
			return false;
		}
		return compareObjects(o1, o2) && compareObjects(o2, o1);
	}

	public static int hashCode(final Object o) {
		final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 37);
		for (final Annotation a : o.getClass().getAnnotations()) {
			hashCodeBuilder.append(a.hashCode());
		}
		return hashCodeBuilder.toHashCode();
	}

	private static boolean compareObjects(final Object o1, final Object o2) {
		final Annotation[] annotations1 = o1.getClass().getAnnotations();
		final Annotation[] annotations2 = o2.getClass().getAnnotations();
		if (annotations1.length == annotations2.length) {
			for (final Annotation a1 : annotations1) {
				final Annotation a2 = o2.getClass().getAnnotation(a1.annotationType());
				if (a2 != null) {
					if (!a1.equals(a2)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
}
