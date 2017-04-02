/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos;

import org.brandao.brutos.scope.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.logger.Logger;
import org.brandao.brutos.logger.LoggerProvider;

/**
 * 
 * @author Brandao
 */
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
