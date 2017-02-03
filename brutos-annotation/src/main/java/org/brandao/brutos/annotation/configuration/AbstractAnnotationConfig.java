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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.brandao.brutos.BrutosException;
import org.brandao.brutos.ComponentRegistry;
import org.brandao.brutos.ConfigurableApplicationContext;
import org.brandao.brutos.annotation.AnnotationConfig;

/**
 *
 * @author Brandao
 */
public abstract class AbstractAnnotationConfig implements AnnotationConfig,
		ApplyAnnotationConfig {

	protected AnnotationConfigEntry annotation;

	protected ConfigurableApplicationContext applicationContext;

	private Converter sourceConverter;

	public void setApplicationContext(
			ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setConfiguration(AnnotationConfigEntry annotation) {
		this.annotation = annotation;
	}

	public AnnotationConfigEntry getConfiguration() {
		return this.annotation;
	}

	public Object applyInternalConfiguration(Object source, Object builder,
			ComponentRegistry componentRegistry) {

		List<AnnotationConfigEntry> list = getOrder(annotation
				.getNextAnnotationConfig());

		for (int i = 0; i < list.size(); i++) {
			AnnotationConfig next = list.get(i).getAnnotationConfig();
			if (next.isApplicable(source)) {
				source = next.getSourceConverter() == null ? source : next
						.getSourceConverter().converter(source,
								componentRegistry);

				builder = next.applyConfiguration(source, builder,
						componentRegistry);
			}
		}

		return builder;
	}

	private List<AnnotationConfigEntry> getOrder(
			List<AnnotationConfigEntry> list) {

		Class<? extends Annotation>[] order = getExecutionOrder();

		if (order.length == 0)
			return list;

		Map<Class, AnnotationConfigEntry> map = new HashMap<Class, AnnotationConfigEntry>();

		for (AnnotationConfigEntry ace : annotation.getNextAnnotationConfig())
			map.put(ace.getStereotype().target(), ace);

		List<AnnotationConfigEntry> result = new ArrayList<AnnotationConfigEntry>();

		for (Class target : getExecutionOrder()) {
			AnnotationConfigEntry ace = map.get(target);

			if (ace == null)
				throw new BrutosException("not found target: @"
						+ target.getSimpleName());

			result.add(ace);
		}

		for (AnnotationConfigEntry ace : annotation.getNextAnnotationConfig()) {
			if (!map.containsKey(ace.getStereotype().target()))
				result.add(ace);
		}

		return result;
	}

	public Class<? extends Annotation>[] getExecutionOrder() {
		return new Class[] {};
	}

	public void setSourceConverter(Converter value) {
		this.sourceConverter = value;
	}

	public Converter getSourceConverter() {
		return this.sourceConverter;
	}

}
