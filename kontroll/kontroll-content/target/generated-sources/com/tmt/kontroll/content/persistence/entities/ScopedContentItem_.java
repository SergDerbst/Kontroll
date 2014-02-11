package com.tmt.kontroll.content.persistence.entities;

import com.tmt.kontroll.content.persistence.selections.ContentType;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScopedContentItem.class)
public abstract class ScopedContentItem_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile SingularAttribute<ScopedContentItem, String> content;
	public static volatile SingularAttribute<ScopedContentItem, String> tagName;
	public static volatile SingularAttribute<ScopedContentItem, String> cssClass;
	public static volatile SingularAttribute<ScopedContentItem, ContentType> type;
	public static volatile ListAttribute<ScopedContentItem, ScopedContent> scopedContents;

}

