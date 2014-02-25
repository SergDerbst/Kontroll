package com.tmt.kontroll.persistence.entities.security;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile SingularAttribute<User, Boolean> enabled;
	public static volatile ListAttribute<User, Authority> authorities;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> password;

}

