package com.tmt.kontroll.content.persistence.entities;

import com.tmt.kontroll.content.persistence.selections.ConditionalOperator;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScopedContentCondition.class)
public abstract class ScopedContentCondition_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile ListAttribute<ScopedContentCondition, ScopedContentItem> scopedContentItems;
	public static volatile ListAttribute<ScopedContentCondition, ScopedContentCondition> parentConditions;
	public static volatile SingularAttribute<ScopedContentCondition, String> name;
	public static volatile SingularAttribute<ScopedContentCondition, String> description;
	public static volatile ListAttribute<ScopedContentCondition, ScopedContent> scopedContents;
	public static volatile ListAttribute<ScopedContentCondition, ScopedContentCondition> childConditions;
	public static volatile SingularAttribute<ScopedContentCondition, ConditionalOperator> operator;
	public static volatile ListAttribute<ScopedContentCondition, ScopedContentConditionAttribute> scopedContentConditionAttributes;

}

