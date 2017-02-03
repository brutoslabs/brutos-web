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

package org.brandao.brutos.annotation.scanner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Afonso Brandao
 */
public abstract class AbstractScanner implements Scanner {

	protected Set<Class> listClass;
	protected Set<TypeFilter> includeFilters;
	protected Set<TypeFilter> excludeFilters;
	private String[] basePackage;

	public AbstractScanner() {
		this.listClass = new HashSet<Class>();
		this.includeFilters = new HashSet<TypeFilter>();
		this.excludeFilters = new HashSet<TypeFilter>();
	}

	protected boolean accepts(String resource) {
		if (!listClass.contains(resource)) {

			boolean include = false;

			for (TypeFilter filter : this.includeFilters) {
				if (filter.accepts(resource)) {
					include = true;
					break;
				}
			}

			if (include) {
				for (TypeFilter filter : this.excludeFilters) {
					if (filter.accepts(resource)) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}

	public List getClassList() {
		return new ArrayList(this.listClass);
	}

	public String[] getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String[] basePackage) {
		this.basePackage = basePackage;
	}

	public void addIncludeFilter(TypeFilter filter) {
		this.includeFilters.add(filter);
	}

	public void addExcludeFilter(TypeFilter filter) {
		this.excludeFilters.add(filter);
	}

	public void removeIncludeFilter(TypeFilter filter) {
		this.includeFilters.remove(filter);
	}

	public void removeExcludeFilter(TypeFilter filter) {
		this.excludeFilters.remove(filter);
	}

}