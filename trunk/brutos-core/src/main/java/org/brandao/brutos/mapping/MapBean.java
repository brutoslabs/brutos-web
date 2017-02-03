^/*
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

package org.brandao.brutos.mapping;

import java.util.Map;
import org.brandao.brutos.*;
import org.brandao.brutos.validator.ValidatorException;

public class MapBean extends CollectionBean {

	private DependencyBean key;

	public MapBean(Controller controller, Bean parent) {
		super(controller, parent);
	}

	public void setKey(DependencyBean key) {
		this.key = key;
	}

	public DependencyBean getKey() {
		return this.key;
	}

	private Object getKey(long index, String prefix,
			ValidatorException exceptionHandler) {

		if (key != null)
			return key.getValue(prefix, index, exceptionHandler, null);
		else
			throw new BrutosException(String.format(
					"key mapping not defined: %s",
					new Object[] { this.getName() }));

	}

	public Object getValue() {
		return getValue(null);
	}

	public Object getValue(Object instance) {
		return getValue(instance, null, -1, null, false);
	}

	public Object getValue(boolean force) {
		return getValue(null, null, -1, null, force);
	}

	public Object getValue(Object instance, String prefix, long otherIndex,
			ValidatorException exceptionHandler, boolean force) {
		try {

			ValidatorException vex = new ValidatorException();

			instance = getInstance(instance, prefix, otherIndex, vex, force);

			Map map = (Map) instance;

			long index = 0;
			Object beanInstance;

			while ((beanInstance = get(prefix, index, vex)) != null) {

				Object keyInstance = getKey(index, prefix, vex);

				if (keyInstance != null)
					map.put(keyInstance, beanInstance);

				index++;
			}

			if (!map.isEmpty() || force) {
				if (exceptionHandler == null) {
					if (!vex.getCauses().isEmpty())
						throw vex;
					else
						return map;
				} else {
					exceptionHandler.addCauses(vex.getCauses());
					return map;
				}
			} else
				return null;

		} catch (ValidatorException e) {
			throw e;
		} catch (BrutosException e) {
			throw e;
		} catch (Exception e) {
			throw new BrutosException(e);
		}
	}

	public boolean isBean() {
		return false;
	}

	public boolean isCollection() {
		return false;
	}

	public boolean isMap() {
		return true;
	}

}