package com.tmt.kontroll.content.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Scope.class)
public abstract class Scope_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile ListAttribute<Scope, RequestContext> requestContexts;
	public static volatile SingularAttribute<Scope, String> name;
	public static volatile SingularAttribute<Scope, String> description;
	public static volatile ListAttribute<Scope, ScopedContent> scopedContents;

}

