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

package org.brandao.brutos.annotation.configuration;

import org.brandao.brutos.DispatcherType;
import org.brandao.brutos.annotation.DefaultThrowSafe;
import org.brandao.brutos.annotation.ThrowSafe;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
public class ThrowableEntry {

	private String view;

	private DispatcherType dispatcher;

	private Class<? extends Throwable> target;

	private String name;

	private boolean rendered;

	private boolean enabled;

	private boolean resolved;

	public ThrowableEntry() {
	}

	public ThrowableEntry(Class<? extends Throwable> target) {
		this.target = target;
		this.enabled = true;
		this.rendered = true;
		this.resolved = false;
	}

	public ThrowableEntry(ThrowSafe value) {
		this.dispatcher = StringUtil.isEmpty(value.dispatcher()) ? null
				: DispatcherType.valueOf(StringUtil.adjust(value.dispatcher()));

		this.enabled = value.enabled();
		this.name = StringUtil.isEmpty(value.name()) ? null
				: StringUtil.adjust(value.name());

		this.rendered = value.rendered();
		this.target = value.target();
		this.view = StringUtil.adjust(value.view());
		this.resolved = value.resolved();
	}

	public ThrowableEntry(DefaultThrowSafe value,
			Class<? extends Throwable> target) {
		this.dispatcher = StringUtil.isEmpty(value.dispatcher()) ? null
				: DispatcherType.valueOf(StringUtil.adjust(value.dispatcher()));

		this.enabled = value.enabled();
		this.name = StringUtil.isEmpty(value.name()) ? null
				: StringUtil.adjust(value.name());

		this.rendered = value.rendered();
		this.target = target;
		this.view = StringUtil.adjust(value.view());
		this.resolved = value.resolved();
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public DispatcherType getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(DispatcherType dispatcher) {
		this.dispatcher = dispatcher;
	}

	public Class<? extends Throwable> getTarget() {
		return target;
	}

	public void setTarget(Class<? extends Throwable> target) {
		this.target = target;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ThrowableEntry other = (ThrowableEntry) obj;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
