package com.tmt.kontroll.test.persistence.run.data.preparation.compliance;

import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveFieldsForValueProvision;
import static com.tmt.kontroll.test.persistence.run.utils.ClassReflectionHelper.retrieveTypeArgumentsOfField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;

import org.dbunit.dataset.builder.DataSetBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class TableComplianceAssurer {

	private static class InstanceHolder {
		public static TableComplianceAssurer instance = new TableComplianceAssurer();
	}

	public static TableComplianceAssurer instance() {
		if (InstanceHolder.instance == null) {
			InstanceHolder.instance = new TableComplianceAssurer();
		}
		return InstanceHolder.instance;
	}

	private static LocalContainerEntityManagerFactoryBean entityManager;

	@Autowired
	LocalContainerEntityManagerFactoryBean entityManagerFactoryBean;

	@PostConstruct
	public void setUpTableComplianceAssurance() {
		entityManager = this.entityManagerFactoryBean;
	}

	public String retrieveEntityTypeTableName(final Class<?> entityType) {
		final Table tableAnnotation = entityType.getAnnotation(Table.class);
		if (tableAnnotation == null || tableAnnotation.name().isEmpty()) {
			return entityType.getSimpleName();
		}
		return tableAnnotation.name();
	}

	public void assureTableCompliance(final DataSetBuilder builder) throws Exception {
		for (final Class<?> entityType : this.getEntityTypes()) {
			builder.ensureTableIsPresent(this.retrieveEntityTypeTableName(entityType));
			this.assureTableComplianceForJoinTables(builder, entityType);
		}
	}

	private void assureTableComplianceForJoinTables(final DataSetBuilder builder, final Class<?> entityType) throws Exception {
		for (final Field  field : retrieveFieldsForValueProvision(entityType)) {
			final JoinTable joinTableAnnotation = field.getAnnotation(JoinTable.class);
			if (joinTableAnnotation != null) {
				if (!joinTableAnnotation.name().isEmpty()) {
					builder.ensureTableIsPresent(joinTableAnnotation.name());
				} else {
					builder.ensureTableIsPresent(entityType.getSimpleName() + "_" + retrieveTypeArgumentsOfField(field, 0).getSimpleName());
				}
			}
		}
	}

	private List<Class<?>> getEntityTypes() {
		final List<Class<?>> entityTypeList = new ArrayList<>();
		final Set<EntityType<?>> entities = entityManager.getNativeEntityManagerFactory().getMetamodel().getEntities();
		for (final EntityType<?> entity : entities) {
			entityTypeList.add(entity.getBindableJavaType());
		}
		return entityTypeList;
	}
}
