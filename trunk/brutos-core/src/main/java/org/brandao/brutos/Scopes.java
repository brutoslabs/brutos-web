package org.brandao.brutos;

import org.brandao.brutos.scope.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

public class Scopes {

	private Map<String, Scope> scopes;

	public Scopes() {
		scopes = new HashMap<String, Scope>();
	}

	public void register(String id, Scope scope) {

		if (id == null)
			throw new NullPointerException("id");

		if (scope == null)
			throw new NullPointerException("scope");

		if (getLogger().isInfoEnabled())
			getLogger().info(
					(scopes.containsKey(id) ? "override scope "
							: "adding scope ") + id);

		scopes.put(id, scope);
	}

	public void remove(String id) {

		if (id == null)
			throw new NullPointerException("id");

		if (getLogger().isInfoEnabled())
			getLogger().info("removed scope: " + id);

		scopes.remove(id);
	}

	public Scope get(String id) {
		return (Scope) scopes.get(id);
	}

	public Scope get(ScopeType scopeId) {
		return get(scopeId.toString());
	}

	public Map<String, Scope> getScopes() {
		return Collections.unmodifiableMap(scopes);
	}

	void clear() {
		scopes.clear();
	}

	public Logger getLogger() {
		return LoggerProvider.getCurrentLoggerProvider().getLogger(
				Scopes.class.getName());
	}

	public static Scope getCurrentScope(ScopeType value) {

		ApplicationContext context = Invoker.getCurrentApplicationContext();

		if (context == null)
			throw new BrutosException("could not get context");

		Scope objectScope = context.getScopes().get(value);

		if (objectScope == null)
			throw new BrutosException("scope not found in context: " + value);

		return objectScope;
	}

}
