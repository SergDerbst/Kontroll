package com.tmt.kontroll.context.request.handling.coordination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

public class RequestHandlingCoordinationDto {

	private final Map<Class<? extends RequestHandlingService>, Set<String>>							dtoPathsMap	= new HashMap<>();
	private final Map<Class<? extends RequestHandlingService>, RequestHandlingService>	alwaysFirst	= new HashMap<>();
	private final Map<Class<? extends RequestHandlingService>, RequestHandlingService>	alwaysLast	= new HashMap<>();
	private final Map<Class<? extends RequestHandlingService>, RequestHandlingService>	always			= new HashMap<>();
	private final Map<Class<? extends RequestHandlingService>, RequestHandlingService>	other				= new HashMap<>();
	private final Set<RequestHandlingService>																						alreadyRun	= new HashSet<>();

	public Map<Class<? extends RequestHandlingService>, Set<String>> getDtoPathsMap() {
		return this.dtoPathsMap;
	}

	public Map<Class<? extends RequestHandlingService>, RequestHandlingService> getAlwaysFirst() {
		return this.alwaysFirst;
	}

	public Map<Class<? extends RequestHandlingService>, RequestHandlingService> getAlwaysLast() {
		return this.alwaysLast;
	}

	public Map<Class<? extends RequestHandlingService>, RequestHandlingService> getAlways() {
		return this.always;
	}

	public Map<Class<? extends RequestHandlingService>, RequestHandlingService> getOther() {
		return this.other;
	}

	public Set<RequestHandlingService> getAlreadyRun() {
		return this.alreadyRun;
	}
}
