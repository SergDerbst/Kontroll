package com.tmt.kontroll.persistence.entities.security;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Authority.class)
public abstract class Authority_ extends com.tmt.kontroll.persistence.entities.AbstractBaseEntity_ {

	public static volatile ListAttribute<Authority, User> users;
	public static volatile SingularAttribute<Authority, String> name;
	public static volatile SingularAttribute<Authority, String> comment;

}

