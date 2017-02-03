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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 *
 * @author Brandao
 */
public class ConstructorArgEntryWrapper extends ConstructorArgEntry {

	private ConstructorArgEntry constructorArgEntry;

	public ConstructorArgEntryWrapper(ConstructorArgEntry constructorArgEntry) {
		this.constructorArgEntry = constructorArgEntry;
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
		return constructorArgEntry.isAnnotationPresent(annotation);
	}

	@Override
	public <T> T getAnnotation(Class<T> annotation) {
		return constructorArgEntry.getAnnotation(annotation);
	}

	@Override
	public Type getGenericType() {
		return constructorArgEntry.getGenericType();
	}

	@Override
	public void setGenericType(Type genericType) {
		constructorArgEntry.setGenericType(genericType);
	}

	@Override
	public Class getType() {
		return constructorArgEntry.getType();
	}

	@Override
	public void setType(Class type) {
		constructorArgEntry.setType(type);
	}

	@Override
	public String getName() {
		return constructorArgEntry.getName();
	}

	@Override
	public void setName(String name) {
		constructorArgEntry.setName(name);
	}

	@Override
	public Annotation[] getAnnotation() {
		return constructorArgEntry.getAnnotation();
	}

	@Override
	public void setAnnotation(Annotation[] annotation) {
		constructorArgEntry.setAnnotation(annotation);
	}

	@Override
	public int getIndex() {
		return constructorArgEntry.getIndex();
	}

	@Override
	public void setIndex(int index) {
		constructorArgEntry.setIndex(index);
	}

}
