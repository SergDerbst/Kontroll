package annotations.com.tmt.kontroll.persistence.entities;

import java.sql.Timestamp;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.tmt.kontroll.persistence.entities.BaseEntity;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Integer> id;
	public static volatile SingularAttribute<BaseEntity, Timestamp> timeStamp;

}

