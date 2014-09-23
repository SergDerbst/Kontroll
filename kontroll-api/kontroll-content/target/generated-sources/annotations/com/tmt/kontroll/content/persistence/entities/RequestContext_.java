package com.tmt.kontroll.content.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RequestContext.class)
public abstract class RequestContext_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile SingularAttribute<RequestContext, String> path;
	public static volatile ListAttribute<RequestContext, Scope> scopes;

}

