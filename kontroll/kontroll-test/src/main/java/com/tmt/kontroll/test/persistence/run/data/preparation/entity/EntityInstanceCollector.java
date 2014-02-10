package com.tmt.kontroll.test.persistence.run.data.preparation.entity;

public class EntityInstanceCollector {

	private static class InstanceHolder {
		public static EntityInstanceCollector instance;
	}

	public static EntityInstanceCollector newInstance() {
		InstanceHolder.instance = new EntityInstanceCollector();
		return InstanceHolder.instance;
	}

	public void collectEntities(final Class<?> rootEntityClass) {

	}
}
