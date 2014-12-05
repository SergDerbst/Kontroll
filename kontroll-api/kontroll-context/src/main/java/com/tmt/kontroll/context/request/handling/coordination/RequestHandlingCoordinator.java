package com.tmt.kontroll.context.request.handling.coordination;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmt.kontroll.context.annotations.RequestHandler;
import com.tmt.kontroll.context.exceptions.RequestHandlingPreparationException;
import com.tmt.kontroll.context.global.GlobalContext;
import com.tmt.kontroll.context.request.RequestContextItem;
import com.tmt.kontroll.context.request.handling.RequestHandlingParam;
import com.tmt.kontroll.context.request.handling.services.RequestHandlingService;

@Component
public class RequestHandlingCoordinator {

	@Autowired
	GlobalContext	globalContext;

	public void coordinate(final RequestHandlingParam param) {
		final RequestHandlingCoordinationDto coordinationDto = this.prepare(param);
		this.runAlwaysFirst(coordinationDto, param);
		this.runAlways(coordinationDto, param);
		this.runDefault(coordinationDto, param);
		this.runAlwaysLast(coordinationDto, param);
	}

	private RequestHandlingCoordinationDto prepare(final RequestHandlingParam param) {
		final RequestHandlingCoordinationDto coordinationDto = new RequestHandlingCoordinationDto();
		for (final RequestContextItem item : this.globalContext.requestContextHolder().fetchRequestContext(param.getRequestPath())) {
			final RequestHandlingService handler = item.getService();
			if (this.handlerIsNotExcluded(handler, param.getRequest())) {
				coordinationDto.getDtoPathsMap().put(handler.getClass(), item.getDtoPaths());
				final RequestHandler requestHandler = handler.getClass().getAnnotation(RequestHandler.class);
				switch (requestHandler.handling()) {
					case Always:
						coordinationDto.getAlways().put(handler.getClass(), handler);
						break;
					case AlwaysFirst:
						coordinationDto.getAlwaysFirst().put(handler.getClass(), handler);
						break;
					case AlwaysLast:
						coordinationDto.getAlwaysLast().put(handler.getClass(), handler);
						break;
					case Optional:
						coordinationDto.getOther().put(handler.getClass(), handler);
						break;
					default:
						throw RequestHandlingPreparationException.prepare(requestHandler.handling());
				}
			}
		}
		return coordinationDto;
	}

	private boolean handlerIsNotExcluded(final RequestHandlingService handler, final HttpServletRequest request) {
		for (final RequestMethod method : handler.getClass().getAnnotation(RequestHandler.class).excludedMethod()) {
			if (method.name().equals(request.getMethod())) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private void runAlwaysFirst(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param) {
		for (final Entry<Class<? extends RequestHandlingService>, RequestHandlingService> entry : coordinationDto.getAlwaysFirst().entrySet()) {
			this.runBeforeServiceAndAfter(coordinationDto, param, entry.getValue(), coordinationDto.getAlwaysFirst());
		}
	}

	@SuppressWarnings("unchecked")
	private void runAlways(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param) {
		for (final Entry<Class<? extends RequestHandlingService>, RequestHandlingService> entry : coordinationDto.getAlways().entrySet()) {
			this.runBeforeServiceAndAfter(coordinationDto, param, entry.getValue(), coordinationDto.getAlways());
		}
	}

	@SuppressWarnings("unchecked")
	private void runDefault(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param) {
		for (final Entry<Class<? extends RequestHandlingService>, RequestHandlingService> entry : coordinationDto.getAlways().entrySet()) {
			this.runBeforeServiceAndAfter(coordinationDto, param, entry.getValue(), coordinationDto.getAlways(), coordinationDto.getOther());
		}
	}

	@SuppressWarnings("unchecked")
	private void runAlwaysLast(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param) {
		for (final Entry<Class<? extends RequestHandlingService>, RequestHandlingService> entry : coordinationDto.getAlwaysLast().entrySet()) {
			this.runBeforeServiceAndAfter(coordinationDto, param, entry.getValue(), coordinationDto.getAlwaysLast());
		}
	}

	@SuppressWarnings("unchecked")
	private void runBeforeServiceAndAfter(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param, final RequestHandlingService service, final Map<Class<? extends RequestHandlingService>, RequestHandlingService>... runningMaps) {
		this.runBefore(coordinationDto, param, service, runningMaps);
		this.runService(coordinationDto, param, service);
		this.runAfter(coordinationDto, param, service, runningMaps);
	}

	@SuppressWarnings("unchecked")
	private void runBefore(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param, final RequestHandlingService service, final Map<Class<? extends RequestHandlingService>, RequestHandlingService>... runningMaps) {
		for (final Class<? extends RequestHandlingService> preServiceClass : service.getClass().getAnnotation(RequestHandler.class).preHandling()) {
			for (final Map<Class<? extends RequestHandlingService>, RequestHandlingService> runningMap : runningMaps) {
				final RequestHandlingService preService = runningMap.get(preServiceClass);
				if (this.mustRunBefore(coordinationDto, preService)) {
					this.runService(coordinationDto, param, preService);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void runAfter(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param, final RequestHandlingService service, final Map<Class<? extends RequestHandlingService>, RequestHandlingService>... runningMaps) {
		for (final Class<? extends RequestHandlingService> postServiceClass : service.getClass().getAnnotation(RequestHandler.class).postHandling()) {
			for (final Map<Class<? extends RequestHandlingService>, RequestHandlingService> runningMap : runningMaps) {
				final RequestHandlingService postService = runningMap.get(postServiceClass);
				if (this.mustRunAfter(postService)) {
					this.runService(coordinationDto, param, postService);
				}
			}
		}
	}

	private void runService(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingParam param, final RequestHandlingService service) {
		param.setDtoPaths(coordinationDto.getDtoPathsMap().get(service.getClass()));
		service.handle(param);
		coordinationDto.getAlreadyRun().add(service);
	}

	private boolean mustRunBefore(final RequestHandlingCoordinationDto coordinationDto, final RequestHandlingService service) {
		return service != null && !coordinationDto.getAlreadyRun().contains(service);
	}

	private boolean mustRunAfter(final RequestHandlingService service) {
		return service != null;
	}
}
