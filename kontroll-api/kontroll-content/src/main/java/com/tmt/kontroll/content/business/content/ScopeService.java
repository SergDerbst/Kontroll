package com.tmt.kontroll.content.business.content;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmt.kontroll.content.persistence.entities.Scope;
import com.tmt.kontroll.content.persistence.services.ScopeDaoService;
import com.tmt.kontroll.persistence.exceptions.EntityNotFoundInDatabaseException;

/**
 * Business service to handle business operations on {@link Scope}s. All methods are cached or invalidate the proper caches.
 *
 * @author SergDerbst
 *
 */
@Service
public class ScopeService {

	@Autowired
	ScopeDaoService	scopeDaoService;

	//TODO caching
	public List<Scope> loadAll(final String scopeName) {
		return this.scopeDaoService.findAllByName(scopeName);
	}

	//TODO caching
	public Scope load(final String scopeName, final String path) {
		for (final Scope scope : this.scopeDaoService.findAllByName(scopeName)) {
			if (Pattern.compile(scope.getRequestPattern()).matcher(path).find()) {
				return scope;
			}
		}
		return null;
	}

	//TODO caching
	public Scope init(final String scopeName, final String pattern) {
		Scope scope = this.scopeDaoService.findByNameAndRequestPattern(scopeName, pattern);
		if (scope == null) {
			scope = new Scope();
			scope.setName(scopeName);
			scope.setRequestPattern(pattern);
			this.scopeDaoService.create(scope);
		}
		return scope;
	}

	//TODO invalidate caching
	public Scope write(final Scope scope) throws EntityNotFoundInDatabaseException {
		return this.scopeDaoService.update(scope);
	}
}
