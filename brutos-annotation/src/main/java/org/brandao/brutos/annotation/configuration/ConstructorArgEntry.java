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

import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.EnumerationType;
import org.brandao.brutos.FetchType;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.DetachedName;
import org.brandao.brutos.annotation.Enumerated;
import org.brandao.brutos.annotation.Target;
import org.brandao.brutos.annotation.Temporal;
import org.brandao.brutos.mapping.StringUtil;

/**
 *
 * @author Brandao
 */
public class ConstructorArgEntry {

	private int index;

	private Type genericType;

	private Class<?> type;

	private String name;

	private Annotation[] annotation;

	public ConstructorArgEntry() {
	}

	public ConstructorArgEntry(String name, Class<?> type, Type genericType,
			Annotation[] annotation, int index) {
		this.name = name;
		this.type = type;
		this.genericType = genericType;
		this.annotation = annotation;
		this.index = index;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
		for (Annotation a : this.annotation) {
			if (a.annotationType().isAssignableFrom(annotation))
				return true;
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public <T> T getAnnotation(Class<T> annotation) {
		for (Annotation a : this.annotation) {
			if (a.annotationType().isAssignableFrom(annotation))
				return (T) a;
		}

		return null;
	}

	public Type getGenericType() {
		Target target = this.getAnnotation(Target.class);
		return target == null ? this.genericType : target.value();
	}

	public void setGenericType(Type genericType) {
		this.genericType = genericType;
	}

	public Class<?> getType() {
		Target target = this.getAnnotation(Target.class);
		return target == null ? this.type : target.value();
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getName() {

		DetachedName notNamed = (DetachedName) this.getAnnotation(DetachedName.class);

		if (notNamed != null)
			return null;

		Basic basic = (Basic) this.getAnnotation(Basic.class);

		if (basic != null) {
			String actionName = StringUtil.adjust(basic.bean());
			if (!StringUtil.isEmpty(actionName))
				return actionName;
		}

		if (this.name != null) {
			String actionName = StringUtil.adjust(this.name);
			if (!StringUtil.isEmpty(actionName))
				return actionName;

		}

		//return this.getDefaultName();
		return BrutosConstants.EMPTY_ARGNAME;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Annotation[] getAnnotation() {
		return annotation;
	}

	public void setAnnotation(Annotation[] annotation) {
		this.annotation = annotation;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public org.brandao.brutos.type.Type getTypeInstance(){
		return AnnotationUtil.getTypeInstance(this.getAnnotation(org.brandao.brutos.annotation.Type.class));
	}

	public FetchType getFetchType(){
		Basic basic = this.getAnnotation(Basic.class);
		return basic == null? null : FetchType.valueOf(basic.fetchType().name().toLowerCase());
	}
	
	public String getTemporalProperty(){
		return AnnotationUtil.getTemporalProperty(this.getAnnotation(Temporal.class));
	}
	
	public EnumerationType getEnumProperty(){
		return AnnotationUtil.getEnumerationType(this.getAnnotation(Enumerated.class));
	}

	public ScopeType getScope() {
		return AnnotationUtil.getScope(this.getAnnotation(Basic.class));
	}
	
}
