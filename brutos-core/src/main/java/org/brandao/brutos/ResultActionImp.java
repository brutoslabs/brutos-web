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

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Brandao
 */
public class ResultActionImp implements ConfigurableResultAction {

	private Map<String, Object> infos;

	private Map<String, Object> values;

	private Class<?> contentType;

	private Object content;

	private String view;

	private boolean resolved;

	public ResultActionImp() {
		this.infos = new HashMap<String, Object>();
		this.values = new HashMap<String, Object>();
	}

	public String getView() {
		return this.view;
	}

	public boolean isResolvedView() {
		return this.resolved;
	}

	public Class<?> getContentType() {
		return this.contentType;
	}

	public Object getContent() {
		return this.content;
	}

	public Map<String, Object> getVars() {
		return this.values;
	}

	public void setVars(Map<String, Object> values) {
		this.values = values;
	}

	public Map<String, Object> getHeader() {
		return this.infos;
	}

	public void setHeader(Map<String, Object> infos) {
		this.infos = infos;
	}

	public ResultAction setView(String view) {
		return this.setView(view, false);
	}

	public ResultAction setView(String view, boolean resolved) {
		if (this.content != null || this.contentType != null)
			throw new IllegalStateException();
		else {
			this.view     = view;
			this.resolved = resolved;
			return this;
		}
	}

	public ResultAction setContentType(Class<?> type) {
		if (this.view != null)
			throw new IllegalStateException();
		else {
			this.contentType = type;
			return this;
		}
	}

	public ResultAction addInfo(String name, String o) {
		this.infos.put(name, o);
		return this;
	}

	public ResultAction setContent(Object value) {
		if (this.view != null)
			throw new IllegalStateException();
		else {
			this.content = value;
			return this;
		}
	}

	public ResultAction add(String name, Object o) {
		this.values.put(name, o);
		return this;
	}

}
