package com.tmt.kontroll.content.persistence.entities;

import java.util.Locale;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Caption.class)
public abstract class Caption_ extends com.tmt.kontroll.persistence.entities.BaseEntity_ {

	public static volatile SingularAttribute<Caption, String> text;
	public static volatile SingularAttribute<Caption, Locale> locale;
	public static volatile SingularAttribute<Caption, String> identifier;

}

