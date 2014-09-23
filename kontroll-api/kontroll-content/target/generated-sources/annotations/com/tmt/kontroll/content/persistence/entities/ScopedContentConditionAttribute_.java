package com.tmt.kontroll.content.persistence.entities;

import com.tmt.kontroll.content.persistence.selections.ReferenceOperator;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ScopedContentConditionAttribute.class)
public abstract class ScopedContentConditionAttribute_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile SingularAttribute<ScopedContentConditionAttribute, ScopedContentCondition> scopedContentCondition;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, String> expectedValueType;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, String> expectedValue;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, String> className;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, String> valuePath;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, String> key;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, Integer> checkOrder;
	public static volatile SingularAttribute<ScopedContentConditionAttribute, ReferenceOperator> operator;

}

