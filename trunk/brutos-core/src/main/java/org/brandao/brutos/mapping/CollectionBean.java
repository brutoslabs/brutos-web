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

package org.brandao.brutos.mapping;

import java.util.Collection;

import org.brandao.brutos.BrutosException;
import org.brandao.brutos.validator.ValidatorException;

/**
 * 
 * @author Brandao
 */
public class CollectionBean extends Bean {

	protected DependencyBean collection;

	public CollectionBean(Controller controller, Bean parent) {
		super(controller, parent);
	}

	public void setCollection(DependencyBean collection) {
		this.collection = collection;
	}

	public DependencyBean getCollection() {
		return this.collection;
	}

	protected Object get(String prefix, long index,
			ValidatorException exceptionHandler) {

		if (collection != null)
			return collection.getValue(prefix, index, exceptionHandler, null);
		else
			throw new MappingException(String.format(
					"element of the collection is not defined: %s",
					new Object[] { this.getName() }));
	}

	public Object getValue(boolean force) {
		return getValue(null, null, -1, null, force);
	}

	public Object getValue(Object instance) {
		return getValue(instance, null, -1, null, false);
	}

	public Object getValue() {
		return getValue(null);
	}

	@SuppressWarnings("unchecked")
	public Object getValue(Object instance, String prefix, long otherIndex,
			ValidatorException exceptionHandler, boolean force) {
		try {

			ValidatorException vex = new ValidatorException();

			instance = getInstance(instance, prefix, otherIndex, vex, force);
			Collection<Object> collectionBean = (Collection<Object>) instance;

			long index = 0;
			Object beanInstance;

			if(this.collection.getParameterName() == null){
				prefix = prefix.substring(0, prefix.length() - 1);
			}
					
			while ((beanInstance = this.collection.getValue(prefix, index, vex, null, null)) != null) {
				collectionBean.add(beanInstance);
				index++;
			}

			if (!collectionBean.isEmpty() || force) {
				if (exceptionHandler == null) {
					if (!vex.getCauses().isEmpty())
						throw vex;
					else
						return collectionBean;
				} else {
					exceptionHandler.addCauses(vex.getCauses());
					return collectionBean;
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

	protected Object getInstance(Object instance, String prefix, long index,
			ValidatorException exceptionHandler, boolean force)
			throws InstantiationException, IllegalAccessException {

		if (instance == null) {
			instance = super.getValue(instance, prefix, index,
					exceptionHandler, force);
		}

		return instance;
	}
	
	public boolean isBean() {
		return false;
	}

	public boolean isCollection() {
		return true;
	}

	public boolean isMap() {
		return false;
	}

}
