package com.tmt.kontroll.commons.utils.scanning;

import java.lang.annotation.Annotation;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * An include filter for classpath scanning that will match a candidate
 * class, if it is annotated with the given <code>annotationType</code> 
 * and has the given <code>assignableType</code>.
 * 
 * @author Sergio Weigel
 *
 */
public class AnnotationAndAssignableTypeFilter extends AnnotationTypeFilter {

	private final Class<?> assignableType;
	
	public AnnotationAndAssignableTypeFilter(final Class<? extends Annotation> annotationType,
	                                      final Class<?> assignableType) {
		super(annotationType);
		this.assignableType = assignableType;
	}

	@Override
	protected boolean matchSelf(MetadataReader metadataReader) {
		try {
			return super.matchSelf(metadataReader) && this.assignableType.isAssignableFrom(Class.forName(metadataReader.getClassMetadata().getClassName()));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
