package com.tmt.kontroll.content.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScopedContent.class)
public abstract class ScopedContent_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile ListAttribute<ScopedContent, ScopedContentCondition> conditions;
	public static volatile SingularAttribute<ScopedContent, Scope> scope;
	public static volatile SingularAttribute<ScopedContent, String> description;
	public static volatile SingularAttribute<ScopedContent, String> name;
	public static volatile ListAttribute<ScopedContent, ScopedContentItem> scopedContentItems;

}

